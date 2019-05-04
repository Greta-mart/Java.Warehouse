package com.warehouse.utils;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class OperationResult {
    private boolean isSuccess;
    private String error;
    private Object data;

    public boolean isSuccess() {
        return isSuccess;
    }

    public String getError() {
        return error;
    }

    public Object getData() {
        return data;
    }

    private OperationResult(boolean isSuccess, String error, Object data){
        this.isSuccess = isSuccess;
        this.error = error;
        this.data = data;
    }

    public static OperationResult CreateSuccess(Object data) {
        return new OperationResult(true, null, data);
    }

    public static OperationResult CreateError(String errorText) {
        return new OperationResult(false, errorText, null);
    }
}
