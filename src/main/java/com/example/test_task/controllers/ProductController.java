package com.example.test_task.controllers;

import com.example.test_task.dto.ProductDto;
import com.example.test_task.entities.Product;
import com.example.test_task.services.ProductService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
public class ProductController {
    private final ProductService productService;

    @GetMapping
    public List<Product> getAll() {
        return productService.getAll();
    }

    @GetMapping("{id}")
    public Product getById(@PathVariable long id) {
        return productService.getById(id);
    }

    @GetMapping("best-rated")
    public Product getBestRated() {
        return productService.getByBestRating();
    }

    @GetMapping("cheapest")
    public Product getCheapest() {
        return productService.getByLowestPrice();
    }

    @GetMapping("most-expensive")
    public Product getMostExpensive() {
        return productService.getByHighestPrice();
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable long id) {
        productService.deleteById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Product create(@Valid @RequestBody ProductDto product) {
        return productService.add(product);
    }

    @PostMapping("{id}/uploadImage")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void uploadImage(@PathVariable long id,
                            @RequestPart("image") MultipartFile[] images,
                            HttpServletRequest request) throws BadRequestException {
        productService.uploadImage(id, images, request.getHeader("host"));
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Product update(@PathVariable long id,
                          @Valid @RequestBody ProductDto product) {
        return productService.update(id, product);
    }
}
