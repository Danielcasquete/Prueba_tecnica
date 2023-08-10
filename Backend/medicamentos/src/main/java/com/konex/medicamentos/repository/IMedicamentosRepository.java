package com.konex.medicamentos.repository;

import com.konex.medicamentos.model.Medicamentos;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Calendar;
import java.util.List;
import java.util.Optional;

public interface IMedicamentosRepository extends JpaRepository<Medicamentos, Long> {
    Optional<Medicamentos> findBynombreIgnoreCase(String nombre);
    Optional<List<Medicamentos>> findByfechaVencimientoBetween(Calendar from, Calendar to);
}
