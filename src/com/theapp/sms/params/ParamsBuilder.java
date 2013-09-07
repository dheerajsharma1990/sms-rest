package com.theapp.sms.params;


public class ParamsBuilder {

    public static LoginParamBuilder loginParamBuilder() {
        return new LoginParamBuilder();
    }

    public static RegistrationParamBuilder registrationParamBuilder() {
        return new RegistrationParamBuilder();
    }

    public static ForgotPasswordParamBuilder forgotPasswordParamBuilder() {
        return new ForgotPasswordParamBuilder();
    }

}
