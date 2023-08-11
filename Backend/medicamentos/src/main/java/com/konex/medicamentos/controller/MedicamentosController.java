package com.konex.medicamentos.controller;

import com.konex.medicamentos.model.Medicamentos;
import com.konex.medicamentos.service.MedicamentosService;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Calendar;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/Medicamentos")
@CrossOrigin(origins = "http://localhost:4200")
public class MedicamentosController {

    @Autowired
    private MedicamentosService service;

    @GetMapping (value = "/{id}")
    public ResponseEntity<Optional<Medicamentos>> findUserById (@PathVariable ("id") Long id){
        if (!service.existById(id)){
            throw new ValidationException("This ID does not exist");
        }else {
            return ResponseEntity.status(HttpStatus.OK).body(service.findById(id));}
    }

    @GetMapping("/all")
    public ResponseEntity<List<Medicamentos>> listAll() {
        return ResponseEntity.status(HttpStatus.OK).body(service.listAll());
    }

    @GetMapping(value = "/medicamento/{medicamento}")
    public ResponseEntity<Optional<Medicamentos>> findByName(@PathVariable ("medicamento") String medicamento) {
        return ResponseEntity.status(HttpStatus.OK).body(service.findByName(medicamento));
    }

    @GetMapping(value = "/FechaVencimiento/{from}/{to}")
    public ResponseEntity<Optional<List<Medicamentos>>> findByBirthday(@PathVariable ("from") @DateTimeFormat(pattern = "yyyy-MM-dd") Calendar from, @PathVariable ("to") @DateTimeFormat(pattern = "yyyy-MM-dd") Calendar to) {
        return ResponseEntity.status(HttpStatus.OK).body(service.findByfechaVencimiento(from,to));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity deleteMedicamento (@PathVariable ("id") Long id){

        if (!service.existById(id)){
            throw new ValidationException("Este ID no existe");
        }else {
            service.deleteMedicamento(id);
            return ResponseEntity.ok(!service.existById(id));
        }

    }

    @PostMapping
    public ResponseEntity<Medicamentos> create (@Valid @RequestBody Medicamentos medicamento){
        return ResponseEntity.status(HttpStatus.CREATED).body(service.crearMedicamento(medicamento));
    }

    @PutMapping
    public ResponseEntity<Medicamentos> editMedic (@RequestBody Medicamentos medicamento){
        if (medicamento.getId() == null){
            throw new ValidationException("El Id no puede estar vacio en un put request");
        }
        if (!service.existById(medicamento.getId())){
            throw new ValidationException("Este ID no existe");
        }else {
            return ResponseEntity.status(HttpStatus.CREATED).body(service.editMedicamento(medicamento));
        }

    }
}
