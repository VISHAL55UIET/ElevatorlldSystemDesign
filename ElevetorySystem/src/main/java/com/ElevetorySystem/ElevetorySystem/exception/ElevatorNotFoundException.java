package com.ElevetorySystem.ElevetorySystem.exception;

public class ElevatorNotFoundException extends RuntimeException {
    public ElevatorNotFoundException(int id) { super("Elevator " + id + " not found"); }
}