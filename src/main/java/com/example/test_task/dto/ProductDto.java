package com.example.test_task.dto;

import com.example.test_task.annotations.Precision;
import com.example.test_task.entities.Image;
import jakarta.persistence.Column;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {
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
    private String category;
    private String color;
    @DecimalMin(value = "0.01")
    @Precision(2)
    private double price;
    private List<Image> images;
    private List<String> features;
}
