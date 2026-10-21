package com.ElevetorySystem.ElevetorySystem.exception;


public class NoElevatorAvailableException extends RuntimeException {
    public NoElevatorAvailableException() { super("No elevator available (all in maintenance)"); }
}