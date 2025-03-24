package org.example.mainpriject.repository;

import org.example.mainpriject.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductRepository extends MongoRepository<Product, String> {

}
