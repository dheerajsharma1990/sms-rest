package com.theapp.sms.exceptions;

public enum ExceptionType {

	INVALID_URL_SPECIFIED("URL specified is invalid."),
	ENCODING_ERROR("Unable to encode the specified value."),
	PROBLEM_WTH_PROTOCOL("Unable to apply the specified protocol."),
	INVALID_POST_QUERY("The query specified for posting in http is invalid."),
    EXCEPTION_ON_SERVER("Something wrong happened.");

	private String message;

	private ExceptionType(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

}
