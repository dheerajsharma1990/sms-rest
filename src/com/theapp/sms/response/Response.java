package com.theapp.sms.response;

import com.theapp.sms.cookies.Cookies;

public class Response {

    private Cookies cookies;

    private boolean isExceptioned;

    private String captchaUrl;

    private String captchaImage;

    private String registrationError;

    private String forgotPasswordResult;

    public Response() {
    }

    public void setCookies(Cookies cookies) {
        this.cookies = cookies;
    }

    public Cookies getCookies() {
        return cookies;
    }

    public boolean isExceptioned() {
        return isExceptioned;
    }

    public void setExceptioned(boolean exceptioned) {
        isExceptioned = exceptioned;
    }

    public String getCaptchaImage() {
        return captchaImage;
    }

    public void setCaptchaImage(String captchaImage) {
        this.captchaImage = captchaImage;
    }

    public String getRegistrationError() {
        return registrationError;
    }

    public void setRegistrationError(String registrationError) {
        this.registrationError = registrationError;
    }

    public String getForgotPasswordResult() {
        return forgotPasswordResult;
    }

    public void setForgotPasswordResult(String forgotPasswordResult) {
        this.forgotPasswordResult = forgotPasswordResult;
    }

    public boolean isLoginValid() {
        boolean lastLoginCookie = false;
        if (cookies == null) {
            return false;
        }
        for (String key : cookies.keySet()) {
            if (key.toLowerCase().contains("last")
                    && key.toLowerCase().contains("login")) {
                lastLoginCookie = true;
                break;
            }
        }

        return lastLoginCookie;
    }

    public String getCaptchaImageResponse() {
        StringBuilder builder = new StringBuilder();
        builder.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>")
                .append("<response>");
        if (!isExceptioned()) {
            builder.append("<cookie>")
                    .append("<name>").append("JSESSIONID").append("</name>")
                    .append("<value>").append(cookies.get("JESESSIONID")).append("</value>")
                    .append("</cookie>")
                    .append("<image>")
                    .append(captchaImage)
                    .append("</image>");
        }
        builder.append("</response>");
        return builder.toString();
    }
}
