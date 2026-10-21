package com.ElevetorySystem.ElevetorySystem.event;

import java.time.Instant;

public record ElevatorEvent(int elevatorId, int floor, Type type, Instant at) {
    public enum Type { REQUEST_ASSIGNED, ARRIVED }
}