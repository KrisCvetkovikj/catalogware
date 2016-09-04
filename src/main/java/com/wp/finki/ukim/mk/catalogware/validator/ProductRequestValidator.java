package com.wp.finki.ukim.mk.catalogware.validator;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Borce on 03.09.2016.
 */
@Component
public class ProductRequestValidator {

    public Map<String, String> validate(String name, String description, Double price, MultipartFile image) {
        Map<String, String> errors = new HashMap<>();
        if (name == null) {
            errors.put("name.notNull", "The name field is required");
        }
        if (description == null) {
            errors.put("description.notNull", "The description field is required");
        } else if (description.length() > 10000) {
            errors.put("description.max", "The description can't contains more that 10000 characters");
        }
        if (price == null) {
            errors.put("price.notNull", "The price field is required");
        } else if (price < 1) {
            errors.put("price.min", "The price field must be greater that 0");
        }
        if (image == null) {
            errors.put("image.notNull", "The image is required");
        } else if (image.getSize() > 20480) {
            errors.put("image.max", "The image size is too big");
        }
        return errors;
    }
}
