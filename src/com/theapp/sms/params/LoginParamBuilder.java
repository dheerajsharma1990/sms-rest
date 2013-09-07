package com.theapp.sms.params;

public class LoginParamBuilder {

    private Params params = new Params();

    public LoginParamBuilder withUsername(String username) {
        params.put("username", username);
        return this;
    }

    public LoginParamBuilder withPassword(String password) {
        params.put("password", password);
        return this;
    }

    public Params build() {
        return params;
    }

}
