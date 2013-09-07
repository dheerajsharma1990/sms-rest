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
import static com.theapp.sms.connection.ConnectionUtils.getLine;
import static com.theapp.sms.connection.ConnectionUtils.getReader;
import static com.theapp.sms.connection.ConnectionUtils.isValidResponseCode;
import static com.theapp.sms.utils.Utils.isEmpty;

public class Registration {

    private String regUrl;
    private Params params;
    private Cookies cookies;

    public Registration(String regUrl, Params params, Cookies cookies) {
        this.regUrl = regUrl;
        this.params = params;
        this.cookies = cookies;
    }

    public Response getRegistrationResponse() {
        Response response = new Response();
        try {
            HttpURLConnection connection = ConnectionFactory.newMockBrowserConnection(regUrl, cookies.toString());
            Poster poster = new Poster();
            poster.post(connection, params);
            Timer timer = new Timer("Time taken to read registration response:");
            timer.startTimer();
            if (isValidResponseCode(connection)) {
                BufferedReader reader = getReader(connection, 102400);
                String line;
                String toLowerCase;
                String errorMessage;
                while ((line = getLine(reader)) != null) {
                    toLowerCase = line.toLowerCase();
                    if (toLowerCase.contains("id=\"fielderror\"")) {
                        errorMessage = extractErrorMessage(reader, line, "</div>");
                        response.setRegistrationError(errorMessage);
                        break;
                    }
                }
            }
            System.out.println(timer.endTimer());
        } catch (TheAppException e) {
            response.setExceptioned(true);
            e.printStackTrace();
        }
        return response;
    }

    private String extractErrorMessage(BufferedReader reader, String line, String endPattern) throws TheAppException {
        String errorBox = getErrorAsSingleString(reader, line, endPattern);
        int index1 = errorBox.toLowerCase().indexOf("fielderror");
        int index2 = errorBox.indexOf("<span>", index1);
        int index3 = errorBox.indexOf("</span>", index2);
        return getErrorMessageProper(errorBox.substring(index2, index3));
    }

    private String getErrorAsSingleString(BufferedReader reader, String line, String pattern) throws TheAppException {
        StringBuilder builder = new StringBuilder();
        while (line != null && !line.contains(pattern)) {
            builder.append(line);
            line = getLine(reader);
        }
        if (line != null) {
            builder.append(line);
        }
        return builder.toString();
    }

    private String getErrorMessageProper(String errorFromServer) {
        if (isEmpty(errorFromServer)) {
            return Constants.REGISTRATION_ERROR;
        }

        if (errorFromServer.toLowerCase().contains("mobile") && errorFromServer.toLowerCase().contains("register")) {
            return Constants.MOBILE_NUMBER_REGISTERED;
        } else if (errorFromServer.toLowerCase().contains("invalid") && errorFromServer.toLowerCase().contains("mobile")) {
            return Constants.INVALID_MOBILE_NUMBER;
        } else if (errorFromServer.toLowerCase().contains("text") && errorFromServer.toLowerCase().contains("does") && errorFromServer.toLowerCase().contains("match")) {
            return Constants.INVALID_CAPTCHA_ENTERED;
        } else if (errorFromServer.toLowerCase().contains("failed") && errorFromServer.toLowerCase().contains("try")) {
            return Constants.REGISTRATION_ERROR;
        }

        return Constants.REGISTRATION_ERROR;
    }
}

