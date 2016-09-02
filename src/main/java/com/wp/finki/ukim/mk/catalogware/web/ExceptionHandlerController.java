package com.wp.finki.ukim.mk.catalogware.web;

import com.wp.finki.ukim.mk.catalogware.exception.BadRequestException;
import com.wp.finki.ukim.mk.catalogware.exception.NotAuthenticatedException;
import com.wp.finki.ukim.mk.catalogware.model.response.ErrorResponse;
import com.wp.finki.ukim.mk.catalogware.model.response.Response;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Borce on 02.09.2016.
 */
@ControllerAdvice
@RestController
public class ExceptionHandlerController {

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ErrorResponse badRequestException(BadRequestException exp) {
        return new ErrorResponse(400, "Bad Request", "The request data can't be processed", exp.getErrors());
    }

    @ExceptionHandler(BadCredentialsException.class)
    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
    public ErrorResponse badCredentialsException() {
        Map<String, String> map = new HashMap<>();
        map.put("credentials.notMatch", "The given credentials don't match with our records");
        return new ErrorResponse(401, "UnAuthorized", "User not authenticated", map);
    }

    @ExceptionHandler(NotAuthenticatedException.class)
    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
    public Response notAuthenticatedException() {
        return new Response(401, "Not Authenticated",
                "You need to be authenticated in order to access to this resource");
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(value = HttpStatus.FORBIDDEN)
    public Response accessForbiddenException(AccessDeniedException exp) {
        return new Response(403, "Access Denied", "You don't have permissions to access to this resource");
    }
}
