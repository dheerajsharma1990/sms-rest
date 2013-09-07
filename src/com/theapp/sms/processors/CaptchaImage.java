package com.theapp.sms.processors;

import com.theapp.sms.connection.ConnectionFactory;
import com.theapp.sms.cookies.Cookies;
import com.theapp.sms.exceptions.TheAppException;
import com.theapp.sms.response.Response;
import com.theapp.sms.utils.Timer;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;

import static com.theapp.sms.connection.ConnectionUtils.getCookies;
import static com.theapp.sms.connection.ConnectionUtils.isValidResponseCode;

public class CaptchaImage implements ResponseMaker {

    private String captchaImageUrl;

    public CaptchaImage(String captchaImageUrl) {
        this.captchaImageUrl = captchaImageUrl;
    }

    @Override
    public Response getResponse() {
        Response response = new Response();
        try {
            HttpURLConnection connection = ConnectionFactory.newMockBrowserConnection(captchaImageUrl);
            Timer timer = new Timer("Time taken to load captcha:");
            timer.startTimer();
            if (isValidResponseCode(connection)) {
                Cookies cookies = getCookies(connection);
                String image = encodeImage(connection);
                System.out.println(image);
                response.setCookies(cookies);
                response.setCaptchaImage(image);
            }
            System.out.println(timer.endTimer());
        } catch (TheAppException e) {
            response.setExceptioned(true);
        }
        return response;
    }

    private String encodeImage(HttpURLConnection connection) throws TheAppException {
        try {
            byte[] imageAsByte = IOUtils.toByteArray(new BufferedInputStream(connection.getInputStream()));
            return encodeImageData(imageAsByte);
        } catch (IOException e) {
            throw new TheAppException(e);
        }
    }

    private String encodeImageData(byte[] imageData) {
        return Base64.encodeBase64URLSafeString(imageData);
    }

}
