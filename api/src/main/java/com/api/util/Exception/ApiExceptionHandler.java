package com.api.util.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class ApiExceptionHandler {
    /**
     * Other exceptions
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiResult handleAllException(Exception ex, WebRequest request) {
        // quá trình kiểm soat lỗi diễn ra ở đây
        return new ApiResult(10000, ex.getLocalizedMessage());
    }

    /**
     * IndexOutOfBoundsException handling
     */
    @ExceptionHandler(IndexOutOfBoundsException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ApiResult handleIndexOutOfBoundsException(Exception ex, WebRequest request) {
        return new ApiResult(10100, "Data not found");
    }

    /**
     * NullPointerException handling
     */
    @ExceptionHandler(NullPointerException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ApiResult handleNullPointerException(NullPointerException ex, WebRequest request) {
        return new ApiResult(10101, "Null reference encountered: " + ex.getMessage());
    }

    /**
     * IllegalArgumentException handling
     */
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ApiResult handleIllegalArgumentException(IllegalArgumentException ex, WebRequest request) {
        return new ApiResult(10102, "Invalid argument: " + ex.getMessage());
    }

    /**
     * NumberFormatException handling
     */
    @ExceptionHandler(NumberFormatException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ApiResult handleNumberFormatException(NumberFormatException ex, WebRequest request) {
        return new ApiResult(10103, "Invalid number format: " + ex.getMessage());
    }

    /**
     * IllegalStateException handling
     */
    @ExceptionHandler(IllegalStateException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ApiResult handleIllegalStateException(IllegalStateException ex, WebRequest request) {
        return new ApiResult(10104, "Invalid state: " + ex.getMessage());
    }

    /**
     * UnsupportedOperationException handling
     */
    @ExceptionHandler(UnsupportedOperationException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ApiResult handleUnsupportedOperationException(UnsupportedOperationException ex, WebRequest request) {
        return new ApiResult(10105, "Unsupported operation: " + ex.getMessage());
    }

    /**
     * ClassCastException handling
     */
    @ExceptionHandler(ClassCastException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ApiResult handleClassCastException(ClassCastException ex, WebRequest request) {
        return new ApiResult(10106, "Class cast error: " + ex.getMessage());
    }
}
