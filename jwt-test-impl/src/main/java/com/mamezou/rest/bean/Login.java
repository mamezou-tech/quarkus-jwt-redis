package com.mamezou.rest.bean;

public class Login {
    public String email;
    public String password;

    public Login() {}

    public Login(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public boolean equals(Login another) {
        if (this.email == null) {
            if (another.email == null) {
                return true;
            }
            return false;
        }
        if (this.password == null) {
            return false;
        }
        return this.email.equals(another.email) && this.password.equals(another.password);
    }
}
