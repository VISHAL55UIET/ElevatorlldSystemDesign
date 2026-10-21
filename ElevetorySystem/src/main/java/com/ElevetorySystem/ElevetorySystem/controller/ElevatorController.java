package com.ElevetorySystem.ElevetorySystem.controller;

import com.ElevetorySystem.ElevetorySystem.dto.*;
import com.ElevetorySystem.ElevetorySystem.service.ElevatorService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/elevators")
public class ElevatorController {
    private final ElevatorService service;

    public ElevatorController(ElevatorService service) { this.service = service; }

    @PostMapping("/request")
    public ElevatorStatusResponse hallCall(@Valid @RequestBody Request request) {
        return ElevatorStatusResponse.from(service.handleHallCall(request));
    }

    @PostMapping("/{id}/select-floor")
    public ElevatorStatusResponse selectFloor(@PathVariable int id,
                                              @Valid @RequestBody FloorSelectRequest body) {
        return ElevatorStatusResponse.from(service.selectFloor(id, body.floor()));
    }

    @GetMapping("/status")
    public List<ElevatorStatusResponse> status() {
        return service.getAll().stream().map(ElevatorStatusResponse::from).toList();
    }

    @PostMapping("/{id}/maintenance/{on}")
    public ElevatorStatusResponse maintenance(@PathVariable int id, @PathVariable boolean on) {
        var e = service.find(id);
        e.setMaintenance(on);
        return ElevatorStatusResponse.from(e);
    }
}