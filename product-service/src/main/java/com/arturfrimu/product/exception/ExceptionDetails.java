package com.arturfrimu.product.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

import static com.fasterxml.jackson.annotation.JsonFormat.Shape.STRING;

@Getter
@RequiredArgsConstructor
public final class ExceptionDetails {
    @JsonFormat(shape = STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private final LocalDateTime time;
    private final String message;
    private final String description;
}
