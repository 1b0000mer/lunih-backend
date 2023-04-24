package com.gsquad.lunih.services.demo.product;

import com.gsquad.lunih.dtos.demo.ProductDTO;
import com.gsquad.lunih.entities.demo.Product;
import com.gsquad.lunih.repos.demo.ProductRepo;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

// For testing purposes
@Service
@Transactional
public class ProductServiceImpl implements ProductService {
    private final ProductRepo productRepo;

    public ProductServiceImpl(ProductRepo productRepo) {
        this.productRepo = productRepo;
    }

    @Override
    public List<Product> listAll() {
        return productRepo.findAll();
    }

    @Override
    public Product get(int id) {
        return productRepo.findById(id).get();
    }

    @Override
    public Product create(ProductDTO dto) {
        Product nProduct = new Product();
        nProduct.setName(dto.getName());
        nProduct.setPrice(dto.getPrice());
        productRepo.save(nProduct);
        return nProduct;
    }

    @Override
    public Product update(int id, ProductDTO dto) {
        Product nProduct = get(id);
        nProduct.setName(dto.getName());
        nProduct.setPrice(dto.getPrice());
        productRepo.save(nProduct);
        return nProduct;
    }

    @Override
    public Product delete(int id) {
        Product nProduct = get(id);
        productRepo.deleteById(nProduct.getId());
        return nProduct;
    }
}
