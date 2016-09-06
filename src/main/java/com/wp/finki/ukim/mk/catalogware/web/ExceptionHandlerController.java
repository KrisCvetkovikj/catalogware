package com.wp.finki.ukim.mk.catalogware.web;

import com.wp.finki.ukim.mk.catalogware.exception.*;
import com.wp.finki.ukim.mk.catalogware.model.response.ErrorResponse;
import com.wp.finki.ukim.mk.catalogware.model.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.MethodArgumentConversionNotSupportedException;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Borce on 02.09.2016.
 */
@ControllerAdvice
@RestController
public class ExceptionHandlerController {

    private static final Logger logger = LoggerFactory.getLogger(ExceptionHandlerController.class);

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ErrorResponse badRequestException(BadRequestException exp) {
        logger.info("bad request exception");
        return new ErrorResponse(400, "Bad Request", "The request data can't be processed", exp.getErrors());
    }

    @ExceptionHandler(BadCredentialsException.class)
    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
    public ErrorResponse badCredentialsException() {
        logger.info("bad cradentials exception");
        Map<String, String> map = new HashMap<>();
        map.put("credentials.notMatch", "The given credentials don't match with our records");
        return new ErrorResponse(401, "UnAuthorized", "User not authenticated", map);
    }

    @ExceptionHandler(NotAuthenticatedException.class)
    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
    public Response notAuthenticatedException() {
        logger.info("user not authenticated exception");
        return new Response(401, "Not Authenticated",
                "You need to be authenticated in order to access to this resource");
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(value = HttpStatus.FORBIDDEN)
    public Response accessForbiddenException(AccessDeniedException exp) {
        logger.info("access denied exception");
        return new Response(403, "Access Denied", "You don't have permissions to access to this resource");
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Response resourceNotFoundException(ResourceNotFoundException exp) {
        if (exp.getMessage() != null) {
            logger.error(exp.getMessage());
        } else {
            logger.error("resource not found");
        }
        return new Response(404, "Not Found", "The requested resource was not found on the server");
    }

    @ExceptionHandler(ResourceChangeFailedException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Response resourceChangeFailedException(ResourceChangeFailedException exp) {
        logger.error(exp.getMessage());
        return new Response(500, "Internal Server Error",
                "Some error occurred while performing the last action. Please try again later");
    }

    @ExceptionHandler(EmptyBasketException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Response emptyBasketException() {
        logger.error("empty basket exception has been thrown");
        return new Response(500, "Empty Basket", "The basket is empty");
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public Response methodNotAllowedException(HttpRequestMethodNotSupportedException exp) {
        logger.info(String.format("method %s not supported on the resource", exp.getMethod()));
        return new Response(405, "Method not allowed", "This method is not allowed on the resource");
    }
}
