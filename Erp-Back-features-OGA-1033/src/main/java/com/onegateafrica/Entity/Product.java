package com.onegateafrica.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * The Class Product.
 *
 * @author devrobot
 * @version 1.0
 */
@Entity
public class Product {

    /** The id. */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", unique = true, nullable = false)
    private long id;

    /** The title. */
    @Column(name = "title", nullable = true)
    private String title;

    /** The description. */
    @Column(name = "description")
    private String description;

    /** The weight. */
    @Column(name = "weight")
    private double weight;

    /** The price. */
    @Column(name = "price", nullable = false)
    private double price;

    /** The quantity */
    private double quantity;

    /** The category. */
    @ManyToOne()
    @JoinColumn(name="category_id")
    @JsonIgnore
    private Category category;

    /**
     * Empty Constructor. Instantiates a new product.
     */
    public Product() {
    }


    public Product(Integer id, String title, String description, double weight, double price,
                   double quantity, Category category) {
        super();
        this.id = id;
        this.title = title;
        this.description = description;
        this.weight = weight;
        this.price = price;
        this.quantity = quantity;
        this.category = category;
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
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Gets the name.
     *
     * @return the name
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the name.
     *
     * @param title the new name
     */
    public void setTitle(String title) {
        this.title = title;
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
     * @param description the new description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets the price.
     *
     * @return the price
     */
    public double getPrice() {
        return price;
    }

    /**
     * Sets the price.
     *
     * @param price the new price
     */
    public void setPrice(double price) {
        this.price = price;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    /**
     * Gets the category.
     *
     * @return the category
     */
    public Category getCategory() {
        return category;
    }

    /**
     * Sets the category.
     *
     * @param category the new category
     */
    public void setCategory(Category category) {
        this.category = category;
    }

    /**
     * Gets the weight.
     *
     * @return the weight
     */
    public double getWeight() {
        return weight;
    }

    /**
     * Sets the weight.
     *
     * @param weight the new weight
     */
    public void setWeight(double weight) {
        this.weight = weight;
    }
}
