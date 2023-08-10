package com.mamezou.kvs;

import jakarta.ws.rs.Path;
import jakarta.ws.rs.CookieParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.core.NewCookie;
import java.util.UUID;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.security.sasl.AuthenticationException;

import com.mamezou.kvs.bean.Login;
import com.mamezou.kvs.bean.ResultPack;
import com.mamezou.kvs.bean.User;

import org.jboss.resteasy.reactive.RestResponse;
import org.jboss.resteasy.reactive.RestResponse.ResponseBuilder;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@Path("/login")
public class LoginResource extends BaseResource {
    private Map<String, User> umap = new ConcurrentHashMap<String, User>();

    public LoginResource() {
        // ユーザ登録
        umap.put("minoru-matsumoto@mamezou.com", new User(1, "minoru-matsumoto@mamezou.com", "Minoru Matsumoto", "Matsumoto31"));
    }

    @POST
    public RestResponse<ResultPack> doLogin(Login login) throws AuthenticationException {
        // ユーザチェック
        User u = umap.get(login.getEmail());
        if (u == null || !u.getPassword().equals(login.getPassword())) {
            throw new AuthenticationException();
        }
        // セッションIDを生成する
        UUID uuid = UUID.randomUUID();
        NewCookie cookie = new NewCookie.Builder("sessionID")
            .value(uuid.toString())
            .maxAge(5 * 60 * 60)
            .build();

        try (JedisPool pool = new JedisPool("localhost", 6379)) {
            try (Jedis jedis = pool.getResource()) {
                // Redis に登録する
                jedis.hset(uuid.toString(), u.getMap());

                // ログイン結果を返す
                return ResponseBuilder.ok(new ResultPack(true, null))
                    .cookie(cookie)
                    .build();
            }
        }
    }

    @Path("/logout")
    @POST
    public ResultPack doLogout(@CookieParam("sessionID") String ckvalue) throws AuthenticationException {
        validateUser(ckvalue);
        try (JedisPool pool = new JedisPool("localhost", 6379)) {
            try (Jedis jedis = pool.getResource()) {
                // Redis から削除する
                jedis.del(ckvalue);

                // ログアウト結果を返す
                return new ResultPack(true, null);
            }
        }

    }
}
