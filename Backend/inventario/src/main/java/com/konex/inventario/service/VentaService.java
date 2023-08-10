package com.konex.inventario.service;

import com.konex.inventario.model.Venta;
import com.konex.inventario.repository.IVentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.List;
import java.util.Optional;

//inyeccion de dependencia -> bean para logica de negocio @service
@Service
public class VentaService {
    @Autowired
    private IVentaRepository repository;
    public List<Venta> listAll() {
        return repository.findAll();
    }

    public Optional<List<Venta>> findByFechaVenta (Calendar from, Calendar to){
        return repository.findByfechaVentaBetween(from, to);
    }

    public Venta crearVenta(Venta venta){
        return repository.save(venta);
    }
}
