package com.theapp.sms.processors;

import com.theapp.sms.response.Response;
import com.theapp.sms.utils.URLS;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;

public class CaptchaCombinedTest {

    @Test
    public void shouldGetCaptchaAndCookies() {
        CaptchaCombined captchaCombined = new CaptchaCombined(URLS.FORGOT_PASSWORD_URL);
        Response response = captchaCombined.getResponse();
        assertThat(response.getCookies().get("JSESSIONID"), notNullValue());
        assertThat(response.getCaptchaImage(), notNullValue());
    }
}
