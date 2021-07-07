package com.onegateafrica.services;

import com.onegateafrica.Entity.Product;
import com.onegateafrica.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    private ProductRepository productRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Optional<Product> getproductByIdForResponse(long id) {
        return productRepository.findById(id);
    }
}
