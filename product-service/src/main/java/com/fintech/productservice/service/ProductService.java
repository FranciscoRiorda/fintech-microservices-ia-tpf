package com.fintech.productservice.service;

import com.fintech.productservice.dto.ProductDTO;
import com.fintech.productservice.model.Product;
import com.fintech.productservice.repository.ProductRepository;
import com.fintech.productservice.mapper.ProductMapper; // Import ProductMapper
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
            .map(ProductMapper::toDTO) // Use ProductMapper
            .toList();
    }

    public ProductDTO obtenerProductoPorId(Long id) {
        return productRepository.findById(id)
            .map(ProductMapper::toDTO) // Use ProductMapper
            .orElse(null);
    }

    public ProductDTO crearProducto(ProductDTO dto) {
        Product product = ProductMapper.toEntity(dto); // Use ProductMapper
        Product saved = productRepository.save(product);
        return ProductMapper.toDTO(saved); // Use ProductMapper
    }

    public ProductDTO actualizarProducto(Long id, ProductDTO dto) {
        return productRepository.findById(id)
            .map(product -> {
                product.setClienteId(dto.clienteId());
                product.setTipo(dto.tipo());
                product.setDescripcion(dto.descripcion());
                product.setMonto(dto.monto());
                Product updated = productRepository.save(product);
                return ProductMapper.toDTO(updated); // Use ProductMapper
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
}
