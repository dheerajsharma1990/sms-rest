package com.theapp.sms.response;

import static com.theapp.sms.cookies.CookiesBuilder.cookiesBuilder;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import java.util.Map;
import java.util.HashMap;

import com.theapp.sms.cookies.Cookies;
import com.theapp.sms.cookies.CookiesBuilder;
import com.theapp.sms.response.Response;
import org.testng.annotations.Test;

public class ResponseTest {

    @Test
    public void shouldValidateLogin() {
        Cookies cookies = cookiesBuilder()
                .withCookie("JSESSIONID", "GG~FBECC79DFB449D7FA4B421EB36B4A875.8507")
                .withCookie("LastLoginCookie",
                        "24-08-2013-901-17:34-901-Mozilla FireFox 3.0.5-901-101.58.158.238-901-19-08-2012").build();
        Response response = new Response();
        response.setCookies(cookies);
        assertThat(response.isLoginValid(), is(true));
    }

    @Test
    public void shouldNotValidateLogin() {
        Cookies cookies = cookiesBuilder()
                .withCookie("JSESSIONID", "GG~FBECC79DFB449D7FA4B421EB36B4A875.8507")
                .build();
        Response response = new Response();
        response.setCookies(cookies);
        assertThat(response.isLoginValid(), is(false));
    }

    @Test
    public void shouldNotValidateLoginWithZeroCookies() {
        Cookies cookies = cookiesBuilder().build();
        Response response = new Response();
        response.setCookies(cookies);
        assertThat(response.isLoginValid(), is(false));
    }

}
