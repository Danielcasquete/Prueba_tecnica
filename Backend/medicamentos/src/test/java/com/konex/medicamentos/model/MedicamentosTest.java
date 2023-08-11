package com.konex.medicamentos.model;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MedicamentosTest {

    private Validator validator;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testMedicamentosValidation() {
        Medicamentos medicamentos = new Medicamentos();
        medicamentos.setNombre("");
        medicamentos.setLaboratorio("");
        medicamentos.setFechaFabricacion(null);
        medicamentos.setFechaVencimiento(null);
        medicamentos.setCantidad(-1);
        medicamentos.setValorUnitario(null);

        Set<ConstraintViolation<Medicamentos>> violations = validator.validate(medicamentos);

        assertEquals(6, violations.size());
    }

    @Test
    public void testValidMedicamentos() {
        Medicamentos medicamentos = new Medicamentos();
        medicamentos.setNombre("Ibuprofeno");
        medicamentos.setLaboratorio("Pfizer");
        medicamentos.setFechaFabricacion(Calendar.getInstance());
        medicamentos.setFechaVencimiento(Calendar.getInstance());
        medicamentos.setCantidad(10);
        medicamentos.setValorUnitario(5.0);

        Set<ConstraintViolation<Medicamentos>> violations = validator.validate(medicamentos);

        assertEquals(0, violations.size());
    }
}

