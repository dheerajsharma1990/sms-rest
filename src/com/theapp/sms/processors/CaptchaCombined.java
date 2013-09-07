package com.theapp.sms.processors;

import com.theapp.sms.exceptions.TheAppException;
import com.theapp.sms.response.Response;

public class CaptchaCombined implements ResponseMaker {

    private String captchaContainingUrl;

    public CaptchaCombined(String captchaContainingUrl) {
        this.captchaContainingUrl = captchaContainingUrl;
    }

    @Override
    public Response getResponse() {
        try {
            CaptchaReader captchaReader = new CaptchaReader(captchaContainingUrl);
            String urlString = captchaReader.getCaptchaUrl();
            return new CaptchaImage(urlString).getResponse();
        } catch (TheAppException e) {
            Response response = new Response();
            response.setExceptioned(true);
            return response;
        }
    }
}
