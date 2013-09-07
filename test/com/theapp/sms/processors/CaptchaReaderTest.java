package com.theapp.sms.processors;

import com.theapp.sms.exceptions.TheAppException;
import com.theapp.sms.utils.URLS;
import org.testng.annotations.Test;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;

public class CaptchaReaderTest {

    @Test
    public void shouldCaptureCaptchaUrl() throws TheAppException {
        CaptchaReader reader = new CaptchaReader(URLS.REG_CAPTCHA_URL);
        String captchaUrl = reader.getCaptchaUrl();
        assertThat(captchaUrl.toLowerCase().contains("/./captchaservlet.chk"), is(true));
    }

    @Test
    public void shouldCaptureCaptchaUrlForgotPassword() throws TheAppException {
        CaptchaReader reader = new CaptchaReader(URLS.FORGOT_PASSWORD_URL);
        String captchaUrl = reader.getCaptchaUrl();
        assertThat(captchaUrl.toLowerCase().contains("/./captchaservlet.chk"), is(true));
    }

    @Test
    public void shouldReturnNullCaptchaUrl() throws TheAppException {
        CaptchaReader reader = new CaptchaReader(URLS.BASE_URL);
        String captchaUrl = reader.getCaptchaUrl();
        assertThat(captchaUrl, nullValue());
    }

}
