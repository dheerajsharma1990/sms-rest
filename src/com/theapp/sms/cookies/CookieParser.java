package com.theapp.sms.cookies;

import java.util.List;

public class CookieParser {

    public static Cookies parse(List<String> cookieSet) {
        CookiesBuilder builder = CookiesBuilder.cookiesBuilder();
        String parsedCookie[], key, value;
        for (String cookieKeyValue : cookieSet) {
            parsedCookie = cookieKeyValue.split("=");
            key = parsedCookie[0];
            value = parsedCookie[1].substring(0, parsedCookie[1].indexOf(";"));
            builder.withCookie(key, value);
        }
        return builder.build();
    }
}
