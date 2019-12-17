package com.example.passwordmanager.entity;

import org.litepal.annotation.Column;
import org.litepal.crud.LitePalSupport;

public class PasswordBean extends LitePalSupport {

    @Column(unique = true, defaultValue = "unknown")
    private String name;

    private String account;

    private String password;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
