package com.ElevetorySystem.ElevetorySystem.dto;

import com.ElevetorySystem.ElevetorySystem.enums.Direction;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

/** Hall call: someone presses UP/DOWN on a floor. */
public record Request(@Min(0) int floor, @NotNull Direction direction) {}