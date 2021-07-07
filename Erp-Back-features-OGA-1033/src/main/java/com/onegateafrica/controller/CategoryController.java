package com.onegateafrica.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;


import com.onegateafrica.Entity.Category;
import com.onegateafrica.exeption.NotFoundExeption;
import com.onegateafrica.repository.CategoryRepository;
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
import org.webjars.NotFoundException;


import javax.validation.Valid;

/**
 * The Class CategoryResource.
 *
 * @author devrobot
 * @version 1.0
 */
@RestController
@RequestMapping("/api")
public class CategoryController {




    private CategoryRepository categoryRepository;

    @Autowired
    public CategoryController(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }


    @GetMapping(value = "/categories")
    public List<Category> getAll() {
        return categoryRepository.findAll();
    }
    /**
     * Gets category.
     *
     * @return category if exists
     */
    @GetMapping("/categorie/{id}")
    public ResponseEntity<Optional<Category>> getCategoryId(
            @PathVariable(value = "id") long categoryId)  {
        Optional<Category> category = categoryRepository.findById(categoryId);
        if (category.isPresent()){
            return new ResponseEntity(category, HttpStatus.OK);
        }else{
            return new ResponseEntity("categorie introuvable",HttpStatus.NOT_FOUND);
        }


    }

    @PostMapping("/categorie")
    public Category createCategorie(@Valid @RequestBody Category categorie) {
        return categoryRepository.save(categorie);
    }
    /**
     * Delete category.
     *
     */
    @DeleteMapping("/categorie/{id}")
    public Map< String, Boolean > deleteUser(
            @PathVariable(value = "id") long categoryId) throws NotFoundExeption {

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new NotFoundExeption("Category not found :: " + categoryId));

        categoryRepository.delete(category);
        Map < String, Boolean > response = new HashMap< >();
        response.put("deleted", Boolean.TRUE);
        return response;
    }
    /**
     * Put category.
     */
    @PutMapping("/categorie/{id}")
    public ResponseEntity < Category > updateUser(
            @PathVariable(value = "id") long categoryId,
            @Valid @RequestBody Category userDetails) throws NotFoundExeption {
        Category cat = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new NotFoundException("Instructor not found :: " + categoryId));
        cat.setName(userDetails.getName());
        cat.setDescription(userDetails.getDescription());
        final Category updatedUser = categoryRepository.save(cat);
        return ResponseEntity.ok(updatedUser);
    }
}
