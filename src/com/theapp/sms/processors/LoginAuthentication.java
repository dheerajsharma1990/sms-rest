package com.theapp.sms.processors;

import java.net.HttpURLConnection;

import com.theapp.sms.cookies.Cookies;
import com.theapp.sms.params.Params;
import com.theapp.sms.connection.*;
import com.theapp.sms.exceptions.TheAppException;
import com.theapp.sms.response.Response;
import com.theapp.sms.utils.Timer;

public class LoginAuthentication implements ResponseMaker {

    private String loginUrl;
    private Params params;

    public LoginAuthentication(String loginUrl, Params params) {
        this.loginUrl = loginUrl;
        this.params = params;
    }

    @Override
    public Response getResponse() {
        Response response = new Response();
        try {
            HttpURLConnection connection = ConnectionFactory.newMockBrowserConnection(loginUrl);
            connection.setInstanceFollowRedirects(false);
            Poster poster = new Poster();
            poster.post(connection, params);
            Timer timer = new Timer("Time taken to login:");
            timer.startTimer();
            if (ConnectionUtils.isValidResponseCode(connection)) {
                Cookies cookies = ConnectionUtils
                        .getCookies(connection);
                response.setCookies(cookies);
            }
            System.out.println(timer.endTimer());
        } catch (TheAppException e) {
            response.setExceptioned(true);
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
            response.setExceptioned(true);
        }

        return response;
    }

}
