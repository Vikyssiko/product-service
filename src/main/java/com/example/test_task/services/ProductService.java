package com.example.test_task.services;

import com.example.test_task.dto.ProductDto;
import com.example.test_task.entities.Product;
import com.example.test_task.repositories.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final RemoveBackgroundService removeBackgroundService;
    private final ConversionService conversionService;

    public List<Product> getAll() {
        return productRepository.findAll();
    }

    public Product getById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product with id " + id + " not found"));
    }

    public Product getByBestRating() {
        return productRepository.findTopByOrderByRatingDesc()
                .orElseThrow(() -> new EntityNotFoundException("There is no product in a database"));
    }

    public Product getByLowestPrice() {
        return productRepository.findTopByOrderByPriceAsc()
                .orElseThrow(() -> new EntityNotFoundException("There is no product in a database"));
    }

    public Product getByHighestPrice() {
        return productRepository.findTopByOrderByPriceDesc()
                .orElseThrow(() -> new EntityNotFoundException("There is no product in a database"));
    }

    @Transactional
    public void deleteById(long id) {
        productRepository.deleteById(id);
    }

    @Transactional
    public void uploadImage(Long id, MultipartFile[] images, String host) throws BadRequestException {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product with id " + id + " not found"));
        for (MultipartFile image : images) {
            try {
                product.addImage(removeBackgroundService.removeBackground(image, id, host));
            } catch (IOException | InterruptedException e) {
                throw new BadRequestException(e.getMessage());
            }
            productRepository.save(product);
        }
    }

    @Transactional
    public Product add(ProductDto productDto) {
        Product product = conversionService.convert(productDto, Product.class);
        product.createDescription();
        return productRepository.save(product);
    }

    @Transactional
    public Product update(long id, ProductDto productDto) {
        Product newProduct = conversionService.convert(productDto, Product.class);
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product with id: " + id + " not found"));
        product.setAllFields(newProduct);
        return productRepository.save(product);
    }
}
