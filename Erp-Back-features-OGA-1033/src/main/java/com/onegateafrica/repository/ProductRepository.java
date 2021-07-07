package com.onegateafrica.repository;

import com.onegateafrica.Entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;


import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * The Interface ProductRepository.
 *
 * @author devrobot
 * @version 1.0
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByCategoryId(long categoryId);
    Optional<Product> findByIdAndCategoryId(long id, long productId);
}
