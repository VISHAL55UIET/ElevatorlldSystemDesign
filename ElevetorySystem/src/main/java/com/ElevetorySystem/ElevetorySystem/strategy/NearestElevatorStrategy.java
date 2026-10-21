package com.ElevetorySystem.ElevetorySystem.strategy;

import com.ElevetorySystem.ElevetorySystem.dto.Request;
import com.ElevetorySystem.ElevetorySystem.enums.Direction;
import com.ElevetorySystem.ElevetorySystem.enums.ElevatorState;
import com.ElevetorySystem.ElevetorySystem.model.Elevator;
import org.springframework.stereotype.Component;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Component("nearest")
public class NearestElevatorStrategy implements DispatchStrategy {

    @Override
    public Optional<Elevator> select(List<Elevator> elevators, Request request) {
        return elevators.stream()
                .filter(e -> e.getState() != ElevatorState.MAINTENANCE)
                .min(Comparator.comparingInt(e -> cost(e, request)));
    }

    /** Lower is better. A car already heading toward the floor costs only the distance. */
    private int cost(Elevator e, Request r) {
        int distance = Math.abs(e.getCurrentFloor() - r.floor());
        Direction d = e.getDirection();
        boolean idle = d == Direction.IDLE;
        boolean headingToward = (d == Direction.UP && r.floor() >= e.getCurrentFloor())
                || (d == Direction.DOWN && r.floor() <= e.getCurrentFloor());
        return (idle || headingToward) ? distance : distance + 2 * e.pendingStops();
    }
}