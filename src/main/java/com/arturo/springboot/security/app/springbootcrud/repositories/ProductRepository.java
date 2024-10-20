package com.arturo.springboot.security.app.springbootcrud.repositories;

import org.springframework.data.repository.CrudRepository;

import com.arturo.springboot.security.app.springbootcrud.entities.Product;

public interface ProductRepository extends CrudRepository<Product, Long> {
    boolean existsBySku(String sku);
}
