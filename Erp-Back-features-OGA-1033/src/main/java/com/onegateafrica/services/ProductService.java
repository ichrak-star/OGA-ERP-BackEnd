package com.onegateafrica.services;

import com.onegateafrica.Entity.AppelDoffreEntity;
import com.onegateafrica.Entity.Product;

import java.util.Optional;

public interface ProductService {
    public Optional<Product> getproductByIdForResponse(long id);
}
