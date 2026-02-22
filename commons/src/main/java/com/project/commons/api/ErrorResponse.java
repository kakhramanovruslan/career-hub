package com.project.commons.api;


import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class ErrorResponse {

    private int status;

    private String message;

    @Builder.Default
    private Date timestamp = new Date();
}