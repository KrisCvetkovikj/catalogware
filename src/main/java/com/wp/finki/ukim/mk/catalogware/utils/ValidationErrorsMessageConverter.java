package com.wp.finki.ukim.mk.catalogware.utils;

import org.springframework.validation.FieldError;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Borce on 02.09.2016.
 */
public class ValidationErrorsMessageConverter {

    public Map<String, String> getErrors(List<FieldError> fieldErrors) {
        Map<String, String> errors = new LinkedHashMap<>();
        for (FieldError fieldError : fieldErrors) {
            String code = fieldError.getCode();
            String key = String.format("%s.%s%s", fieldError.getField(), code.substring(0, 1).toLowerCase(),
                    code.substring(1, code.length()));
            errors.put(key, fieldError.getDefaultMessage());
        }
        return errors;
    }
}
