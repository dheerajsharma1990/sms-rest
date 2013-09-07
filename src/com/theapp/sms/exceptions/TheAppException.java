package com.theapp.sms.exceptions;

public class TheAppException extends Exception {

	private static final long serialVersionUID = 4680569986028808169L;
	
	private ExceptionType exceptionType;

	public TheAppException() {
		super();
	}

    public TheAppException(Exception e){
        super(e);
    }

	public TheAppException(ExceptionType exceptionType) {
		super(exceptionType.getMessage());
        this.exceptionType = exceptionType;
	}
	
	public TheAppException(ExceptionType exceptionType, Object relatedObject) {
		super(exceptionType.getMessage() + "[" + relatedObject + "]");
        this.exceptionType = exceptionType;
	}

	public ExceptionType getExceptionType() {
		return exceptionType;
	}

}
