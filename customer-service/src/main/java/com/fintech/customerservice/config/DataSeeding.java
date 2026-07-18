package com.fintech.customerservice.config;

import com.fintech.customerservice.model.Customer;
import com.fintech.customerservice.repository.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;

@Configuration
public class DataSeeding {

    @Bean
    public CommandLineRunner loadData(CustomerRepository customerRepository) {
        return args -> {
            customerRepository.save(new Customer(
                null,
                "Juan Pérez",
                "12345678",
                "juan@example.com",
                new BigDecimal("10000.00")
            ));

            customerRepository.save(new Customer(
                null,
                "María García",
                "87654321",
                "maria@example.com",
                new BigDecimal("25000.00")
            ));
        };
    }

}
