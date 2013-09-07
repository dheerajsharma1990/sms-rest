package com.theapp.sms.processors;

import com.theapp.sms.connection.ConnectionFactory;
import com.theapp.sms.exceptions.TheAppException;
import com.theapp.sms.utils.Timer;
import com.theapp.sms.utils.URLS;
import java.io.BufferedReader;
import java.net.HttpURLConnection;
import static com.theapp.sms.connection.ConnectionUtils.*;
import static com.theapp.sms.utils.Utils.extractValue;

public class CaptchaReader {

    private String captchaContainingUrl;

    public CaptchaReader(String captchaContainingUrl) {
        this.captchaContainingUrl = captchaContainingUrl;
    }

    public String getCaptchaUrl() throws TheAppException {
        String captchaUrl = null;
        HttpURLConnection connection = ConnectionFactory.newMockBrowserConnection(captchaContainingUrl);
        Timer timer = new Timer("Time taken to read Captcha URL from response:");
        timer.startTimer();
        if (isValidResponseCode(connection)) {
            BufferedReader reader = getReader(connection, 102400);
            System.out.println(getCookies(connection));
            String line;
            String toLower;
            while ((line = getLine(reader)) != null) {
                toLower = line.toLowerCase();
                if (toLower.contains("<img")) {
                    String tag = getTag(reader, line, "<img");
                    if (tag != null && tag.toLowerCase().contains("captcha") && tag.toLowerCase().contains("capture")) {
                        captchaUrl = URLS.BASE_URL + extractValue(tag, "src");
                        break;
                    }
                }
            }

        }
        connection.disconnect();
        System.out.println(timer.endTimer());
        return captchaUrl;
    }
}
