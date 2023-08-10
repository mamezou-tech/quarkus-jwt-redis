package com.mamezou.rest.handler;

import jakarta.ws.rs.ext.Provider;

import javax.security.sasl.AuthenticationException;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;

@Provider
public class AuthenticationExceptionHandler implements ExceptionMapper<AuthenticationException> {

    @Override
    public Response toResponse(AuthenticationException e) {
        return Response.status(Response.Status.UNAUTHORIZED).build();
    }
    
}
