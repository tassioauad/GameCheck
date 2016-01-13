package com.tassioauad.gamecatalog.model.api.asynctask.exception;

public class BadRequestException extends Exception {

    public BadRequestException() {
    }

    public BadRequestException(String message) {
        super(message);
    }
}
