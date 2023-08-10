package com.mamezou.kvs;

import jakarta.ws.rs.Path;
import jakarta.ws.rs.CookieParam;
import jakarta.ws.rs.POST;

import com.mamezou.kvs.bean.User;

import javax.security.sasl.AuthenticationException;

import com.mamezou.kvs.bean.ResultUser;

@Path("/welcome")
public class WelcomeResource extends BaseResource {

    @POST
    public ResultUser loadUser(@CookieParam("sessionID") String  ckvalue) throws AuthenticationException {
        // Redis との突合およびユーザ情報取得
        User user = validateUser(ckvalue);

        return new ResultUser(true, user);
    }
}
