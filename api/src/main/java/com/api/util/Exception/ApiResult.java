package com.api.util.Exception;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApiResult {
    private int statusCode;
    private String message;
}
