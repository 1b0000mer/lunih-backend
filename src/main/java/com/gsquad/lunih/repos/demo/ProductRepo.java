package com.gsquad.lunih.repos.demo;

import com.gsquad.lunih.entities.demo.Product;
import org.springframework.data.jpa.repository.JpaRepository;

// For testing purposes
public interface ProductRepo extends JpaRepository<Product, Integer> {

}
