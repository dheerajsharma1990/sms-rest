package com.theapp.sms.response;

import com.theapp.sms.connection.ConnectionFactoryTest;
import com.theapp.sms.cookies.Cookies;
import org.testng.annotations.Test;

import static com.theapp.sms.cookies.CookiesBuilder.cookiesBuilder;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import com.theapp.sms.utils.Constants;


public class LoginResponseTest {

    @Test
    public void shouldGetCorrectXMLResponse() {

        Response response = new Response();
        Cookies cookies = cookiesBuilder().withCookie(ConnectionFactoryTest.JSESSIONID, "2340915093205432~22.8514")
                .withCookie("LastLoginCookie", "THurdsay blah blah").build();
        response.setCookies(cookies);
        LoginResponse loginResponse = new LoginResponse(response);
        String xmlResponse = loginResponse.getXmlResponse();
        assertThat(xmlResponse.contains("<loginSuccessful>true</loginSuccessful>"), is(true));
        assertThat(xmlResponse.contains("<exception></exception>"), is(true));
    }

    @Test
    public void shouldGetInvalidLogin() {

        Response response = new Response();
        Cookies cookies = cookiesBuilder().withCookie(ConnectionFactoryTest.JSESSIONID, "2340915093205432~22.8514")
                .build();
        response.setCookies(cookies);
        response.setExceptioned(false);
        LoginResponse loginResponse = new LoginResponse(response);
        String xmlResponse = loginResponse.getXmlResponse();
        assertThat(xmlResponse.contains("<loginSuccessful>false</loginSuccessful>"), is(true));
        assertThat(xmlResponse.contains("<exception>" + Constants.INVALID_LOGIN + "</exception>"), is(true));
    }

    @Test
    public void shouldGetServerException() {

        Response response = new Response();
        response.setExceptioned(true);
        LoginResponse loginResponse = new LoginResponse(response);
        String xmlResponse = loginResponse.getXmlResponse();
        assertThat(xmlResponse.contains("<loginSuccessful>false</loginSuccessful>"), is(true));
        assertThat(xmlResponse.contains("<exception>" + Constants.LOGIN_ERROR_AT_SERVER + "</exception>"), is(true));
    }

}
