package com.theapp.sms.connection;

import static com.theapp.sms.connection.ConnectionUtils.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.testng.Assert.fail;

import com.theapp.sms.utils.URLS;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.theapp.sms.exceptions.TheAppException;

import java.net.HttpURLConnection;
import java.util.Map;

public class ConnectionUtilsTest {

    private HttpURLConnection connection = null;

    @Test
    public void shouldGetJSessionCookie() {
        Map<String, String> cookieMap = getCookies(connection);
        assertThat(cookieMap.containsKey(ConnectionFactoryTest.JSESSIONID), is(true));
    }

    @Test
    public void shouldGetLocation() {
        connection.setInstanceFollowRedirects(false);
        assertThat(getLocationUrl(connection), is("http://www.160by2.com/Login"));
    }

    @Test
    public void shouldValidateResponseCode() throws TheAppException {
        boolean valid = isValidResponseCode(connection);
        assertThat(valid, is(true));
    }

    @Test
    public void shouldHaveGzipCompression() throws TheAppException {
        HttpURLConnection connection1 = ConnectionFactory.newMockBrowserConnection(URLS.REG_CAPTCHA_URL);
        if (isValidResponseCode(connection1)) {
            boolean isGzip = isConnectionGzip(connection1);
            assertThat(isGzip, is(true));
        } else {
            fail();
        }
    }

    @BeforeMethod
    public void setConnection() throws TheAppException {
        connection = ConnectionFactory.newMockBrowserConnection(URLS.LOGIN_URL);
    }

    @AfterMethod
    public void closeConnection() {
        if (connection != null) {
            connection.disconnect();
        }
    }

}
