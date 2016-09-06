package com.wp.finki.ukim.mk.catalogware.web;

import com.wp.finki.ukim.mk.catalogware.exception.BadRequestException;
import com.wp.finki.ukim.mk.catalogware.exception.ProductNotFoundException;
import com.wp.finki.ukim.mk.catalogware.model.Product;
import com.wp.finki.ukim.mk.catalogware.model.ProductLike;
import com.wp.finki.ukim.mk.catalogware.model.response.Response;
import com.wp.finki.ukim.mk.catalogware.model.security.AuthUser;
import com.wp.finki.ukim.mk.catalogware.service.BasketService;
import com.wp.finki.ukim.mk.catalogware.service.ProductLikeService;
import com.wp.finki.ukim.mk.catalogware.service.ProductService;
import com.wp.finki.ukim.mk.catalogware.utils.ValidationErrorsMessageConverter;
import com.wp.finki.ukim.mk.catalogware.validator.ProductRequestValidator;
import java.util.HashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Borce on 02.09.2016.
 */
@RestController
@RequestMapping("/products")
public class ProductController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    private ProductService service;

    @Autowired
    private ProductLikeService likeService;

    @Autowired
    private BasketService basketService;

    @Autowired
    private ProductRequestValidator validator;

    @GetMapping
    public List<Product> index(@RequestParam(required = false) Integer page,
                               @RequestParam(required = false) Integer size,
                               @RequestParam(required = false) Boolean latest,
                               @RequestParam(required = false) String categories) {
        logger.info("list products");
        page = page != null ? page : 0;
        size = size != null ? size : Integer.MAX_VALUE;
        latest = latest !=  null? latest : true;
        if (categories != null) {
            return service.filterByCategories(page, size, latest, categories);
        }
        return service.getAll(page, size, latest);
    }

    @GetMapping(value = "/{id}")
    public Product get(@PathVariable long id) {
        logger.info(String.format("get product with id %d", id));
        Product product = service.get(id);
        if (product == null) {
            throw new ProductNotFoundException(String.format("product with id %d not found", id));
        }
        return product;
    }

    @GetMapping(value = "/{id}/image")
    public void getImage(@PathVariable long id, HttpServletResponse response) throws IOException {
        Product product = service.get(id);
        if (product == null) {
            throw new ProductNotFoundException(String.format("product with id %d not found", id));
        }
        byte[] image = product.getImage();
        if (image == null) {
            throw new ProductNotFoundException(String.format("product with id %d don't have image", id));
        }
        OutputStream out = response.getOutputStream();
        response.setContentType("image/jpeg, image/jpg, image/png, image/gif");
        out.write(image);
        out.flush();
        out.close();
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping(consumes = "multipart/form-data")
    @ResponseStatus(HttpStatus.CREATED)
    public Response store(@RequestPart(value = "product") Product product,
                          @RequestPart(value = "image", required = false) MultipartFile image,
                          @AuthenticationPrincipal AuthUser authUser) {
        logger.info("crating product");
//        Map<String, String> errors = validator.validate(name, description, price, image);
//        if (errors.size() > 0) {
//            logger.info("request data to create the product is invalid");
//            throw new BadRequestException(errors);
//        }
//        service.store(name, description, price, image, authUser);
        return new Response(201, "Product created", "The product was created successfully");
    }

    /**
     * Its post and not put because put only accepts single resource, so we can't send image over put.
     *
     * @param id product id
     * @param name new product name
     * @param description new product description
     * @param price new product price
     * @param image new product image
     * @return success response indicating that the product has been saved in the database
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping(value = "/{id}", consumes = "multipart/form-data")
    public Response update(@PathVariable long id, @RequestParam(required = false) String name,
                           @RequestParam(required = false) String description,
                           @RequestParam(required = false) Double price,
                           MultipartFile image) {
        logger.info(String.format("updating product with id %d", id));
        Map<String, String> errors = validator.validate(name, description, price, image);
        if (errors.size() > 0) {
            throw new BadRequestException(errors);
        }
        service.update(id, name, description, price, image);
        return new Response(200, "Product updated", "The product was updated successfully");
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable long id) {
        service.delete(id);
    }

    @GetMapping(value = "/{id}/likes")
    public Set<ProductLike> getLikes(@PathVariable long id) {
        return likeService.getProductLikes(id);
    }

    @PreAuthorize("hasAuthority('CUSTOMER')")
    @GetMapping(value = "/{id}/likes/me")
    public Map<String, Short> getAuthUserLike(@PathVariable long id, @AuthenticationPrincipal AuthUser user) {
        ProductLike like = likeService.get(user.getId(), id);
        Map<String, Short> map = new HashMap<>();
        if (like != null) {
            map.put("rating", like.getRating());
        } else {
            map.put("rating", null);
        }
        return map;
    }

    @PreAuthorize("hasAuthority('CUSTOMER') and !@productLikeService.exists(#authUser.id, #id)")
    @PostMapping("/{id}/likes")
    @ResponseStatus(HttpStatus.CREATED)
    public Response addLike(@PathVariable long id, @AuthenticationPrincipal AuthUser authUser,
                            @Valid @RequestBody ProductLike like, BindingResult bindingResult,
                            ValidationErrorsMessageConverter validationErrorsMessageConverter) {
        super.checkForBadRequest(bindingResult, validationErrorsMessageConverter);
        likeService.store(authUser.getId(), id, like.getRating());
        return new Response(200, "Like added", "The like on the product was added successfully");
    }

    @PreAuthorize("hasAuthority('CUSTOMER') and @productLikeService.exists(#authUser.id, #id)")
    @PutMapping("/{id}/likes")
    public Response updateLike(@PathVariable long id, @AuthenticationPrincipal AuthUser authUser,
                            @Valid @RequestBody ProductLike like, BindingResult bindingResult,
                            ValidationErrorsMessageConverter validationErrorsMessageConverter) {
        super.checkForBadRequest(bindingResult, validationErrorsMessageConverter);
        likeService.update(authUser.getId(), id, like.getRating());
        return new Response(200, "Like updated", "The like rating was updated successfully");
    }

    @PreAuthorize("hasAuthority('CUSTOMER') and @productLikeService.exists(#authUser.id, #id)")
    @DeleteMapping("/{id}/likes")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteLike(@PathVariable long id, @AuthenticationPrincipal AuthUser authUser) {
        likeService.delete(authUser.getId(), id);
    }

    @PreAuthorize("hasAuthority('CUSTOMER')")
    @GetMapping("/{id}/baskets/me")
    public Map<String, Boolean> getBasket(@PathVariable long id, @AuthenticationPrincipal AuthUser authUser) {
        Map<String, Boolean> result = new HashMap<>();
        result.put("inBasket", basketService.hasProduct(authUser.getId(), id));
        return result;
    }

    @PreAuthorize("hasAuthority('CUSTOMER') and !@basketService.hasProduct(#authUser.id, #id)")
    @PostMapping("/{id}/baskets")
    @ResponseStatus(HttpStatus.CREATED)
    public Response addToBasket(@PathVariable long id, @AuthenticationPrincipal AuthUser authUser) {
        basketService.addProduct(authUser.getId(), id);
        return new Response(201, "Product added", "The product was successfully added to the basket");
    }

    @PreAuthorize("hasAuthority('CUSTOMER') and @basketService.hasProduct(#authUser.id, #id)")
    @DeleteMapping("/{id}/baskets")
    public void removeFromBasket(@PathVariable long id, @AuthenticationPrincipal AuthUser authUser) {
        basketService.removeProduct(authUser.getId(), id);
    }
}
