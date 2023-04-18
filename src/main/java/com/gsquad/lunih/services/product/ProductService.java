package com.gsquad.lunih.services.product;

import com.gsquad.lunih.dtos.ProductDTO;
import com.gsquad.lunih.entities.Product;

import java.util.List;

// For testing purposes
public interface ProductService {

    List<Product> listAll();

    Product get(int id);

    Product create(ProductDTO dto);

    Product update(int id, ProductDTO dto);

    Product delete(int id);
}
