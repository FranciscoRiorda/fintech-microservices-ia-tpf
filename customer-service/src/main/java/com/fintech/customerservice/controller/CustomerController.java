package com.fintech.customerservice.controller;

import com.fintech.customerservice.dto.CustomerDTO;
import com.fintech.customerservice.dto.CustomerPerfilDTO;
import com.fintech.customerservice.service.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public ResponseEntity<List<CustomerDTO>> obtenerTodos() {
        List<CustomerDTO> customers = customerService.obtenerTodos();
        return ResponseEntity.ok(customers);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerDTO> obtenerCliente(@PathVariable Long id) {
        CustomerDTO customer = customerService.obtenerPorId(id);
        if (customer == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(customer);
    }

    @PostMapping
    public ResponseEntity<CustomerDTO> crearCliente(@RequestBody CustomerDTO customerDTO) {
        CustomerDTO created = customerService.crearCliente(customerDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomerDTO> actualizarCliente(@PathVariable Long id, @RequestBody CustomerDTO customerDTO) {
        CustomerDTO updated = customerService.actualizarCliente(id, customerDTO);
        if (updated == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updated);
    }

    @GetMapping("/{id}/perfil")
    public ResponseEntity<CustomerPerfilDTO> obtenerPerfilCompleto(@PathVariable Long id) {
        try {
            CustomerPerfilDTO perfil = customerService.obtenerPerfilCompleto(id);
            return ResponseEntity.ok(perfil);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

}
