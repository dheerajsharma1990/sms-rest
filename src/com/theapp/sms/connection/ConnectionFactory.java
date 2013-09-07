package com.theapp.sms.connection;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import com.theapp.sms.exceptions.ExceptionType;
import com.theapp.sms.exceptions.TheAppException;
import com.theapp.sms.utils.URLS;

public class ConnectionFactory {

    public static HttpURLConnection newMockBrowserConnection(String urlString, String cookies)
            throws TheAppException {
        HttpURLConnection connection = newConnection(urlString);
        connection.setRequestProperty("Cookie", cookies);
        return mockAsBrowser(connection);
    }

    public static HttpURLConnection newMockBrowserConnection(String urlString)
            throws TheAppException {
        HttpURLConnection connection = newConnection(urlString);
        return mockAsBrowser(connection);
    }

    private static HttpURLConnection newConnection(String urlString)
            throws TheAppException {
        try {
            URL url = new URL(urlString);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url
                    .openConnection();
            httpURLConnection.setConnectTimeout(10000);
            return httpURLConnection;
        } catch (MalformedURLException e) {
            throw new TheAppException(ExceptionType.INVALID_URL_SPECIFIED,
                    urlString);
        } catch (IOException e) {
            throw new TheAppException(e);
        }
    }

    private static HttpURLConnection mockAsBrowser(HttpURLConnection connection) {
        connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 6.0; " +
                "en-US; rv:1.9.0.5) Gecko/2008120122 Firefox/3.0.5");
        connection.setRequestProperty("Accept", "*/*");
        connection.setRequestProperty("Accept-Encoding", "gzip,deflate,sdch");
        return connection;
    }

    public static void testConnection(){
        try {
            HttpURLConnection connection1 = (HttpURLConnection) new URL(URLS.FORGOT_PASSWORD_URL).openConnection();
            connection1.getResponseCode();
            HttpURLConnection connection2 = (HttpURLConnection) new URL(URLS.FORGOT_PASSWORD_URL).openConnection();
            connection2.getResponseCode();
            System.out.println("end");
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }
}
