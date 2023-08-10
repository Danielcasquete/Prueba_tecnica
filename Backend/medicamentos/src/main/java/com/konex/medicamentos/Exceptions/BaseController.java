package com.konex.medicamentos.Exceptions;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class BaseController extends RuntimeException{
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidateExceptions(MethodArgumentNotValidException ex){
        Map<String, String> errors = new HashMap<String, String>();
        ex.getBindingResult().getAllErrors().forEach((error) ->{
            String fieldName = ((FieldError) error).getField();
            String message = error.getDefaultMessage();
            errors.put(fieldName, message);
        });
        return errors;
    }

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = ValidationException.class)
    public Map<String, String>  handleException(ValidationException exception) {
        Map<String, String> errors = new HashMap<String, String>();
        errors.put("Id", exception.getMessage());
        return errors;

    }


    @ExceptionHandler(value = NameAlreadyExistException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public Map<String, String>  EmailAlreadyExistException() {
        Map<String, String> errors = new HashMap<String, String>();
        errors.put("nombre", "el nombre ya existe y no puede ser creado");
        return errors;
    }

    @ExceptionHandler(value = HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public String  methodNotSupported() {
        return "Lo siento ʕ´•ᴥ•̥`ʔ, este request method no esta soportado o la request está mal escrita";
    }

    @ExceptionHandler(value = MethodArgumentTypeMismatchException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public String  MismatchException() {
        return "Lo siento ʕ´•ᴥ•̥`ʔ, el parámetro o argumento no es del tipo correcto";
    }
}
