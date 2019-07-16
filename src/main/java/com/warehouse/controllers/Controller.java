package com.warehouse.controllers;

import com.warehouse.utils.OperationResult;
import org.springframework.web.bind.annotation.ExceptionHandler;

public abstract class Controller {

    @ExceptionHandler({Exception.class})
    public Object handleException(Exception ex){
        String message = ex.getMessage();

        if (message == null || message.isEmpty())
            message = ex.getClass().getName();

        return OperationResult.createError(message);
    }
}
