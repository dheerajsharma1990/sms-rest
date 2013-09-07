package com.theapp.sms.utils;

/**
 * Created with IntelliJ IDEA.
 * User: user
 * Date: 28/8/13
 * Time: 1:46 PM
 * To change this template use File | Settings | File Templates.
 */
public class Timer {

    private long startTime = -1;

    private long endTime;

    private String displayMessage;

    public Timer(String displayMessage) {
        this.displayMessage = displayMessage;
    }

    public void startTimer(){
        startTime = System.currentTimeMillis();
    }

    public String endTimer() {
        endTime = System.currentTimeMillis();
        return displayMessage + " " + (endTime-startTime)/1000 + "s.";
    }
}
