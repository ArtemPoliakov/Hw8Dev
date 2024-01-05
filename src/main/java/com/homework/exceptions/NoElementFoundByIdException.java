package com.homework.exceptions;

public class NoElementFoundByIdException extends Exception{
    public NoElementFoundByIdException(){}
    public NoElementFoundByIdException(String msg){
        super(msg);
    }
}
