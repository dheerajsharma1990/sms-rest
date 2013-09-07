package com.theapp.sms.params;

import java.util.Collections;

public class RegistrationParamBuilder {

    private Params params = new Params();

    public RegistrationParamBuilder withName(String name) {
        params.put("userName", name);
        return this;
    }

    public RegistrationParamBuilder withMobile(String mobile) {
        params.put("mobileNo", mobile);
        return this;
    }

    public RegistrationParamBuilder withEmail(String email) {
        params.put("emailId", email);
        return this;
    }

    public RegistrationParamBuilder withDayOfBirth(String dayOfBirth) {
        params.put("dob_day", dayOfBirth);
        return this;
    }

    public RegistrationParamBuilder withMonthOfBirth(String monthOfBirth) {
        params.put("dob_month", monthOfBirth);
        return this;
    }

    public RegistrationParamBuilder withYearOfBirth(String yearOfBirth) {
        params.put("dob_year", yearOfBirth);
        return this;
    }

    public RegistrationParamBuilder withGender(String gender) {
        params.put("gender", gender);
        return this;
    }

    public RegistrationParamBuilder withCity(String city) {
        params.put("city", city);
        return this;
    }

    public RegistrationParamBuilder withCaptcha(String captcha) {
        params.put("capture", captcha);
        return this;
    }

    public Params build() {
        params.put("hid_capture", "");
        params.put("cterms", "on");
        Collections.unmodifiableMap(params);
        return params;
    }
}
