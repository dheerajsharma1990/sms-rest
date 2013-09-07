package com.theapp.sms.cookies;

import java.util.HashMap;
import java.util.Map;

public class Cookies extends HashMap<String, String> {

    @Override
    public String toString() {
        StringBuffer buffer = new StringBuffer();
        for (Map.Entry<String, String> entry : this.entrySet()) {
            buffer.append(entry.getKey());
            buffer.append("=");
            buffer.append(entry.getValue());
            buffer.append(";");
        }
        return buffer.toString();
    }
}
