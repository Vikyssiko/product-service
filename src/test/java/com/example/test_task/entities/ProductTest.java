package com.example.test_task.entities;

import com.example.test_task.mothers.ProductMother;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;

public class ProductTest {
    @Nested
    public class AddImage {
        @Test
        void shouldAddImageWhenImageIsNotNull() {
            Image image = new Image("image", "url");
            Product product = ProductMother.create().id(null).build();

            product.addImage(image);

            Assertions.assertEquals(image, product.getImages().get(0));
            Assertions.assertEquals(product, image.getProduct());
        }
    }

    @Nested
    public class SetAllFields {
        @Test
        void shouldSetFieldsWhenFieldsArePresent() {
            List<String> features = List.of("feature", "another feature");
            Product product = ProductMother.create()
                    .id(null)
                    .brand("Lenovo")
                    .model("1234book")
                    .color("black")
                    .weight("1.2 kg")
                    .features(features).build();
            String description = "product, бренд: Lenovo, модель: 1234book, цвет: black, вес: 1.2 kg, " +
                    "особенности: feature, another feature";
            Product expected = ProductMother.create()
                    .id(null)
                    .brand("Lenovo")
                    .model("1234book")
                    .color("black")
                    .weight("1.2 kg")
                    .features(features)
                    .description(description).build();
            Product actual = ProductMother.create().id(null).build();

            actual.setAllFields(product);

            Assertions.assertEquals(expected, actual);
        }
    }

    @Nested
    public class CreateDescription {
        @Test
        void shouldCreateDescriptionWhenAllFieldsArePresent() {
            List<String> features = List.of("feature", "another feature");
            Product product = ProductMother.create().id(null).features(features).build();
            String expectedDescription = "product, бренд: brand, модель: model, цвет: black, вес: 1 kg, " +
                    "особенности: feature, another feature";

            product.createDescription();

            Assertions.assertEquals(expectedDescription, product.getDescription());
        }

        @Test
        void shouldCreateDescriptionWhenAllFieldsAreNull() {
            Product product = ProductMother.create().id(null).brand(null).model(null).color(null).weight(null).build();
            String expectedDescription = "product";

            product.createDescription();

            Assertions.assertEquals(expectedDescription, product.getDescription());
        }
    }
}
