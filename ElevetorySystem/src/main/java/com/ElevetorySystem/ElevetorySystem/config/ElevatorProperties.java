package com.ElevetorySystem.ElevetorySystem.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "elevator")
public record ElevatorProperties(int minFloor, int maxFloor, int elevators, String strategy) {}