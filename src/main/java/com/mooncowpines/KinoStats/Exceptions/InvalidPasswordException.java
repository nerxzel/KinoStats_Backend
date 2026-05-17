package com.mooncowpines.KinoStats.Exceptions;

public class InvalidPasswordException extends RuntimeException {
    public InvalidPasswordException() {
        super("Incorrect password");
    }
}
