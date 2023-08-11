package com.konex.inventario.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Calendar;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Sales")
public class Venta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Generación automática del ID
    private Long id;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Calendar fechaVenta;
    @NotNull(message = "La venta debe tener un medicamento")
    private long idMedicamento;
    @NotNull(message = "cantidad no deberia estar vacio")
    private int cantidad;
    @NotNull(message = "Valor unitario no deberia estar vacio")
    private Double valorUnitario;
    @NotNull(message = "Valor total no deberia estar vacio")
    private Double valorTotal;

    @PrePersist
    protected void onCreate() {
        fechaVenta = Calendar.getInstance();
    }
}
