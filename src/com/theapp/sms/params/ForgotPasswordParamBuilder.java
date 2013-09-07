package com.theapp.sms.params;
import java.util.Collections;

public class ForgotPasswordParamBuilder {

    private Params params = new Params();

    public ForgotPasswordParamBuilder withMobileNumber(String mobileNumber) {
        params.put("pass_mob1", mobileNumber);
        return this;
    }

    public ForgotPasswordParamBuilder withCaptcha(String captcha) {
        params.put("nlpAnswer", captcha);
        return this;
    }

    public Params build() {
        params.put("hfGUCode", "");
        params.put("pnum", "Enter Mobile Number");
        Collections.unmodifiableMap(params);
        return params;
    }
}
