package com.konex.medicamentos.exceptions;

import jakarta.validation.ValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class BaseControllerTest {

    private BaseController baseController;

    @BeforeEach
    public void setup() {
        baseController = new BaseController();
    }

    @Test
    public void testHandleException() {
        ValidationException ex = new ValidationException("Some message");

        Map<String, String> result = baseController.handleException(ex);


        assertEquals("Some message", result.get("Id"));
    }

    @Test
    public void testNameAlreadyExistException() {
        Map<String, String> result = baseController.EmailAlreadyExistException();


        assertEquals("el nombre ya existe y no puede ser creado", result.get("nombre"));
    }

    @Test
    public void testMethodNotSupported() {
        String result = baseController.methodNotSupported();


        assertEquals("Lo siento ʕ´•ᴥ•̥`ʔ, este request method no esta soportado o la request está mal escrita", result);
    }

    @Test
    public void testMismatchException() {
        String result = baseController.MismatchException();


        assertEquals("Lo siento ʕ´•ᴥ•̥`ʔ, el parámetro o argumento no es del tipo correcto", result);
    }
}
