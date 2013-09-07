package com.theapp.sms.connection;

import java.io.IOException;
import java.util.Map;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

import com.theapp.sms.cookies.Cookies;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import com.theapp.sms.exceptions.TheAppException;
import com.theapp.sms.utils.URLS;

public class ConnectionFactoryTest {

    private HttpURLConnection connection = null;
    private final String TIME_OUT_URL = "http://www.google.com:81/";
    public static final String JSESSIONID = "JSESSIONID";


    @Test(expectedExceptions = SocketTimeoutException.class)
    public void shouldTimeOutConnection() throws IOException, TheAppException {
        connection = ConnectionFactory.newMockBrowserConnection(TIME_OUT_URL);
        connection.connect();
    }

    @Test
    public void shouldHaveDifferentJsessions() throws TheAppException {
        connection = ConnectionFactory.newMockBrowserConnection(URLS.LOGIN_URL);
        Cookies cookies = ConnectionUtils.getCookies(connection);

        String jsession1 = cookies.get(JSESSIONID);

        connection = ConnectionFactory.newMockBrowserConnection(URLS.LOGIN_URL);
        cookies = ConnectionUtils.getCookies(connection);
        String jsession2 = cookies.get(JSESSIONID);

        assertThat(jsession1, notNullValue());
        assertThat(jsession2, notNullValue());
        assertThat(jsession1.equals(jsession2), is(false));


    }

    @AfterMethod
    public void closeConnection() {
        if (connection != null) {
            connection.disconnect();
        }
    }

}
