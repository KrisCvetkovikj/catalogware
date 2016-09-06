package com.wp.finki.ukim.mk.catalogware.validator;

import com.wp.finki.ukim.mk.catalogware.model.Product;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Borce on 03.09.2016.
 */
@Component
public class ProductRequestValidator {

    public Map<String, String> validate(Product product, MultipartFile image) {
        Map<String, String> errors = this.validate(product);
        if (image == null) {
            errors.put("image.notNull", "The image is required");
        } else if (image.getSize() > 20480) {
            errors.put("image.max", "The image size is too big");
        }
        return errors;
    }

    public Map<String, String> validate(Product product) {
        Map<String, String> errors = new HashMap<>();
        if (product.getName()== null) {
            errors.put("name.notNull", "The name field is required");
        }
        if (product.getDescription() == null) {
            errors.put("description.notNull", "The description field is required");
        } else if (product.getDescription().length() > 10000) {
            errors.put("description.max", "The description can't contains more that 10000 characters");
        }
        if (product.getPrice() < 1) {
            errors.put("price.min", "The price field must be greater that 0");
        }
        return errors;
    }
}
