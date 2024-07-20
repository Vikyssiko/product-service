package com.example.test_task.converters;

import com.example.test_task.dto.ProductDto;
import com.example.test_task.entities.Product;
import org.mapstruct.Mapper;
import org.springframework.core.convert.converter.Converter;

@Mapper(componentModel = "spring")
public interface ProductDtoToProductConverter extends Converter<ProductDto, Product> {
    @Override
    Product convert(final ProductDto source);
}
