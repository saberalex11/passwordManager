package com.example.passwordmanager.entity;

import org.litepal.crud.LitePalSupport;

public class RootPassword extends LitePalSupport {

    private String rootPassword;

    public RootPassword(){}

    public String getRootPassword() {
        return rootPassword;
    }

    public void setRootPassword(String rootPassword) {
        this.rootPassword = rootPassword;
    }
}
