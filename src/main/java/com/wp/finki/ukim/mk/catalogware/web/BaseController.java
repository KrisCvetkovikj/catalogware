package com.wp.finki.ukim.mk.catalogware.web;

import com.wp.finki.ukim.mk.catalogware.exception.BadRequestException;
import com.wp.finki.ukim.mk.catalogware.utils.ValidationErrorsMessageConverter;
import org.springframework.validation.BindingResult;

/**
 * Created by Borce on 03.09.2016.
 */
public class BaseController {

    public void checkForBadRequest(BindingResult bindingResult,
                                   ValidationErrorsMessageConverter validationErrorsMessageConverter) {
        if (bindingResult.hasErrors()) {
            throw new BadRequestException(validationErrorsMessageConverter
                    .getErrors(bindingResult.getFieldErrors()));
        }
    }
}
