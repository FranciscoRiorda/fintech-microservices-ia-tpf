package com.fintech.productservice.service;

import com.fintech.productservice.dto.ProductDTO;
import com.fintech.productservice.model.Product;
import com.fintech.productservice.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<ProductDTO> obtenerProductosPorCliente(Long clienteId) {
        return productRepository.findByClienteId(clienteId)
            .stream()
            .map(this::entityToDTO)
            .toList();
    }

    public ProductDTO obtenerProductoPorId(Long id) {
        return productRepository.findById(id)
            .map(this::entityToDTO)
            .orElse(null);
    }

    public ProductDTO crearProducto(ProductDTO dto) {
        Product product = dtoToEntity(dto);
        Product saved = productRepository.save(product);
        return entityToDTO(saved);
    }

    public ProductDTO actualizarProducto(Long id, ProductDTO dto) {
        return productRepository.findById(id)
            .map(product -> {
                product.setClienteId(dto.clienteId());
                product.setTipo(dto.tipo());
                product.setDescripcion(dto.descripcion());
                product.setMonto(dto.monto());
                Product updated = productRepository.save(product);
                return entityToDTO(updated);
            })
            .orElse(null);
    }

    public boolean eliminarProducto(Long id) {
        if (productRepository.existsById(id)) {
            productRepository.deleteById(id);
            return true;
        }
        return false;
    }

    private ProductDTO entityToDTO(Product product) {
        return new ProductDTO(
            product.getId(),
            product.getClienteId(),
            product.getTipo(),
            product.getDescripcion(),
            product.getMonto()
        );
    }

    private Product dtoToEntity(ProductDTO dto) {
        Product product = new Product();
        product.setId(dto.id());
        product.setClienteId(dto.clienteId());
        product.setTipo(dto.tipo());
        product.setDescripcion(dto.descripcion());
        product.setMonto(dto.monto());
        return product;
    }

}
