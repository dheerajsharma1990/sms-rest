package com.theapp.sms.processors;

import com.theapp.sms.cookies.Cookies;
import com.theapp.sms.params.Params;
import com.theapp.sms.params.ParamsBuilder;
import com.theapp.sms.response.Response;
import com.theapp.sms.utils.Constants;
import com.theapp.sms.utils.URLS;
import org.testng.annotations.Test;
import static com.theapp.sms.cookies.CookiesBuilder.cookiesBuilder;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class ForgotPasswordTest {

    @Test
    public void shouldGetPasswordOnMobile() {
        Params params = ParamsBuilder.forgotPasswordParamBuilder()
                .withMobileNumber("9999770595")
                .withCaptcha("R81z3y")
                .build();
        Cookies cookies = cookiesBuilder().withCookie("JSESSIONID", "CC~D9BCE3B53ADFC0DA0AAE45267B69A297.8503").build();
        ForgotPassword forgotPassword = new ForgotPassword(URLS.FORGOT_PASSWORD_ACTION_URL, params, cookies);
        Response response = forgotPassword.getResponse();
        assertThat(response.getForgotPasswordResult(), is(Constants.PASSWORD_SENT));
    }

    @Test
    public void shouldGetMobileNumberDoesNotExist() {
        Params params = ParamsBuilder.forgotPasswordParamBuilder()
                .withMobileNumber("9811289448")
                .withCaptcha("jpajde")
                .build();
        Cookies cookies = cookiesBuilder()
                .withCookie("JSESSIONID", "OO~9BCC8707615E774107066F704024DEB2.8515").build();
        ForgotPassword forgotPassword = new ForgotPassword(URLS.FORGOT_PASSWORD_ACTION_URL, params, cookies);
        Response response = forgotPassword.getResponse();
        assertThat(response.getForgotPasswordResult(), is(Constants.MOBILE_NOT_REGISTERED));
    }

    @Test
    public void shouldGetInvalidCaptcha() {
        Params params = ParamsBuilder.forgotPasswordParamBuilder()
                .withMobileNumber("9999770595")
                .withCaptcha("blablah")
                .build();
        Cookies cookies = cookiesBuilder()
                .withCookie("JSESSIONID", "9G~8C816DF5EACDE28DC0D4816F9A6DF7D9.8707").build();
        ForgotPassword forgotPassword = new ForgotPassword(URLS.FORGOT_PASSWORD_ACTION_URL, params, cookies);
        Response response = forgotPassword.getResponse();
        assertThat(response.getForgotPasswordResult(), is(Constants.INVALID_CAPTCHA_ENTERED));
    }

}
