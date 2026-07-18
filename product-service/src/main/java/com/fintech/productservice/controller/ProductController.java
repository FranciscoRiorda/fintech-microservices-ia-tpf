package com.fintech.productservice.controller;

import com.fintech.productservice.dto.ProductDTO;
import com.fintech.productservice.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<ProductDTO>> obtenerProductosPorCliente(@PathVariable Long clienteId) {
        List<ProductDTO> productos = productService.obtenerProductosPorCliente(clienteId);
        return ResponseEntity.ok(productos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> obtenerProducto(@PathVariable Long id) {
        ProductDTO producto = productService.obtenerProductoPorId(id);
        if (producto == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(producto);
    }

    @PostMapping
    public ResponseEntity<ProductDTO> crearProducto(@RequestBody ProductDTO productDTO) {
        ProductDTO created = productService.crearProducto(productDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDTO> actualizarProducto(@PathVariable Long id, @RequestBody ProductDTO productDTO) {
        ProductDTO updated = productService.actualizarProducto(id, productDTO);
        if (updated == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarProducto(@PathVariable Long id) {
        boolean deleted = productService.eliminarProducto(id);
        if (!deleted) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }

}
