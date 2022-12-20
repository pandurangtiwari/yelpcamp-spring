package com.yelpcamp.controller.error;

import com.yelpcamp.controller.ControllerConstants;
import com.yelpcamp.exception.AppError;
import com.yelpcamp.exception.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler
    public String handleValidationException(BindException ex, Model model) {
        var message = getValidationErrorMessage(ex);
        var appError = new AppError(message, ex);
        model.addAttribute(ControllerConstants.ATTRIBUTES.TITLE, "Error");
        model.addAttribute(ControllerConstants.ATTRIBUTES.ERROR, appError);
        return ControllerConstants.VIEWS.ERROR;
    }

    //This is a workaround, custom messages should be defined in messages.properties file
    private static String getValidationErrorMessage(BindException ex){
        if(ex.getAllErrors().get(0).contains(NumberFormatException.class))
            return ((FieldError)ex.getAllErrors().get(0)).getField() + " must be number";
        return ex.getAllErrors().get(0).getDefaultMessage();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler
    public String handleResourceNotFoundException(Model model,
                                                  ResourceNotFoundException ex){
        model.addAttribute(ControllerConstants.ATTRIBUTES.TITLE, "Error");
        model.addAttribute(ControllerConstants.ATTRIBUTES.ERROR, ex);
        return ControllerConstants.VIEWS.ERROR;
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler
    public String defaultErrorHandler(Exception ex, Model model) {
        var appError = new AppError("Something went wrong", ex);
        model.addAttribute(ControllerConstants.ATTRIBUTES.ERROR, appError);
        model.addAttribute(ControllerConstants.ATTRIBUTES.TITLE, "Error");
        return ControllerConstants.VIEWS.ERROR;
    }
}
