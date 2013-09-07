package com.theapp.sms.cookies;

public class CookiesBuilder {

    private Cookies cookies = new Cookies();

    public static CookiesBuilder cookiesBuilder() {
        return new CookiesBuilder();
    }

    public CookiesBuilder withCookie(String name, String value) {
        cookies.put(name, value);
        return this;
    }

    public Cookies build() {
        return cookies;
    }
}
