package com.konex.inventario.repository;

import com.konex.inventario.model.Venta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Calendar;
import java.util.List;
import java.util.Optional;

public interface IVentaRepository extends JpaRepository<Venta, Long> {

    Optional<List<Venta>> findByfechaVentaBetween(Calendar from, Calendar to);
}
