package com.konex.medicamentos.repository;

import com.konex.medicamentos.model.Medicamentos;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@DataJpaTest
public class IMedicamentosRepositoryTest {

    @Mock
    private IMedicamentosRepository medicamentosRepository;

    @Test
    public void testFindBynombreIgnoreCase() {
        // Configurar el comportamiento del mock repository
        Medicamentos medicamento = new Medicamentos();
        medicamento.setNombre("Medicamento Test");
        when(medicamentosRepository.findBynombreIgnoreCase("medicamento test")).thenReturn(Optional.of(medicamento));


        Optional<Medicamentos> medicamentoEncontrado = medicamentosRepository.findBynombreIgnoreCase("medicamento test");

        // Realizar las aserciones
        assertTrue(medicamentoEncontrado.isPresent());
        assertEquals("Medicamento Test", medicamentoEncontrado.get().getNombre());
    }

    @Test
    public void testFindByfechaVencimientoBetween() {
        // Configurar el comportamiento del mock repository
        Medicamentos medicamento2 = new Medicamentos();
        medicamento2.setNombre("Medicamento 2");
        Calendar fechaVencimiento = Calendar.getInstance();
        fechaVencimiento.add(Calendar.DAY_OF_MONTH, 7); // Agregar 7 días a la fecha actual
        medicamento2.setFechaVencimiento(fechaVencimiento);
        when(medicamentosRepository.findByfechaVencimientoBetween(any(Calendar.class), any(Calendar.class)))
                .thenReturn(Optional.of(List.of(medicamento2)));

        // Llamar al método probando en el mock repository
        Calendar fechaInicio = Calendar.getInstance();
        Calendar fechaFin = Calendar.getInstance();
        fechaFin.add(Calendar.DAY_OF_MONTH, 10);
        Optional<List<Medicamentos>> medicamentosEncontrados = medicamentosRepository.findByfechaVencimientoBetween(fechaInicio, fechaFin);


        assertTrue(medicamentosEncontrados.isPresent());
        assertEquals(1, medicamentosEncontrados.get().size());
        assertEquals("Medicamento 2", medicamentosEncontrados.get().get(0).getNombre());
    }
}
