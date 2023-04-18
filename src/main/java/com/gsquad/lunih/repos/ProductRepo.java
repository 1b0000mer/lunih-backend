package com.gsquad.lunih.repos;

import com.gsquad.lunih.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

// For testing purposes
public interface ProductRepo extends JpaRepository<Product, Integer> {

}
