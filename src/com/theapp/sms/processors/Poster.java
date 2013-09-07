package com.theapp.sms.processors;

import com.theapp.sms.exceptions.ExceptionType;
import com.theapp.sms.exceptions.TheAppException;
import com.theapp.sms.params.Params;
import com.theapp.sms.utils.Utils;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.ProtocolException;

public class Poster {

    public HttpURLConnection post(HttpURLConnection connection, Params params) throws TheAppException {
        String queryString = params.getParamsAsString();
        System.out.println("Query String: " + queryString);
        postQuery(connection, queryString);
        return connection;
    }

    private void postQuery(HttpURLConnection connection,
                           String query) throws TheAppException {

        if (Utils.isEmpty(query)) {
            throw new TheAppException(ExceptionType.INVALID_POST_QUERY, query);
        }

        String method = "POST";
        try {
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            connection.setRequestProperty("Content-Length", String.valueOf(query.length()));
            connection.setRequestMethod(method);
            PrintWriter writer;
            writer = new PrintWriter(new OutputStreamWriter(
                    connection.getOutputStream()), true);
            writer.print(query);
            writer.flush();
            writer.close();
        } catch (ProtocolException e) {
            throw new TheAppException(ExceptionType.PROBLEM_WTH_PROTOCOL, method);
        } catch (IOException e) {
            throw new TheAppException(e);
        }
    }
}
