package com.theapp.sms.response;

import com.theapp.sms.utils.Constants;

public class LoginResponse {

    private Response response;

    public LoginResponse(Response response) {
        this.response = response;
    }

    public String getXmlResponse() {
        XMLResponse xml = new XMLResponse();
        if (response.isLoginValid()) {
            xml.setLoginSuccessful(true);
        } else {
            xml.setLoginSuccessful(false);
            if (response.isExceptioned()) {
                xml.setException(Constants.LOGIN_ERROR_AT_SERVER);
            } else {
                xml.setException(Constants.INVALID_LOGIN);
            }
        }
        return xml.toString();
    }

    private class XMLResponse {

        private boolean loginSuccessful;

        private String exception = "";

        public XMLResponse() {

        }

        public void setLoginSuccessful(boolean loginSuccessful) {
            this.loginSuccessful = loginSuccessful;
        }

        public void setException(String exception) {
            this.exception = exception;
        }

        @Override
        public String toString() {
            StringBuilder builder = new StringBuilder();
            builder.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>")
                    .append("<response>")
                    .append("<loginSuccessful>").append(loginSuccessful).append("</loginSuccessful>")
                    .append("<exception>").append(exception).append("</exception>")
                    .append("</response>");
            return builder.toString();
        }

    }

}
