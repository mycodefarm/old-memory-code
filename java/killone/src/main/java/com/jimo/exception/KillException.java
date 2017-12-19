package com.jimo.exception;

/**
 * 总的异常类
 * Created by root on 17-5-24.
 */
public class KillException extends RuntimeException {
    public KillException(String message) {
        super(message);
    }

    public KillException(String message, Throwable cause) {
        super(message, cause);
    }
}
