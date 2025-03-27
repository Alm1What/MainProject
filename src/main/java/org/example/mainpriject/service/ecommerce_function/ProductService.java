package org.example.mainpriject.service.ecommerce_function;

import org.example.mainpriject.dto.ecommerceDTO.ProductDto;
import org.example.mainpriject.mapper.ecommerce.ProductMapper;
import org.example.mainpriject.model.Product;
import org.example.mainpriject.model.User;
import org.example.mainpriject.repository.ProductRepository;
import org.example.mainpriject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final UserService userService;
    private final ProductMapper productMapper;

    @Autowired
    public ProductService(ProductRepository productRepository,
                          UserService userService,
                          ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.userService = userService;
        this.productMapper = productMapper;
    }




}
