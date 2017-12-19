package com.jimo.exception;

/**
 * 重复秒杀异常
 * Created by root on 17-5-24.
 */
public class RepeatKillException extends KillException {

    public RepeatKillException(String message) {
        super(message);
    }

    public RepeatKillException(String message, Throwable cause) {
        super(message, cause);
    }
}
