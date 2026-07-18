package com.fintech.productservice.config;

import com.fintech.productservice.model.Product;
import com.fintech.productservice.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;

@Configuration
public class DataSeeding {

    @Bean
    public CommandLineRunner loadData(ProductRepository productRepository) {
        return args -> {
            productRepository.save(new Product(
                null,
                1L,
                "CUENTA",
                "Cuenta Corriente de Ejemplo",
                new BigDecimal("5000.00")
            ));

            productRepository.save(new Product(
                null,
                1L,
                "TARJETA",
                "Tarjeta de Crédito Visa",
                new BigDecimal("10000.00")
            ));

            productRepository.save(new Product(
                null,
                2L,
                "PRESTAMO",
                "Préstamo Personal",
                new BigDecimal("50000.00")
            ));

            productRepository.save(new Product(
                null,
                2L,
                "INVERSION",
                "Fondo Común de Inversión",
                new BigDecimal("25000.00")
            ));
        };
    }

}
