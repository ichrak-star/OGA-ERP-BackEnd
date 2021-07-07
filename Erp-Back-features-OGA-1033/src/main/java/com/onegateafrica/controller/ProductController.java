package com.onegateafrica.controller;

import com.onegateafrica.Entity.Category;
import com.onegateafrica.Entity.Product;
import com.onegateafrica.exeption.NotFoundExeption;
import com.onegateafrica.repository.CategoryRepository;
import com.onegateafrica.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

/**
 * The Class ProductResource.
 *
 * @author devrobot
 * @version 1.0
 */
@RestController
@RequestMapping("/api")
public class ProductController {

    /**
     * The product repository.
     */
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    /**
     * Gets all products.
     *
     * @return all products
     */
    @GetMapping("/categorie/{categoryId}/product")
    public List<Product> getProductsByCategory(@PathVariable(value = "categoryId") long categoryId) {
        Optional<Category> category = categoryRepository.findById(categoryId);

        return category.get().getProducts();


    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<Optional<Product>> getProductId(
            @PathVariable(value = "productId") long productId)  {
        Optional<Product> produit = productRepository.findById(productId);
        if (produit.isPresent()){
            return new ResponseEntity<Optional<Product>>(produit, HttpStatus.OK);
        }

        return new ResponseEntity("produit introuvable",HttpStatus.NOT_FOUND);
    }


    @PostMapping("/product")
    public Product Createproduct(@RequestBody final Product product) {
        return productRepository.save(product);

    }

    @PostMapping("/categorie/{categoryId}/product")
    public Product createProduct(@PathVariable(value = "categoryId") long categoryId,
                                 @Valid @RequestBody Product product) throws NotFoundExeption {
        return categoryRepository.findById(categoryId).map(category -> {
            product.setCategory(category);
            return productRepository.save(product);
        }).orElseThrow(() -> new NotFoundExeption("category not found"));
    }


//    @DeleteMapping(value = "/product/{id}")
//    public Void delete(@PathVariable(name = "id") long id) {
//
//        return  productRepository.deleteById(id);
//    }

    @PutMapping("/categorie/{categorieId}/product/{productId}")
    public Product updateProduct(@PathVariable(value = "categorieId") long categoryId,
                                 @PathVariable(value = "productId") long productId, @Valid @RequestBody Product productRequest)
            throws NotFoundExeption {
        if (!categoryRepository.existsById(categoryId)) {
            throw new NotFoundExeption("categoryId not found");
        }

        return productRepository.findById(productId).map(product -> {
            product.setTitle(productRequest.getTitle());
            product.setDescription(productRequest.getDescription());
            product.setPrice(productRequest.getPrice());
            product.setWeight(productRequest.getWeight());
            product.setQuantity(productRequest.getQuantity());
            return productRepository.save(product);
        }).orElseThrow(() -> new NotFoundExeption("product id not found"));
    }

    @DeleteMapping("/categorie/{categorieId}/product/{productId}")
    public ResponseEntity<?> deleteProduct(@PathVariable(value = "categorieId") long categoryId,
                                           @PathVariable(value = "productId") long productId) throws NotFoundExeption {
        return productRepository.findByIdAndCategoryId(productId, categoryId).map(product -> {
            productRepository.delete(product);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new NotFoundExeption(
                "product not found with id " + productId + " and categoryId " + categoryId));

    }
}
