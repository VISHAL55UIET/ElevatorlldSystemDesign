package com.ElevetorySystem.ElevetorySystem.dto;

import com.ElevetorySystem.ElevetorySystem.enums.Direction;
import com.ElevetorySystem.ElevetorySystem.enums.ElevatorState;
import com.ElevetorySystem.ElevetorySystem.model.Elevator;

public record ElevatorStatusResponse(int id, int currentFloor, Direction direction,
                                     ElevatorState state, int pendingStops) {
    public static ElevatorStatusResponse from(Elevator e) {
        return new ElevatorStatusResponse(e.getId(), e.getCurrentFloor(),
                e.getDirection(), e.getState(), e.pendingStops());
    }
}