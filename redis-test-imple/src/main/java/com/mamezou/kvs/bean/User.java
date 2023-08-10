package com.mamezou.kvs.bean;

import java.util.Map;
import java.util.HashMap;

public class User {
    private Integer id;
    private String email;
    private String name;
    private String password;

    public User() {}

    public User(Integer id, String email, String name, String password) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.password = password;
    }

    public User(Map<String, String> map) {
        id = Integer.parseInt(map.get("id"));
        email = map.get("email");
        name = map.get("name");
    }

    public Integer getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public Map<String, String> getMap() {
        Map<String, String> map = new HashMap<>();
        map.put("id", Integer.toString(id));
        map.put("email", email);
        map.put("name", name);
        return map;
    }
}
