package com.fintech.customerservice.service;

import com.fintech.customerservice.client.ProductServiceClient;
import com.fintech.customerservice.dto.CustomerDTO;
import com.fintech.customerservice.dto.CustomerPerfilDTO;
import com.fintech.customerservice.dto.ProductDTO;
import com.fintech.customerservice.exception.ClienteNoEncontradoException;
import com.fintech.customerservice.model.Customer;
import com.fintech.customerservice.repository.CustomerRepository;
import com.fintech.customerservice.mapper.CustomerMapper; // Import CustomerMapper
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final ProductServiceClient productServiceClient;

    public CustomerService(CustomerRepository customerRepository, ProductServiceClient productServiceClient) {
        this.customerRepository = customerRepository;
        this.productServiceClient = productServiceClient;
    }

    public List<CustomerDTO> obtenerTodos() {
        return customerRepository.findAll()
            .stream()
            .map(CustomerMapper::toDTO) // Use CustomerMapper
            .toList();
    }

    public CustomerDTO obtenerPorId(Long id) {
        return customerRepository.findById(id)
            .map(CustomerMapper::toDTO) // Use CustomerMapper
            .orElse(null);
    }

    public CustomerDTO crearCliente(CustomerDTO dto) {
        Customer customer = CustomerMapper.toEntity(dto); // Use CustomerMapper
        Customer saved = customerRepository.save(customer);
        return CustomerMapper.toDTO(saved); // Use CustomerMapper
    }

    public CustomerDTO actualizarCliente(Long id, CustomerDTO dto) {
        return customerRepository.findById(id)
            .map(customer -> {
                customer.setNombre(dto.nombre());
                customer.setDocumento(dto.documento());
                customer.setCorreo(dto.correo());
                customer.setSaldo(dto.saldo());
                Customer updated = customerRepository.save(customer);
                return CustomerMapper.toDTO(updated); // Use CustomerMapper
            })
            .orElse(null);
    }

    public CustomerPerfilDTO obtenerPerfilCompleto(Long id) {
        Customer customer = customerRepository.findById(id)
            .orElseThrow(() -> new ClienteNoEncontradoException(id));

        List<ProductDTO> productos = productServiceClient.obtenerProductosPorCliente(id);

        // Use CustomerMapper for customer data
        CustomerDTO customerDTO = CustomerMapper.toDTO(customer);

        return new CustomerPerfilDTO(
            customerDTO.id(),
            customerDTO.nombre(),
            customerDTO.documento(),
            customerDTO.correo(),
            customerDTO.saldo(),
            productos
        );
    }
}
