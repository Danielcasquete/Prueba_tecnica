package com.konex.medicamentos.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Calendar;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Medicaments")
public class Medicamentos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Generación automática del ID
    private Long id;
    @NotBlank(message = "Nombre no deberia estar vacio")
    private String nombre;
    @NotBlank(message = "Laboratorio no deberia estar vacio")
    private String laboratorio;
    @NotNull(message = "Fecha de fabricacion no deberia ser nulo")
    @JsonFormat(pattern="yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    private Calendar fechaFabricacion;
    @NotNull(message = "Fecha de vencimiento no deberia ser nulo")
    @JsonFormat(pattern="yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    private Calendar fechaVencimiento;
    @NotNull(message = "cantidad no deberia estar vacio")
    private int cantidad;
    @NotNull(message = "Valor unitario no deberia estar vacio")
    private Double valorUnitario;
}
