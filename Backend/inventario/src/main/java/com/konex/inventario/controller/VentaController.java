package com.konex.inventario.controller;

import com.konex.inventario.dto.MedicamentoDTO;
import com.konex.inventario.model.Venta;
import com.konex.inventario.service.VentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Calendar;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/Inventario")
@CrossOrigin(origins = "http://localhost:4200")
public class VentaController {
    @Autowired
    private VentaService service;

    private final WebClient webClient;

    @Autowired
    public VentaController(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://localhost:8080").build();
    }

    @GetMapping("/all")
    public ResponseEntity<List<Venta>> listAll() {
        return ResponseEntity.status(HttpStatus.OK).body(service.listAll());
    }

    @GetMapping(value = "/FechaVenta/{from}/{to}")
    public ResponseEntity<Optional<List<Venta>>> findByFechaVenta(@PathVariable ("from") @DateTimeFormat(pattern = "yyyy-MM-dd") Calendar from, @PathVariable ("to") @DateTimeFormat(pattern = "yyyy-MM-dd")  Calendar to) {
        return ResponseEntity.status(HttpStatus.OK).body(service.findByFechaVenta(from,to));
    }

    @GetMapping("/precioUnitario/{id}")
    public Mono<ResponseEntity<Double>> getUnitPrice(@PathVariable("id") Long id, @RequestParam("quantity") Integer quantity) {
        return webClient.get()
                .uri("/api/Medicamentos/{id}", id)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(MedicamentoDTO.class)
                .map(medicamento -> {
                    if (medicamento != null) {
                        Double unitPrice = medicamento.getValorUnitario();
                        Double totalPrice = unitPrice * quantity;
                        return ResponseEntity.ok(totalPrice);
                    } else {
                        return ResponseEntity.notFound().build();
                    }
                });
    }

    @PutMapping("/HacerVenta/{id}/{quantity}")
    public Mono<ResponseEntity<Venta>> confirmSale(@PathVariable("id") Long id, @PathVariable("quantity") Integer quantity) {
        String url = "/api/Medicamentos/" + id;

        return webClient.get()
                .uri(url)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(MedicamentoDTO.class)
                .map(medicamento -> {
                    if (medicamento == null) {
                        return ResponseEntity.badRequest().build();
                    } else {
                        int newQuantity = medicamento.getCantidad() - quantity;
                        medicamento.setCantidad(newQuantity);
                        webClient.put()
                                .uri("/api/Medicamentos")
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(medicamento)
                                .retrieve()
                                .toBodilessEntity()
                                .subscribe();  // Suscripción para realizar la operación PUT
                        Venta nuevaVenta = new Venta();
                        nuevaVenta.setIdMedicamento(id);
                        nuevaVenta.setCantidad(quantity);
                        nuevaVenta.setValorUnitario(medicamento.getValorUnitario());
                        nuevaVenta.setValorTotal(medicamento.getValorUnitario() * quantity);
                        service.crearVenta(nuevaVenta);
                        return ResponseEntity.ok(nuevaVenta);
                    }
                });
    }






}
