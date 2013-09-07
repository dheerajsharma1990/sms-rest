package com.theapp.sms.processors;

import com.theapp.sms.cookies.Cookies;
import com.theapp.sms.params.Params;
import com.theapp.sms.params.ParamsBuilder;
import com.theapp.sms.response.Response;
import com.theapp.sms.utils.URLS;
import org.testng.annotations.Test;
import com.theapp.sms.connection.ConnectionFactoryTest;
import com.theapp.sms.exceptions.TheAppException;
import com.theapp.sms.utils.UtilsTest;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;

public class LoginAuthenticationTest {

    @Test
    public void shouldGetLastLoginCookie() throws TheAppException {
        Params params = ParamsBuilder.loginParamBuilder()
                .withUsername(UtilsTest.USERNAME)
                .withPassword(UtilsTest.PASSWORD).build();
        LoginAuthentication loginAuthentication = new LoginAuthentication(URLS.LOGIN_URL, params);
        Response response = loginAuthentication.getResponse();
        Cookies cookies = response.getCookies();
        assertThat(cookies.get("LastLoginCookie"), notNullValue());
        assertThat(cookies.get(ConnectionFactoryTest.JSESSIONID), notNullValue());
    }

    @Test
    public void shouldGetOnlyJsessionCookie() throws TheAppException {
        Params params = ParamsBuilder.loginParamBuilder()
                .withUsername(UtilsTest.USERNAME)
                .withPassword("235j@~**324").build();
        LoginAuthentication loginAuthentication = new LoginAuthentication(URLS.LOGIN_URL, params);
        Response response = loginAuthentication.getResponse();
        Cookies cookies = response.getCookies();
        assertThat(cookies.get("LastLoginCookie"), nullValue());
        assertThat(cookies.get(ConnectionFactoryTest.JSESSIONID), notNullValue());
    }

}
