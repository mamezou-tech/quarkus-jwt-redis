package com.mamezou.kvs.bean;

public class ResultUser {
    private boolean result;
    private User user;

    public ResultUser() {}

    public ResultUser(boolean result, User user) {
        this.result = result;
        this.user = user;
    }

    public boolean getResult() {
        return result;
    }

    public User getUser() {
        return user;
    }
}
