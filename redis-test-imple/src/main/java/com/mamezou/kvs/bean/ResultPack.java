package com.mamezou.kvs.bean;

public class ResultPack {
    private boolean result;
    private String reason;

    public ResultPack() {}

    public ResultPack(boolean result, String reason) {
        this.result = result;
        this.reason = reason;
    }

    public boolean getResult() {
        return result;
    }

    public String getReason() {
        return reason;
    }
}
