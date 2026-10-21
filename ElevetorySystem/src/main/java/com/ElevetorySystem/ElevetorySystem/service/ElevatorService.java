package com.ElevetorySystem.ElevetorySystem.service;

import com.ElevetorySystem.ElevetorySystem.config.ElevatorProperties;
import com.ElevetorySystem.ElevetorySystem.dto.Request;
import com.ElevetorySystem.ElevetorySystem.event.ElevatorEvent;
import com.ElevetorySystem.ElevetorySystem.event.ElevatorEvent.Type;
import com.ElevetorySystem.ElevetorySystem.exception.*;
import com.ElevetorySystem.ElevetorySystem.model.Elevator;
import com.ElevetorySystem.ElevetorySystem.strategy.DispatchStrategy;
import com.ElevetorySystem.ElevetorySystem.strategy.DispatchStrategyFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.stream.IntStream;

@Service
public class ElevatorService {
    private final List<Elevator> elevators;
    private final DispatchStrategy strategy;
    private final ElevatorProperties props;
    private final ApplicationEventPublisher publisher;

    public ElevatorService(ElevatorProperties props, DispatchStrategyFactory factory,
                           ApplicationEventPublisher publisher) {
        this.props = props;
        this.publisher = publisher;
        this.strategy = factory.get(props.strategy());
        this.elevators = IntStream.rangeClosed(1, props.elevators())
                .mapToObj(i -> new Elevator(i, props.minFloor()))
                .toList();
    }

    public Elevator handleHallCall(Request request) {
        validateFloor(request.floor());
        Elevator chosen = strategy.select(elevators, request)
                .orElseThrow(NoElevatorAvailableException::new);
        chosen.addStop(request.floor());
        publish(chosen, request.floor(), Type.REQUEST_ASSIGNED);
        if (chosen.getCurrentFloor() == request.floor()) publish(chosen, request.floor(), Type.ARRIVED);
        return chosen;
    }

    public Elevator selectFloor(int elevatorId, int floor) {
        validateFloor(floor);
        Elevator e = find(elevatorId);
        e.addStop(floor);
        publish(e, floor, Type.REQUEST_ASSIGNED);
        return e;
    }

    public List<Elevator> getAll() { return elevators; }

    public Elevator find(int id) {
        return elevators.stream().filter(e -> e.getId() == id).findFirst()
                .orElseThrow(() -> new ElevatorNotFoundException(id));
    }

    /** Simulation clock: every tick each elevator moves one floor. */
    @Scheduled(fixedDelayString = "${elevator.tick-ms:1000}")
    public void tick() {
        for (Elevator e : elevators) {
            if (e.step()) publish(e, e.getCurrentFloor(), Type.ARRIVED);
        }
    }

    private void validateFloor(int floor) {
        if (floor < props.minFloor() || floor > props.maxFloor())
            throw new InvalidFloorException(floor, props.minFloor(), props.maxFloor());
    }

    private void publish(Elevator e, int floor, Type type) {
        publisher.publishEvent(new ElevatorEvent(e.getId(), floor, type, Instant.now()));
    }
}