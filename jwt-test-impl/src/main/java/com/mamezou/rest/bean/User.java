package com.mamezou.rest.bean;

import java.util.Set;

public class User {
    public enum Groups {
        ADMIN("Admin"), USER("User");
        private String id;
        private Groups(String id) {
            this.id = id;
        }
        public String getId() {
            return id;
        }
    }
    private String email;
    private Set<Groups> groupId;
    private String password;

    public User() {}

    public User(String email, Set<Groups> groupId, String password) {
        this.email = email;
        this.groupId = groupId;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public Set<Groups> getGroupId() {
        return groupId;
    }

    public String getPassword() {
        return password;
    }

    public boolean equals(Object o) {
        User u = (User)o;
        if (u == null) {
            if (email == null) {
                return true;
            }
            return false;
        }
        return this.email.equals(u.email);
    }
}
