package com.fintech.customerservice.dto;

import java.math.BigDecimal;

public record ProductDTO(
    Long id,
    Long clienteId,
    String tipo,
    String descripcion,
    BigDecimal monto
) {
}
