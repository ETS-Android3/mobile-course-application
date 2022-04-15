package com.example.mobile30_03.utils;

public class HelperFunctions {
    public static String milliSecondsToTimer(long milliSeconds){
        String timerString="";
        String secondString;
        int hour=(int) (milliSeconds/(1000*60*60));
        int minutes=(int)(milliSeconds%(1000*60*60))/(1000*60);
        int seconds=(int)((milliSeconds%(1000*60*60))%(1000*60)/1000);
        if(hour>0){
            timerString=hour+":";
        }
        if(seconds<10){
            secondString="0"+seconds;
        }
        else {
            secondString=""+seconds;
        }
        timerString=timerString+minutes+":"+secondString;
        return timerString;
    }
}
