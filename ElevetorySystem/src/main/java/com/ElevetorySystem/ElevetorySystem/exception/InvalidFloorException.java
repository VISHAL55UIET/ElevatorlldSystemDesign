package com.ElevetorySystem.ElevetorySystem.exception;


public class InvalidFloorException extends RuntimeException {
    public InvalidFloorException(int floor, int min, int max) {
        super("Floor " + floor + " is outside range " + min + ".." + max);
    }
}