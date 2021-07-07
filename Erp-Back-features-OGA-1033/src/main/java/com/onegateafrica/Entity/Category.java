package com.onegateafrica.Entity;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
/**
 * The Class Category.
 *
 * @author devrobot
 * @version 1.0
 */
@Entity
@AllArgsConstructor
public class Category {
    /** The id. */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", unique = true, nullable = false)
    private long id;

    /** The name. */
    @Column(name = "name", unique = true, nullable = false)
    private String name;

    /** The description **/
    @Column(name = "description")
    private String description;

    /** The products. */
    @OneToMany(mappedBy = "category"
            , orphanRemoval = true, cascade = CascadeType.PERSIST)
    private List<Product> products;

    /**
     * Empty Constructor. Instantiates a new category.
     */
    public Category() {
    }


    public Category(Integer id, String name,String description, List<Category> children) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    /**
     * Gets the id.
     *
     * @return the id
     */
    public long getId() {
        return id;
    }

    /**
     * Sets the id.
     *
     * @param id the new id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Gets the name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name.
     *
     * @param name the new name
     */
    public void setName(String name) {
        this.name = name;
    }
    /**
     * Gets the description.
     *
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description.
     *
     * @param description the new name
     */
    public void setDescription(String description) {
        this.description = description;
    }

    public void setProducts(List < Product > products) {
        this.products = products;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<Product> getProducts() {
        return products;
    }
}
