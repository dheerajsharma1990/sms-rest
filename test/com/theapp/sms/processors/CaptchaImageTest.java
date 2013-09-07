package com.theapp.sms.processors;

import com.theapp.sms.response.Response;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class CaptchaImageTest {

    @Test
    public void shouldGetValidResponse() {
        String captchaUrl = "http://www.160by2.com/./CaptchaServlet.chk?capNum=19129";
        CaptchaImage captchaResponse = new CaptchaImage(captchaUrl);
        Response response = captchaResponse.getResponse();
        assertThat(response.getCookies().get("JSESSIONID"), notNullValue());
        assertThat(response.getCaptchaImage(), notNullValue());
        assertThat(response.isExceptioned(), is(false));
    }
}
