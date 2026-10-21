package com.ElevetorySystem.ElevetorySystem.strategy;

import org.springframework.stereotype.Component;
import java.util.Map;

@Component
public class DispatchStrategyFactory {
    private final Map<String, DispatchStrategy> strategies; // Spring injects by bean name

    public DispatchStrategyFactory(Map<String, DispatchStrategy> strategies) {
        this.strategies = strategies;
    }

    public DispatchStrategy get(String name) {
        DispatchStrategy s = strategies.get(name);
        if (s == null) throw new IllegalArgumentException("Unknown dispatch strategy: " + name);
        return s;
    }
}