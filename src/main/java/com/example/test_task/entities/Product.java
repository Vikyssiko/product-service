package com.example.test_task.entities;

import com.example.test_task.annotations.Precision;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@SuperBuilder
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    private String name;
    private String brand;
    private String model;
    @Min(0)
    @Column(name = "amount")
    private long availableAmount;
    private String weight;
    @DecimalMin(value = "0.0")
    @DecimalMax(value = "5.0")
    @Precision
    private double rating;
    @NotBlank
    private String category;
    @NotBlank
    private String description;
    private String color;
    @DecimalMin(value = "0.01")
    @Precision(value = 2)
    private double price;
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<Image> images;
    @ElementCollection
    @CollectionTable(name = "product_features", joinColumns = @JoinColumn(name = "product_id"))
    @Column(name = "feature")
    private List<String> features;

    public void addImage(Image image) {
        this.images.add(image);
        image.setProduct(this);
    }

    public void setAllFields(Product newProduct) {
        this.name = newProduct.getName();
        this.brand = newProduct.getBrand();
        this.model = newProduct.getModel();
        this.availableAmount = newProduct.getAvailableAmount();
        this.weight = newProduct.getWeight();
        this.rating = newProduct.getRating();
        this.category = newProduct.getCategory();
        this.description = newProduct.getDescription();
        this.color = newProduct.getColor();
        this.price = newProduct.getPrice();

        if (newProduct.getFeatures() != null) {
            this.features = newProduct.getFeatures();
        }
        createDescription();
    }

    public void createDescription() {
        StringBuilder description = new StringBuilder(this.name);
        if (this.brand != null) {
            description.append(", бренд: ").append(this.brand);
        }
        if (this.model != null) {
            description.append(", модель: ").append(this.model);
        }
        if (this.color != null) {
            description.append(", цвет: ").append(this.color);
        }
        if (this.weight != null) {
            description.append(", вес: ").append(this.weight);
        }
        if (this.features != null && !this.features.isEmpty()) {
            description.append(", особенности: ").append(String.join(", ", this.features));
        }
        this.description =  description.toString();
    }
}
