package com.konex.inventario.model;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class VentaTest {

    @Test
    public void testValidVenta() {
        Venta venta = new Venta();
        venta.setIdMedicamento(1L);
        venta.setCantidad(10);
        venta.setValorUnitario(50.0);
        venta.setValorTotal(500.0);

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Venta>> violations = validator.validate(venta);

        assertEquals(0, violations.size(), "La venta válida no debe tener violaciones de restricciones");
    }

    @Test
    public void testInvalidVentaNullValorTotal() {
        Venta venta = new Venta();
        venta.setIdMedicamento(1L);
        venta.setCantidad(10);
        venta.setValorUnitario(50.0);
        venta.setValorTotal(null); // Valor total nulo

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Venta>> violations = validator.validate(venta);

        assertEquals(1, violations.size(), "La venta con valor total nulo debe tener 1 violación de restricción");
    }
}
