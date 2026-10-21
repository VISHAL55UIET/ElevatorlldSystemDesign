package com.ElevetorySystem.ElevetorySystem.dto;

import jakarta.validation.constraints.Min;

/** Inside-car call: someone presses a floor button. */
public record FloorSelectRequest(@Min(0) int floor) {}