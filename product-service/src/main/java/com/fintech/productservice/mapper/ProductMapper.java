package com.fintech.productservice.mapper;

import com.fintech.productservice.dto.ProductDTO;
import com.fintech.productservice.model.Product;

public class ProductMapper {

    public static ProductDTO toDTO(Product product) {
        return new ProductDTO(
            product.getId(),
            product.getClienteId(),
            product.getTipo(),
            product.getDescripcion(),
            product.getMonto()
        );
    }

    public static Product toEntity(ProductDTO dto) {
        Product product = new Product();
        product.setId(dto.id());
        product.setClienteId(dto.clienteId());
        product.setTipo(dto.tipo());
        product.setDescripcion(dto.descripcion());
        product.setMonto(dto.monto());
        return product;
    }
}
