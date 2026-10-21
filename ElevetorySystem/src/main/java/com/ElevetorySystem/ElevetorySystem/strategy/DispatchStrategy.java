package com.ElevetorySystem.ElevetorySystem.strategy;

import com.ElevetorySystem.ElevetorySystem.dto.Request;
import com.ElevetorySystem.ElevetorySystem.model.Elevator;
import java.util.List;
import java.util.Optional;

public interface DispatchStrategy {
    Optional<Elevator> select(List<Elevator> elevators, Request request);
}