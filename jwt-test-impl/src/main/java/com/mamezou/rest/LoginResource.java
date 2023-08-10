package com.mamezou.rest;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.stream.Collectors;
import org.eclipse.microprofile.jwt.Claims;

import java.util.HashSet;
import java.util.Arrays;
import java.util.UUID;
import java.util.Map;
import com.mamezou.rest.bean.Login;
import com.mamezou.rest.bean.ResultPack;
import com.mamezou.rest.bean.User;

import io.smallrye.jwt.build.Jwt;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;

import org.jboss.resteasy.reactive.RestResponse;
import org.jboss.resteasy.reactive.RestResponse.ResponseBuilder;

@Path("/login")
public class LoginResource {
    // 利用可能ユーザ一覧(本来は DB 等にあるはず)
    private Map<String, User> users = Collections.synchronizedMap(new LinkedHashMap<>());

    // トークンの有効期間
    private static long decayTime = 5 * 60;

    // コンストラクタ：ユーザ2人を定義
    public LoginResource() {
        users.put("minoru-matsumoto@mamezou.com", new User("minoru-matsumoto@mamezou.com", new HashSet<>(Arrays.asList(User.Groups.ADMIN)), "matsumoto"));
        users.put("hogehoge@example.com", new User("hogehoge@example.com", new HashSet<>(Arrays.asList(User.Groups.USER)), "hogehoge"));
    }

    // ログイン判定
    @POST
    public RestResponse<ResultPack> doLogin(Login login) {
        User user = users.get(login.email);

        if (user == null || !user.getPassword().equals(login.password)) {
            return ResponseBuilder.ok(new ResultPack(false, "Login failed")).build();
        }
        String JwtString = makeJwtForLoginUser(user);
        return ResponseBuilder.ok(new ResultPack(true, null))
            .header("X-Authorization-Token", JwtString)
            .build();
    }

    // JWT を生成する
    private String makeJwtForLoginUser(User user) {
        Long expireDateTime = System.currentTimeMillis() / 1000L + decayTime;
        String token = Jwt
            .issuer("https://mamezou.com/issuer")
            .upn(user.getEmail())
            .groups(user.getGroupId().stream().map(x -> x.getId()).collect(Collectors.toSet()))
            .claim(Claims.exp.name(), expireDateTime)
            .claim(Claims.jti.name(), UUID.randomUUID())
            .sign();
        return token;
    }
}
