package myapp.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.ValidatorFactory;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Set;
import myapp.domain.Product;
import myapp.domain.enumeration.ProductStatus;
import myapp.repository.ProductRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    private static Validator validator;

    @BeforeAll
    public static void setUpValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService; // Injects the mock into the service

    // Helper method to create a sample product with flexible parameters
    public static Product createProductSample(
        Long id,
        String title,
        String keywords,
        String description,
        int rating,
        int quantityInStock,
        String dimensions,
        BigDecimal price,
        ProductStatus status,
        Double weight,
        Instant dateAdded
    ) {
        Product product = new Product()
            .id(id)
            .title(title)
            .keywords(keywords)
            .description(description)
            .rating(rating)
            .quantityInStock(quantityInStock)
            .dimensions(dimensions)
            .price(price)
            .status(status)
            .weight(weight)
            .dateAdded(dateAdded);

        return product;
    }

    // BEGIN TEST CASES - (with example for Titile)
    @Test
    public void testProductTitle() {
        // T1
        // Valid case Title == 3 char
        Product productWithValidTitle = createProductSample(
            1L,
            "abc",
            null,
            null,
            1,
            1,
            null,
            BigDecimal.TEN,
            ProductStatus.IN_STOCK,
            null,
            Instant.now()
        );
        Set<ConstraintViolation<Product>> violations_valid = validator.validate(productWithValidTitle);
        // Assert
        System.err.println(violations_valid);
        assertTrue(violations_valid.isEmpty());
        when(productRepository.save(productWithValidTitle)).thenReturn(productWithValidTitle);
        Product savedProduct = productService.save(productWithValidTitle);
        assertEquals(productWithValidTitle, savedProduct);

        // T2
        // Valid case Title == 100 char
        Product productWithValidTitle100 = createProductSample(
            1L,
            "a".repeat(100),
            null,
            null,
            1,
            1,
            null,
            BigDecimal.TEN,
            ProductStatus.IN_STOCK,
            null,
            Instant.now()
        );
        Set<ConstraintViolation<Product>> violations_valid2 = validator.validate(productWithValidTitle100);
        // Assert
        System.err.println(violations_valid2);
        assertTrue(violations_valid2.isEmpty());
        when(productRepository.save(productWithValidTitle100)).thenReturn(productWithValidTitle100);
        Product savedProduct2 = productService.save(productWithValidTitle100);
        assertEquals(productWithValidTitle100, savedProduct2);

        // T3
        // Invalid case Title < 3 char
        Product productWithTwoCharTitle = createProductSample(
            1L,
            "ab",
            null,
            null,
            1,
            1,
            null,
            BigDecimal.TEN,
            ProductStatus.IN_STOCK,
            null,
            Instant.now()
        );
        Set<ConstraintViolation<Product>> violations_invalid = validator.validate(productWithTwoCharTitle);
        // Assert
        assertEquals("title", violations_invalid.iterator().next().getPropertyPath().toString());

        // T4
        // Invalid case Title > 101 char
        Product productWithHugeTitle = createProductSample(
            1L,
            "a".repeat(101),
            null,
            null,
            1,
            1,
            null,
            BigDecimal.TEN,
            ProductStatus.IN_STOCK,
            null,
            Instant.now()
        );
        Set<ConstraintViolation<Product>> violations_invalid2 = validator.validate(productWithHugeTitle);
        // Assert
        assertEquals("title", violations_invalid2.iterator().next().getPropertyPath().toString());

        // T5
        // Invalid case Title is empty
        Product productWithEmptyTitle = createProductSample(
            1L,
            "",
            null,
            null,
            1,
            1,
            null,
            BigDecimal.TEN,
            ProductStatus.IN_STOCK,
            null,
            Instant.now()
        );
        Set<ConstraintViolation<Product>> violations_invalid3 = validator.validate(productWithEmptyTitle);
        // Assert
        assertEquals("title", violations_invalid3.iterator().next().getPropertyPath().toString());
    }

    @Test
    public void testKeyWords() {
        // T6
        // Valid case Keywords == null
        Product productWithNullKeywords = createProductSample(
            1L,
            "Valid Title",
            null,
            null,
            1,
            1,
            null,
            BigDecimal.TEN,
            ProductStatus.IN_STOCK,
            null,
            Instant.now()
        );
        Set<ConstraintViolation<Product>> violations_valid = validator.validate(productWithNullKeywords);
        // Assert
        System.err.println(violations_valid);
        assertTrue(violations_valid.isEmpty());
        when(productRepository.save(productWithNullKeywords)).thenReturn(productWithNullKeywords);
        Product savedProduct = productService.save(productWithNullKeywords);
        assertEquals(productWithNullKeywords, savedProduct);

        // T7
        // Valid case Keywords == 0 char (empty)
        Product productWithEmptyKeywords = createProductSample(
            1L,
            "Valid Title",
            "",
            null,
            1,
            1,
            null,
            BigDecimal.TEN,
            ProductStatus.IN_STOCK,
            null,
            Instant.now()
        );
        Set<ConstraintViolation<Product>> violations_valid2 = validator.validate(productWithEmptyKeywords);
        // Assert
        System.err.println(violations_valid2);
        assertTrue(violations_valid2.isEmpty());
        when(productRepository.save(productWithEmptyKeywords)).thenReturn(productWithEmptyKeywords);
        Product savedProduct2 = productService.save(productWithEmptyKeywords);
        assertEquals(productWithEmptyKeywords, savedProduct2);

        // T8
        // Valid case Keywords == 1 char
        Product productWithOneCharKeywords = createProductSample(
            1L,
            "Valid Title",
            "a",
            null,
            1,
            1,
            null,
            BigDecimal.TEN,
            ProductStatus.IN_STOCK,
            null,
            Instant.now()
        );
        Set<ConstraintViolation<Product>> violations_valid3 = validator.validate(productWithOneCharKeywords);
        // Assert
        System.err.println(violations_valid3);
        assertTrue(violations_valid3.isEmpty());
        when(productRepository.save(productWithOneCharKeywords)).thenReturn(productWithOneCharKeywords);
        Product savedProduct3 = productService.save(productWithOneCharKeywords);
        assertEquals(productWithOneCharKeywords, savedProduct3);

        // T9
        // Valid case Keywords == 200 char
        Product productWith200CharKeywords = createProductSample(
            1L,
            "Valid Title",
            "a".repeat(200),
            null,
            1,
            1,
            null,
            BigDecimal.TEN,
            ProductStatus.IN_STOCK,
            null,
            Instant.now()
        );
        Set<ConstraintViolation<Product>> violations_valid4 = validator.validate(productWith200CharKeywords);
        // Assert
        System.err.println(violations_valid4);
        assertTrue(violations_valid4.isEmpty());
        when(productRepository.save(productWith200CharKeywords)).thenReturn(productWith200CharKeywords);
        Product savedProduct4 = productService.save(productWith200CharKeywords);
        assertEquals(productWith200CharKeywords, savedProduct4);

        // T10
        // Invalid case Keywords > 200 char
        Product productWithOver200CharKeywords = createProductSample(
            1L,
            "Valid Title",
            "a".repeat(201),
            null,
            1,
            1,
            null,
            BigDecimal.TEN,
            ProductStatus.IN_STOCK,
            null,
            Instant.now()
        );
        Set<ConstraintViolation<Product>> violations_invalid = validator.validate(productWithOver200CharKeywords);
        // Assert
        assertEquals("keywords", violations_invalid.iterator().next().getPropertyPath().toString());
    }

    @Test
    public void testDescription() {
        // T11
        // Valid case Description == null
        Product productWithNullDescription = createProductSample(
            1L,
            "Valid Title",
            null,
            null,
            1,
            1,
            null,
            BigDecimal.TEN,
            ProductStatus.IN_STOCK,
            null,
            Instant.now()
        );
        Set<ConstraintViolation<Product>> violations_valid = validator.validate(productWithNullDescription);
        // Assert
        System.err.println(violations_valid);
        assertTrue(violations_valid.isEmpty());
        when(productRepository.save(productWithNullDescription)).thenReturn(productWithNullDescription);
        Product savedProduct = productService.save(productWithNullDescription);
        assertEquals(productWithNullDescription, savedProduct);

        // T12
        // Valid case Description == 0 char (empty)
        Product productWithEmptyDescription = createProductSample(
            1L,
            "Valid Title",
            "",
            null,
            1,
            1,
            null,
            BigDecimal.TEN,
            ProductStatus.IN_STOCK,
            null,
            Instant.now()
        );
        Set<ConstraintViolation<Product>> violations_valid2 = validator.validate(productWithEmptyDescription);
        // Assert
        System.err.println(violations_valid2);
        assertTrue(violations_valid2.isEmpty());
        when(productRepository.save(productWithEmptyDescription)).thenReturn(productWithEmptyDescription);
        Product savedProduct2 = productService.save(productWithEmptyDescription);
        assertEquals(productWithEmptyDescription, savedProduct2);

        // T13
        // Valid case Description == 50 char
        Product productWith50CharDescription = createProductSample(
            1L,
            "Valid Title",
            "a".repeat(50),
            null,
            1,
            1,
            null,
            BigDecimal.TEN,
            ProductStatus.IN_STOCK,
            null,
            Instant.now()
        );
        Set<ConstraintViolation<Product>> violations_valid3 = validator.validate(productWith50CharDescription);
        // Assert
        System.err.println(violations_valid3);
        assertTrue(violations_valid3.isEmpty());
        when(productRepository.save(productWith50CharDescription)).thenReturn(productWith50CharDescription);
        Product savedProduct3 = productService.save(productWith50CharDescription);
        assertEquals(productWith50CharDescription, savedProduct3);

        // T14
        // Valid case Description > 50 char
        Product productWithOver50CharDescription = createProductSample(
            1L,
            "Valid Title",
            "a".repeat(51),
            null,
            1,
            1,
            null,
            BigDecimal.TEN,
            ProductStatus.IN_STOCK,
            null,
            Instant.now()
        );
        Set<ConstraintViolation<Product>> violations_valid4 = validator.validate(productWithOver50CharDescription);
        // Assert
        System.err.println(violations_valid4);
        assertTrue(violations_valid4.isEmpty());
        when(productRepository.save(productWithOver50CharDescription)).thenReturn(productWithOver50CharDescription);
        Product savedProduct4 = productService.save(productWithOver50CharDescription);
        assertEquals(productWithOver50CharDescription, savedProduct4);

        // T15
        // Invalid case Description < 50 char
        Product productWith49CharDescription = createProductSample(
            1L,
            "Valid Title",
            null,
            "a".repeat(49),
            1,
            1,
            null,
            BigDecimal.TEN,
            ProductStatus.IN_STOCK,
            null,
            Instant.now()
        );
        Set<ConstraintViolation<Product>> violations_invalid = validator.validate(productWith49CharDescription);
        // Assert
        assertEquals("description", violations_invalid.iterator().next().getPropertyPath().toString());

        // T16
        // Invalid case Description == 1 char
        Product productWith1CharDescription = createProductSample(
            1L,
            "Valid Title",
            null,
            "a",
            1,
            1,
            null,
            BigDecimal.TEN,
            ProductStatus.IN_STOCK,
            null,
            Instant.now()
        );
        Set<ConstraintViolation<Product>> violations_invalid2 = validator.validate(productWith1CharDescription);
        // Assert
        assertEquals("description", violations_invalid2.iterator().next().getPropertyPath().toString());
    }
}
