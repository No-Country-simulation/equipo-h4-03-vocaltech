package com.vocaltech.api.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class ErrorResponse {
    private String details;
    private Integer statusCode;
    private String message;
}
