package com.ElevetorySystem.ElevetorySystem.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class LoggingEventListener {
    private static final Logger log = LoggerFactory.getLogger(LoggingEventListener.class);

    @EventListener
    public void on(ElevatorEvent e) {
        log.info("[Elevator {}] {} at floor {}", e.elevatorId(), e.type(), e.floor());
    }
}