package com.jimo.exception;

/**
 * Created by root on 17-5-24.
 */
public class KillCloseException extends KillException {
    public KillCloseException(String message) {
        super(message);
    }

    public KillCloseException(String message, Throwable cause) {
        super(message, cause);
    }
}
