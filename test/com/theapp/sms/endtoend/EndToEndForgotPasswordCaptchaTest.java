package com.theapp.sms.endtoend;

import com.theapp.sms.exceptions.TheAppException;
import org.testng.annotations.Test;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class EndToEndForgotPasswordCaptchaTest {

    private final String SERVER_URL = "http://localhost:8080/theapp/by2/forgotCaptcha";

    @Test
    public void shouldLoginSuccessfully() throws IOException, TheAppException {
        URL url = new URL(SERVER_URL);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setDoOutput(true);
        connection.setRequestMethod("GET");

        String response = getResponse(connection);
        String RESPONSE = "<?xml version=\"1.0\" encoding=\"utf-8\"?>"
                + "<response>"
                + "<loginSuccessful>true</loginSuccessful>"
                + "<messageSent>false</messageSent>"
                + "<exception></exception>"
                + "</response>";
        assertThat(response, is(RESPONSE));

    }

    private String getResponse(HttpURLConnection connection) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(
                connection.getInputStream()));
        StringBuilder builder = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            builder.append(line);
        }
        return builder.toString();
    }

}
