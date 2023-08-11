package com.konex.medicamentos.service;
import com.konex.medicamentos.exceptions.NameAlreadyExistException;
import com.konex.medicamentos.model.Medicamentos;
import com.konex.medicamentos.repository.IMedicamentosRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class MedicamentosServiceTest {

    @Mock
    private IMedicamentosRepository medicamentosRepository;

    @InjectMocks
    private MedicamentosService medicamentosService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCrearMedicamento() {
        Medicamentos medicamento = new Medicamentos();
        medicamento.setNombre("Medicamento Test");

        when(medicamentosRepository.findBynombreIgnoreCase("Medicamento Test")).thenReturn(Optional.empty());
        when(medicamentosRepository.save(medicamento)).thenReturn(medicamento);

        Medicamentos result = medicamentosService.crearMedicamento(medicamento);

        assertNotNull(result);
        assertEquals("Medicamento Test", result.getNombre());
    }

    @Test
    public void testCrearMedicamentoNombreExistente() {
        Medicamentos medicamento = new Medicamentos();
        medicamento.setNombre("Medicamento Test");

        when(medicamentosRepository.findBynombreIgnoreCase("Medicamento Test")).thenReturn(Optional.of(medicamento));

        assertThrows(NameAlreadyExistException.class, () -> {
            medicamentosService.crearMedicamento(medicamento);
        });
    }

}

