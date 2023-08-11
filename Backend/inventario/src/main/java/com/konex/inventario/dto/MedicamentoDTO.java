package com.konex.inventario.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Calendar;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MedicamentoDTO {
    private Long id;
    private String nombre;
    private String laboratorio;
    private Calendar fechaFabricacion;
    private Calendar fechaVencimiento;
    private int cantidad;
    private Double valorUnitario;
}
