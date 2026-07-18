package com.fintech.customerservice.client;

import com.fintech.customerservice.dto.ProductDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "product-service")
public interface ProductServiceClient {

    @GetMapping("/api/products/cliente/{clienteId}")
    List<ProductDTO> obtenerProductosPorCliente(@PathVariable("clienteId") Long clienteId);

}
