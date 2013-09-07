package com.theapp.sms.processors;

import com.theapp.sms.connection.ConnectionFactory;
import com.theapp.sms.cookies.Cookies;
import com.theapp.sms.exceptions.TheAppException;
import com.theapp.sms.params.Params;
import com.theapp.sms.response.Response;
import com.theapp.sms.utils.Constants;
import com.theapp.sms.utils.Timer;
import java.io.BufferedReader;
import java.net.HttpURLConnection;

import static com.theapp.sms.connection.ConnectionUtils.*;

public class ForgotPassword implements ResponseMaker {

    private String forgotPasswordUrl;
    private Params params;
    private Cookies cookies;

    public ForgotPassword(String forgotPasswordUrl, Params params, Cookies cookies) {
        this.forgotPasswordUrl = forgotPasswordUrl;
        this.params = params;
        this.cookies = cookies;
    }

    @Override
    public Response getResponse() {
        Response response = new Response();
        try {
            Poster poster = new Poster();
            HttpURLConnection connection = ConnectionFactory.newMockBrowserConnection(forgotPasswordUrl, cookies.toString());
            connection.setInstanceFollowRedirects(false);
            poster.post(connection, params);
            Timer timer = new Timer("Time taken to process forget password response:");
            timer.startTimer();
            String location;
            if ((location = getLocationUrl(connection)) != null) {
                HttpURLConnection connection1 = ConnectionFactory.newMockBrowserConnection(location,
                        cookies.toString());
                BufferedReader reader = getReader(connection1, 102400);
                String line;
                String message;
                while ((line = getLine(reader)) != null) {
                    if ((message = getResponseMessage(line)) != null) {
                        response.setForgotPasswordResult(message);
                        break;
                    }
                }
            }
            System.out.println(timer.endTimer());
        } catch (TheAppException e) {
            response.setExceptioned(true);
            e.printStackTrace();
        } catch (Exception e) {
            response.setExceptioned(true);
            e.printStackTrace();
        }
        return response;
    }


    private String getResponseMessage(String line) {
        line = line.toLowerCase();
        if (line.contains("<") && line.contains("password") && line.contains("sent")) {
            return Constants.PASSWORD_SENT;
        } else if (line.contains("<") && line.contains("mobile") && line.contains("does") && line.contains("exist")) {
            return Constants.MOBILE_NOT_REGISTERED;
        } else if (line.contains("<") && line.contains("mobile") && line.contains("format")) {
            return Constants.INVALID_MOBILE_NUMBER;
        } else if (line.contains("<") && line.contains("text") && line.contains("match")) {
            return Constants.INVALID_CAPTCHA_ENTERED;
        } else {
            return null;
        }
    }
}
