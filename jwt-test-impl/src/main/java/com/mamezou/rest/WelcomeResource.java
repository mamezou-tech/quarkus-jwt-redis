package com.mamezou.rest;

import java.util.Set;

import javax.security.sasl.AuthenticationException;

import java.util.HashSet;
import java.util.Arrays;
import java.util.Map;
import java.util.HashMap;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.annotation.security.RolesAllowed;
import com.mamezou.rest.bean.Welcome;

@Path("/welcome")
public class WelcomeResource extends JwtResource {
    private static Set<String> excludes = new HashSet<>(Arrays.asList("upn", "raw_token", "groups", "iss"));
    
    @POST
    @RolesAllowed({"User", "Admin"})
    public Welcome doWelcome() throws AuthenticationException {
        // ユーザ定義の annotation に記述して検査するようにできないか？
        validateJwt();

        Map<String, String> claims = new HashMap<>();
        jwt.getClaimNames().stream().forEach(
            n -> {
                if (jwt.getClaim(n) != null && !excludes.contains(n)) {
                    claims.put(n, jwt.getClaim(n).toString());
                }
            }
        );
        return new Welcome(jwt.getIssuer(), jwt.getName(), jwt.getGroups(), claims);
    }
}
