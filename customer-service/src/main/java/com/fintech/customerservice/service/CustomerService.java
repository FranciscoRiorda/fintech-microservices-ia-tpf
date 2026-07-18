package com.fintech.customerservice.service;

import com.fintech.customerservice.client.ProductServiceClient;
import com.fintech.customerservice.dto.CustomerDTO;
import com.fintech.customerservice.dto.CustomerPerfilDTO;
import com.fintech.customerservice.dto.ProductDTO;
import com.fintech.customerservice.model.Customer;
import com.fintech.customerservice.repository.CustomerRepository;
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
            .map(this::entityToDTO)
            .toList();
    }

    public CustomerDTO obtenerPorId(Long id) {
        return customerRepository.findById(id)
            .map(this::entityToDTO)
            .orElse(null);
    }

    public CustomerDTO crearCliente(CustomerDTO dto) {
        Customer customer = dtoToEntity(dto);
        Customer saved = customerRepository.save(customer);
        return entityToDTO(saved);
    }

    public CustomerDTO actualizarCliente(Long id, CustomerDTO dto) {
        return customerRepository.findById(id)
            .map(customer -> {
                customer.setNombre(dto.nombre());
                customer.setDocumento(dto.documento());
                customer.setCorreo(dto.correo());
                customer.setSaldo(dto.saldo());
                Customer updated = customerRepository.save(customer);
                return entityToDTO(updated);
            })
            .orElse(null);
    }

    public CustomerPerfilDTO obtenerPerfilCompleto(Long id) {
        Customer customer = customerRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Cliente no encontrado con ID: " + id));

        List<ProductDTO> productos = productServiceClient.obtenerProductosPorCliente(id);

        return new CustomerPerfilDTO(
            customer.getId(),
            customer.getNombre(),
            customer.getDocumento(),
            customer.getCorreo(),
            customer.getSaldo(),
            productos
        );
    }

    private CustomerDTO entityToDTO(Customer customer) {
        return new CustomerDTO(
            customer.getId(),
            customer.getNombre(),
            customer.getDocumento(),
            customer.getCorreo(),
            customer.getSaldo()
        );
    }

    private Customer dtoToEntity(CustomerDTO dto) {
        Customer customer = new Customer();
        customer.setId(dto.id());
        customer.setNombre(dto.nombre());
        customer.setDocumento(dto.documento());
        customer.setCorreo(dto.correo());
        customer.setSaldo(dto.saldo());
        return customer;
    }

}
