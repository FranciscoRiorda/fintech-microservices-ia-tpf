package com.fintech.customerservice.mapper;

import com.fintech.customerservice.dto.CustomerDTO;
import com.fintech.customerservice.model.Customer;

public class CustomerMapper {

    public static CustomerDTO toDTO(Customer customer) {
        return new CustomerDTO(
            customer.getId(),
            customer.getNombre(),
            customer.getDocumento(),
            customer.getCorreo(),
            customer.getSaldo()
        );
    }

    public static Customer toEntity(CustomerDTO dto) {
        Customer customer = new Customer();
        customer.setId(null); // As per previous code review, id should be ignored for creation
        customer.setNombre(dto.nombre());
        customer.setDocumento(dto.documento());
        customer.setCorreo(dto.correo());
        customer.setSaldo(dto.saldo());
        return customer;
    }
}
