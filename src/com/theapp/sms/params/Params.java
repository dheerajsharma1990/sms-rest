package com.theapp.sms.params;

import com.theapp.sms.exceptions.ExceptionType;
import com.theapp.sms.exceptions.TheAppException;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class Params extends HashMap<String, String> {

    public String getParamsAsString() throws TheAppException {
        StringBuffer buffer = new StringBuffer();
        if (size() > 0) {
            String value;
            String name;
            for (Map.Entry<String, String> pair : entrySet()) {
                name = pair.getKey();
                value = getUTF8EncodedString(pair.getValue());
                buffer.append(name + "=" + value);
                buffer.append("&");
            }
            String queryString = buffer.toString();
            return queryString.substring(0, queryString.length() - 1);

        }
        return buffer.toString();
    }

    private String getUTF8EncodedString(String value) throws TheAppException {
        try {
            return URLEncoder.encode(value, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new TheAppException(ExceptionType.ENCODING_ERROR, value);
        }
    }
}
