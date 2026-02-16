package com.luisdev.heladopoints.dto;

import java.time.LocalDateTime;

public record ScanDTO(
        Long id,
        LocalDateTime date,
        boolean success
) {
}
