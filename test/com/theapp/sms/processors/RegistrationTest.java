package com.theapp.sms.processors;

import com.theapp.sms.cookies.Cookies;
import com.theapp.sms.exceptions.TheAppException;
import com.theapp.sms.params.Params;
import com.theapp.sms.params.RegistrationParamBuilder;
import com.theapp.sms.response.Response;
import com.theapp.sms.utils.Constants;
import com.theapp.sms.utils.URLS;
import org.apache.commons.io.IOUtils;
import org.testng.annotations.Test;
import java.io.*;
import static com.theapp.sms.cookies.CookiesBuilder.cookiesBuilder;
import static com.theapp.sms.params.ParamsBuilder.registrationParamBuilder;
import static com.theapp.sms.utils.Utils.decodeImageData;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class RegistrationTest {

    @Test
    public void shouldShowMobileNumberAlreadyRegistered() {
        RegistrationParamBuilder builder = getRegistrationParams();
        builder.withCaptcha("XPD1UW");
        Params params = builder.build();
        Cookies cookies = cookiesBuilder().withCookie("JSESSIONID", "9D~54768054F655E53E04F6E8A5A6EE8A36.8704").build();
        checkErrorType(params, cookies, Constants.MOBILE_NUMBER_REGISTERED);
    }

    @Test
    public void shouldShowMobileNumberInvalid() {
        RegistrationParamBuilder builder = getRegistrationParams();
        builder.withMobile("9999");
        builder.withCaptcha("XPD1UW");
        Params params = builder.build();
        Cookies cookies = cookiesBuilder().withCookie("JSESSIONID", "9D~54768054F655E53E04F6E8A5A6EE8A36.8704").build();
        checkErrorType(params, cookies, Constants.INVALID_MOBILE_NUMBER);
    }

    @Test
    public void shouldShowCaptchaInvalid() {
        RegistrationParamBuilder builder = getRegistrationParams();
        builder.withCaptcha("sdlkfalkd");
        Params params = builder.build();
        Cookies cookies = cookiesBuilder().withCookie("JSESSIONID", "9D~54768054F655E53E04F6E8A5A6EE8A36.8704").build();
        checkErrorType(params, cookies, Constants.INVALID_CAPTCHA_ENTERED);
    }

    @Test
    public void shouldShowErrorInRegistration() {
        RegistrationParamBuilder builder = getRegistrationParams();
        builder.withCaptcha("sdlkfalkd");
        Params params = builder.build();
        Cookies cookies = cookiesBuilder().withCookie("JSESSIONID", "9A~C41111111111111.8111").build();
        checkErrorType(params, cookies, Constants.REGISTRATION_ERROR);
    }

    @Test
    public void setCookies() throws TheAppException, IOException {
        CaptchaReader reader = new CaptchaReader(URLS.REG_CAPTCHA_URL);
        String captchaUrl = reader.getCaptchaUrl();
        CaptchaImage captchaImage = new CaptchaImage(captchaUrl);
        Response response = captchaImage.getResponse();
        writeCaptcha(response.getCaptchaImage());
    }

    private void checkErrorType(Params params, Cookies cookies, String errorType) {
        Registration registration = new Registration(URLS.REG_URL, params, cookies);
        Response response = registration.getRegistrationResponse();
        assertThat(response.getRegistrationError(), is(errorType));
    }


    public RegistrationParamBuilder getRegistrationParams() {
        return registrationParamBuilder().withName("Dheeraj Sharma")
                .withMobile("9999770595")
                .withEmail("dheerajsharma1990@gmail.com")
                .withDayOfBirth("22")
                .withMonthOfBirth("09")
                .withYearOfBirth("1990")
                .withGender("M")
                .withCity("Faridabad");

    }

    private void writeCaptcha(String imageData) throws IOException {
        byte[] imageByteArray = decodeImageData(imageData);
        IOUtils.write(imageByteArray, new FileOutputStream(new File("G:\\image.png")));
    }

}
