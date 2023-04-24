package com.gsquad.lunih.services.demo.product;

import com.gsquad.lunih.dtos.demo.ProductDTO;
import com.gsquad.lunih.entities.demo.Product;

import java.util.List;

// For testing purposes
public interface ProductService {

    List<Product> listAll();

    Product get(int id);

    Product create(ProductDTO dto);

    Product update(int id, ProductDTO dto);

    Product delete(int id);
}
