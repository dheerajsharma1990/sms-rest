package com.theapp.sms.utils;

import org.apache.commons.codec.binary.Base64;

public class Utils {

    public static boolean isEmpty(String value) {
        return value == null || value.trim().equals("");
    }

    public static byte[] decodeImageData(String imageDataString) {
        return Base64.decodeBase64(imageDataString);
    }

    public static boolean isValidCode(int code) {
        String stringCode = String.valueOf(code);
        return isFirstCharValid(stringCode.charAt(0));
    }

    public static String extractValue(String string, String attribute) {
        int index = string.indexOf(attribute);
        int beginIndex = (index += attribute.length());
        beginIndex++;
        char ch = string.charAt(beginIndex);
        beginIndex++;
        int lastIndex = string.indexOf(ch, beginIndex);
        return string.substring(beginIndex, lastIndex);
    }

    private static boolean isFirstCharValid(char ch) {
        return (ch == '4' || ch == '5') ? false : true;
    }

}
