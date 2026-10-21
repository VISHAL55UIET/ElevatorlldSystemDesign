package com.ElevetorySystem.ElevetorySystem.model;

import com.ElevetorySystem.ElevetorySystem.enums.Direction;
import com.ElevetorySystem.ElevetorySystem.enums.ElevatorState;

import java.util.TreeSet;

public class Elevator {
    private final int id;
    private int currentFloor;
    private Direction direction = Direction.IDLE;
    private ElevatorState state = ElevatorState.IDLE;
    private final TreeSet<Integer> upStops = new TreeSet<>();
    private final TreeSet<Integer> downStops = new TreeSet<>();

    public Elevator(int id, int startFloor) {
        this.id = id;
        this.currentFloor = startFloor;
    }

    /** Stops above the car go to upStops, stops below go to downStops. */
    public synchronized void addStop(int floor) {
        if (floor > currentFloor) upStops.add(floor);
        else if (floor < currentFloor) downStops.add(floor);
    }

    /** Moves one floor. Returns true if the car arrived at a requested stop. */
    public synchronized boolean step() {
        if (state == ElevatorState.MAINTENANCE) return false;
        updateDirection();
        if (direction == Direction.IDLE) {
            state = ElevatorState.IDLE;
            return false;
        }
        currentFloor += (direction == Direction.UP) ? 1 : -1;
        state = (direction == Direction.UP) ? ElevatorState.MOVING_UP : ElevatorState.MOVING_DOWN;
        TreeSet<Integer> stops = (direction == Direction.UP) ? upStops : downStops;
        boolean arrived = stops.remove(currentFloor);
        if (arrived) state = ElevatorState.DOOR_OPEN;
        return arrived;
    }

    private void updateDirection() {
        if (direction == Direction.UP && upStops.isEmpty()) {
            direction = downStops.isEmpty() ? Direction.IDLE : Direction.DOWN;
        } else if (direction == Direction.DOWN && downStops.isEmpty()) {
            direction = upStops.isEmpty() ? Direction.IDLE : Direction.UP;
        } else if (direction == Direction.IDLE) {
            if (upStops.isEmpty() && downStops.isEmpty()) return;
            if (downStops.isEmpty()) direction = Direction.UP;
            else if (upStops.isEmpty()) direction = Direction.DOWN;
            else {
                int up = upStops.first() - currentFloor;
                int down = currentFloor - downStops.last();
                direction = (up <= down) ? Direction.UP : Direction.DOWN;
            }
        }
    }

    public synchronized void setMaintenance(boolean on) {
        state = on ? ElevatorState.MAINTENANCE : ElevatorState.IDLE;
        if (on) { upStops.clear(); downStops.clear(); direction = Direction.IDLE; }
    }

    public synchronized int pendingStops() { return upStops.size() + downStops.size(); }
    public int getId() { return id; }
    public synchronized int getCurrentFloor() { return currentFloor; }
    public synchronized Direction getDirection() { return direction; }
    public synchronized ElevatorState getState() { return state; }
}