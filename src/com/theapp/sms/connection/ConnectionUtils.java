package com.theapp.sms.connection;

import java.io.*;
import java.net.HttpURLConnection;
import java.util.List;
import java.util.Map;
import java.util.zip.GZIPInputStream;

import com.theapp.sms.cookies.CookieParser;
import com.theapp.sms.cookies.Cookies;
import com.theapp.sms.exceptions.TheAppException;
import com.theapp.sms.utils.Utils;

public class ConnectionUtils {

    public static Cookies getCookies(HttpURLConnection connection) {
        List<String> cookieSet = getHeader(connection, "set-cookie");
        return cookieSet != null ? CookieParser.parse(cookieSet) : null;
    }

    public static String getLocationUrl(HttpURLConnection connection) {
        List<String> headerList = getHeader(connection, "location");
        return headerList != null ? headerList.get(0) : null;
    }

    public static boolean isValidResponseCode(HttpURLConnection connection) throws TheAppException {
        try {
            return Utils.isValidCode(connection.getResponseCode());
        } catch (IOException e) {
            throw new TheAppException(e);
        }
    }

    public static BufferedReader getReader(HttpURLConnection connection, int bufferSize) throws TheAppException {
        try {
            if (isConnectionGzip(connection)) {
                return new BufferedReader(new InputStreamReader(new GZIPInputStream(connection.getInputStream())), bufferSize);
            }
            return new BufferedReader(new InputStreamReader(connection.getInputStream()), bufferSize);
        } catch (IOException e) {
            throw new TheAppException(e);
        }
    }

    public static BufferedReader getReader(HttpURLConnection connection) throws TheAppException {
        try {
            return new BufferedReader(new InputStreamReader(connection.getInputStream()));
        } catch (IOException e) {
            throw new TheAppException(e);
        }
    }

    public static String getLine(BufferedReader reader) throws TheAppException {
        try {
            return reader.readLine();
        } catch (IOException e) {
            throw new TheAppException(e);
        }
    }


    public static boolean isConnectionGzip(HttpURLConnection connection) {
        List<String> encodings = getContentEncoding(connection);
        return encodings != null && encodings.toString().toLowerCase().contains("gzip");
    }

    public static String getTag(BufferedReader reader, String line, String tag) throws TheAppException {
        line = line.substring(line.indexOf(tag));
        StringBuilder builder = new StringBuilder();
        while (line != null && (line.indexOf(">")) == -1) {
            builder.append(line);
            line = getLine(reader);
        }

        if (line != null) {
            builder.append(line);
        }

        String midTag = builder.toString();
        String firstPart = midTag.substring(0, midTag.indexOf(">") + 1);
        String secondPart = midTag.substring(firstPart.length());
        StringBuilder secondBuilder = new StringBuilder();
        if (firstPart.contains("/>")) {
            return firstPart;
        } else {
            while (secondPart != null && !secondPart.contains(tag.substring(1) + ">")) {
                secondBuilder.append(secondPart);
                secondPart = getLine(reader);
            }

            if (secondPart != null) {
                secondBuilder.append(secondPart);
            }

            String secondString = secondBuilder.toString();
            return firstPart + secondString.substring(0, secondString.indexOf(tag.substring(1) + ">") + 1);
        }

    }

    private static List<String> getContentEncoding(HttpURLConnection connection) {
        Map<String, List<String>> headerFields = connection.getHeaderFields();
        String key;
        for (Map.Entry<String, List<String>> header : headerFields.entrySet()) {
            key = header.getKey();
            if (key != null && key.toLowerCase().trim().equals("content-encoding")) {
                return header.getValue();
            }
        }
        return null;
    }

    private static List<String> getHeader(HttpURLConnection connection, String headerName) {
        Map<String, List<String>> headerFields = connection.getHeaderFields();
        String key;
        for (Map.Entry<String, List<String>> header : headerFields.entrySet()) {
            key = header.getKey();
            if (key != null && key.toLowerCase().trim().equals(headerName.toLowerCase())) {
                return header.getValue();
            }
        }
        return null;
    }

}
