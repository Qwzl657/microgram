package com.microgram.error;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ErrorResponseBody {
    private int status;
    private String error;
    private String message;
}