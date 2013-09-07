package com.theapp.sms.cookies;

import org.testng.annotations.Test;
import static com.theapp.sms.cookies.CookiesBuilder.cookiesBuilder;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class CookiesTest {
    @Test
    public void shouldGetCookieString() {
        Cookies cookies = cookiesBuilder()
                .withCookie("JSESSIONID", "124~3023049i23235.3814")
                .withCookie("BLAH", "BLUH").build();
        assertThat(cookies.toString(), is("JSESSIONID=124~3023049i23235.3814;BLAH=BLUH;"));
    }
}
