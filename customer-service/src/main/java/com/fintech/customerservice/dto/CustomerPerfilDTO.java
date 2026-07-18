package com.fintech.customerservice.dto;

import java.math.BigDecimal;
import java.util.List;

public record CustomerPerfilDTO(
    Long id,
    String nombre,
    String documento,
    String correo,
    BigDecimal saldo,
    List<ProductDTO> productos
) {
}
