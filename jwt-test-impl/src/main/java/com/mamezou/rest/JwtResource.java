package com.mamezou.rest;

import javax.security.sasl.AuthenticationException;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.jwt.Claims;
import org.eclipse.microprofile.jwt.JsonWebToken;

import jakarta.inject.Inject;

public class JwtResource {
    @Inject
    protected JsonWebToken jwt;

    @ConfigProperty(name = "mp.jwt.verify.issuer")
    protected String issuer;

    // JWT を認証する
    protected void validateJwt() throws AuthenticationException {
        if (!validateJwtBody()) {
            throw new AuthenticationException("Invalid Token");
        }
    }

    private boolean validateJwtBody() {
        // issuer を確認する
        if (!issuer.trim().equals(jwt.getIssuer())) {
            return false;
        }

        // exp を確認する
        Long expTime = jwt.getClaim(Claims.exp);
        if (expTime == null) {
            return false;
        }
        Long curTime = System.currentTimeMillis() / 1000L;
        return curTime < expTime;
    }
}
