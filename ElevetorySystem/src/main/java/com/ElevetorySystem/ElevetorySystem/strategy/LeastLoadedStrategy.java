package com.ElevetorySystem.ElevetorySystem.strategy;

import com.ElevetorySystem.ElevetorySystem.dto.Request;
import com.ElevetorySystem.ElevetorySystem.enums.ElevatorState;
import com.ElevetorySystem.ElevetorySystem.model.Elevator;
import org.springframework.stereotype.Component;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Component("least-load")
public class LeastLoadedStrategy implements DispatchStrategy {
    @Override
    public Optional<Elevator> select(List<Elevator> elevators, Request request) {
        return elevators.stream()
                .filter(e -> e.getState() != ElevatorState.MAINTENANCE)
                .min(Comparator.comparingInt(Elevator::pendingStops));
    }
}