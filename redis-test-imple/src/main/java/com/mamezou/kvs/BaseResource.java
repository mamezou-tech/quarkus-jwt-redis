package com.mamezou.kvs;

import java.util.Map;

import javax.security.sasl.AuthenticationException;

import com.mamezou.kvs.bean.User;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class BaseResource {
    protected User validateUser(String ckvalue) throws AuthenticationException {
        try (JedisPool pool = new JedisPool("localhost", 6379)) {
            try (Jedis jedis = pool.getResource()) {
                // Redis から読み込む
                Map<String, String> map = jedis.hgetAll(ckvalue);
                if (map == null) {
                    // Redis にない場合はエラー
                    throw new AuthenticationException();
                }
                return new User(map);
            }
        }

    }
    
}
