package com.homework.exceptions;

public class IllegalClientDataException extends Exception{
    public IllegalClientDataException(){

    }
    public IllegalClientDataException(String msg){
        super(msg);
    }
}
