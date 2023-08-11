package com.konex.medicamentos.service;

import com.konex.medicamentos.exceptions.NameAlreadyExistException;
import com.konex.medicamentos.model.Medicamentos;
import com.konex.medicamentos.repository.IMedicamentosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.List;
import java.util.Optional;

//inyeccion de dependencia -> bean para logica de negocio @service
@Service
public class MedicamentosService {
    @Autowired
    private IMedicamentosRepository repository;

    public Optional<Medicamentos> findById (Long id){
        return repository.findById(id);
    }

    public List<Medicamentos> listAll() {
        return repository.findAll();
    }

    public Optional<Medicamentos> findByName (String nombre){
        return repository.findBynombreIgnoreCase(nombre);
    }

    public Optional<List<Medicamentos>> findByfechaVencimiento (Calendar from, Calendar to){
        return repository.findByfechaVencimientoBetween(from, to);
    }

    public Medicamentos crearMedicamento(Medicamentos medicamento){
        // Verificar si el nombre del medicamento ya existe
        if (repository.findBynombreIgnoreCase(medicamento.getNombre()).isPresent()) {
            throw new NameAlreadyExistException();
        }

        // Si el nombre no existe, guardar el medicamento
        return repository.save(medicamento);
    }

    public Medicamentos editMedicamento (Medicamentos medicamento){
        if (medicamento.getId() != null) {
            Optional<Medicamentos> medicamentoDb = repository.findById(medicamento.getId());
            if (!medicamentoDb.isEmpty()) {
                if (medicamento.getNombre() != null) {
                    Optional<Medicamentos> medic = repository.findBynombreIgnoreCase(medicamento.getNombre());
                    if (!medicamento.getNombre().equals(medicamentoDb.get().getNombre())  &&
                            medic.isEmpty()==false){
                        throw new NameAlreadyExistException();
                    }else{
                        medicamentoDb.get().setNombre(medicamento.getNombre());
                    }
                }
                if (medicamento.getLaboratorio() != null) {
                    medicamentoDb.get().setLaboratorio(medicamento.getLaboratorio());
                }
                if (medicamento.getFechaFabricacion() != null) {
                    medicamentoDb.get().setFechaFabricacion(medicamento.getFechaFabricacion());
                }
                if (medicamento.getFechaVencimiento() != null) {
                    medicamentoDb.get().setFechaVencimiento(medicamento.getFechaVencimiento());
                }
                if (medicamento.getCantidad() != medicamentoDb.get().getCantidad()) {
                    medicamentoDb.get().setCantidad(medicamento.getCantidad());
                }
                if (medicamento.getValorUnitario() != medicamentoDb.get().getValorUnitario()) {
                    medicamentoDb.get().setValorUnitario(medicamento.getValorUnitario());
                }
                repository.save(medicamentoDb.get());
                return medicamentoDb.get();
            } else {
                return medicamento;
            }
        } else {
            return medicamento;
        }
    }

    public Boolean deleteMedicamento (Long id){
        Boolean aBoolean = repository.findById(id)
                .map(medicamento -> {
                    repository.delete(medicamento);
                    return true;
                })
                .orElse(false);
        return aBoolean;
    }

    public boolean existById (Long id){
        return repository.existsById(id);
    }
}
