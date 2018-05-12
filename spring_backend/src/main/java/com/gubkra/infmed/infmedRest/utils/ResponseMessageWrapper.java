package com.gubkra.infmed.infmedRest.utils;

public class ResponseMessageWrapper {
    private String message;

    public ResponseMessageWrapper(String message){
        this.message = message;
    }

    public String getMessage(){
        return this.message;
    }
}
