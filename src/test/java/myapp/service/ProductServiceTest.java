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
        Integer rating,
        Integer quantityInStock,
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

    @Test
    public void testRating() {
        // T17
        // Valid case Rating == null
        Product productWithNoRating = createProductSample(
            1L,
            "Valid Title",
            null,
            null,
            null,
            1,
            null,
            BigDecimal.TEN,
            ProductStatus.IN_STOCK,
            null,
            Instant.now()
        );
        Set<ConstraintViolation<Product>> violations_valid = validator.validate(productWithNoRating);
        // Assert
        System.err.println(violations_valid);
        assertTrue(violations_valid.isEmpty());
        when(productRepository.save(productWithNoRating)).thenReturn(productWithNoRating);
        Product savedProduct = productService.save(productWithNoRating);
        assertEquals(productWithNoRating, savedProduct);

        // T18
        // Valid case Rating == 1
        Product productWithRating1 = createProductSample(
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
        Set<ConstraintViolation<Product>> violations_valid2 = validator.validate(productWithRating1);
        // Assert
        System.err.println(violations_valid2);
        assertTrue(violations_valid2.isEmpty());
        when(productRepository.save(productWithRating1)).thenReturn(productWithRating1);
        Product savedProduct2 = productService.save(productWithRating1);
        assertEquals(productWithRating1, savedProduct2);

        // T19
        // Valid case Rating == 10
        Product productWithRating10 = createProductSample(
            1L,
            "Valid Title",
            null,
            null,
            10,
            1,
            null,
            BigDecimal.TEN,
            ProductStatus.IN_STOCK,
            null,
            Instant.now()
        );
        Set<ConstraintViolation<Product>> violations_valid3 = validator.validate(productWithRating10);
        // Assert
        System.err.println(violations_valid3);
        assertTrue(violations_valid3.isEmpty());
        when(productRepository.save(productWithRating10)).thenReturn(productWithRating10);
        Product savedProduct3 = productService.save(productWithRating10);
        assertEquals(productWithRating10, savedProduct3);

        // T20
        // Invalid case Rating == 0
        Product productWithRating0 = createProductSample(
            1L,
            "Valid Title",
            null,
            null,
            0,
            1,
            null,
            BigDecimal.TEN,
            ProductStatus.IN_STOCK,
            null,
            Instant.now()
        );
        Set<ConstraintViolation<Product>> violations_invalid = validator.validate(productWithRating0);
        // Assert
        assertEquals("rating", violations_invalid.iterator().next().getPropertyPath().toString());

        // T21
        // Invalid case Rating == 11
        Product productWithRating11 = createProductSample(
            1L,
            "Valid Title",
            null,
            null,
            11,
            1,
            null,
            BigDecimal.TEN,
            ProductStatus.IN_STOCK,
            null,
            Instant.now()
        );
        Set<ConstraintViolation<Product>> violations_invalid2 = validator.validate(productWithRating11);
        // Assert
        assertEquals("rating", violations_invalid2.iterator().next().getPropertyPath().toString());
        // T22
        // Invalid case Rating == 5.5
        // This test case is not applicable in Java as price is of type BigDecimal

        // T23
        // Invalid case Rating == string
        // This test case is not applicable in Java as rating is of type Integer
    }

    @Test
    public void testPrice() {
        // T24
        // Valid case Price == 1
        Product productWithPrice1 = createProductSample(
            1L,
            "Valid Title",
            null,
            null,
            1,
            1,
            null,
            BigDecimal.valueOf(1),
            ProductStatus.IN_STOCK,
            null,
            Instant.now()
        );
        Set<ConstraintViolation<Product>> violations_valid = validator.validate(productWithPrice1);
        // Assert
        System.err.println(violations_valid);
        assertTrue(violations_valid.isEmpty());
        when(productRepository.save(productWithPrice1)).thenReturn(productWithPrice1);
        Product savedProduct = productService.save(productWithPrice1);
        assertEquals(productWithPrice1, savedProduct);

        // T25
        // Valid case Price == 2
        Product productWithPrice2 = createProductSample(
            1L,
            "Valid Title",
            null,
            null,
            1,
            1,
            null,
            BigDecimal.valueOf(2),
            ProductStatus.IN_STOCK,
            null,
            Instant.now()
        );
        Set<ConstraintViolation<Product>> violations_valid2 = validator.validate(productWithPrice2);
        // Assert
        System.err.println(violations_valid2);
        assertTrue(violations_valid2.isEmpty());
        when(productRepository.save(productWithPrice2)).thenReturn(productWithPrice2);
        Product savedProduct2 = productService.save(productWithPrice2);
        assertEquals(productWithPrice2, savedProduct2);

        // T26
        // Valid case Price == 9998
        Product productWithPrice9998 = createProductSample(
            1L,
            "Valid Title",
            null,
            null,
            1,
            1,
            null,
            BigDecimal.valueOf(9998),
            ProductStatus.IN_STOCK,
            null,
            Instant.now()
        );
        Set<ConstraintViolation<Product>> violations_valid3 = validator.validate(productWithPrice9998);
        // Assert
        System.err.println(violations_valid3);
        assertTrue(violations_valid3.isEmpty());
        when(productRepository.save(productWithPrice9998)).thenReturn(productWithPrice9998);
        Product savedProduct3 = productService.save(productWithPrice9998);
        assertEquals(productWithPrice9998, savedProduct3);

        // T27
        // Valid case Price == 9999
        Product productWithPrice9999 = createProductSample(
            1L,
            "Valid Title",
            null,
            null,
            1,
            1,
            null,
            BigDecimal.valueOf(9999),
            ProductStatus.IN_STOCK,
            null,
            Instant.now()
        );
        Set<ConstraintViolation<Product>> violations_valid4 = validator.validate(productWithPrice9999);
        // Assert
        System.err.println(violations_valid4);
        assertTrue(violations_valid4.isEmpty());
        when(productRepository.save(productWithPrice9999)).thenReturn(productWithPrice9999);
        Product savedProduct4 = productService.save(productWithPrice9999);
        assertEquals(productWithPrice9999, savedProduct4);

        // T28
        // Invalid case Price == 0.99
        Product productWithPrice0_99 = createProductSample(
            1L,
            "Valid Title",
            null,
            null,
            1,
            1,
            null,
            BigDecimal.valueOf(0.99),
            ProductStatus.IN_STOCK,
            null,
            Instant.now()
        );
        Set<ConstraintViolation<Product>> violations_invalid = validator.validate(productWithPrice0_99);
        // Assert
        System.err.println(violations_invalid);
        assertEquals("price", violations_invalid.iterator().next().getPropertyPath().toString());

        // T29
        // Invalid case Price == 10000
        Product productWithPrice10000 = createProductSample(
            1L,
            "Valid Title",
            null,
            null,
            1,
            1,
            null,
            BigDecimal.valueOf(10000),
            ProductStatus.IN_STOCK,
            null,
            Instant.now()
        );
        Set<ConstraintViolation<Product>> violations_invalid2 = validator.validate(productWithPrice10000);
        // Assert
        System.err.println(violations_invalid2);
        assertEquals("price", violations_invalid2.iterator().next().getPropertyPath().toString());

        // T30
        // Invalid case Price == -2
        Product productWithPriceNegative = createProductSample(
            1L,
            "Valid Title",
            null,
            null,
            1,
            1,
            null,
            BigDecimal.valueOf(-2),
            ProductStatus.IN_STOCK,
            null,
            Instant.now()
        );
        Set<ConstraintViolation<Product>> violations_invalid3 = validator.validate(productWithPriceNegative);
        // Assert
        System.err.println(violations_invalid3);
        assertEquals("price", violations_invalid3.iterator().next().getPropertyPath().toString());

        // T31
        // Invalid case Price == string
        // This test case is not applicable in Java as price is of type BigDecimal

        // T32
        // Invalid case Price == null
        Product productWithPriceNull = createProductSample(
            1L,
            "Valid Title",
            null,
            null,
            1,
            1,
            null,
            null,
            ProductStatus.IN_STOCK,
            null,
            Instant.now()
        );
        Set<ConstraintViolation<Product>> violations_invalid4 = validator.validate(productWithPriceNull);
        // Assert
        System.err.println(violations_invalid4);
        assertEquals("price", violations_invalid4.iterator().next().getPropertyPath().toString());
    }

    @Test
    public void testQuantityInStock() {
        // T33
        // Valid case QuantityInStock == 0
        Product productWithZeroQuantity = createProductSample(
            1L,
            "Valid Title",
            null,
            null,
            1,
            0,
            null,
            BigDecimal.TEN,
            ProductStatus.IN_STOCK,
            null,
            Instant.now()
        );
        Set<ConstraintViolation<Product>> violations_valid = validator.validate(productWithZeroQuantity);
        // Assert
        System.err.println(violations_valid);
        assertTrue(violations_valid.isEmpty());
        when(productRepository.save(productWithZeroQuantity)).thenReturn(productWithZeroQuantity);
        Product savedProduct = productService.save(productWithZeroQuantity);
        assertEquals(productWithZeroQuantity, savedProduct);

        // T34
        // Valid case QuantityInStock == 1
        Product productWithOneQuantity = createProductSample(
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
        Set<ConstraintViolation<Product>> violations_valid2 = validator.validate(productWithOneQuantity);
        // Assert
        System.err.println(violations_valid2);
        assertTrue(violations_valid2.isEmpty());
        when(productRepository.save(productWithOneQuantity)).thenReturn(productWithOneQuantity);
        Product savedProduct2 = productService.save(productWithOneQuantity);
        assertEquals(productWithOneQuantity, savedProduct2);

        // T35
        // Valid case QuantityInStock == 300
        Product productWith300Quantity = createProductSample(
            1L,
            "Valid Title",
            null,
            null,
            1,
            300,
            null,
            BigDecimal.TEN,
            ProductStatus.IN_STOCK,
            null,
            Instant.now()
        );
        Set<ConstraintViolation<Product>> violations_valid3 = validator.validate(productWith300Quantity);
        // Assert
        System.err.println(violations_valid3);
        assertTrue(violations_valid3.isEmpty());
        when(productRepository.save(productWith300Quantity)).thenReturn(productWith300Quantity);
        Product savedProduct3 = productService.save(productWith300Quantity);
        assertEquals(productWith300Quantity, savedProduct3);

        // T36
        // Invalid case QuantityInStock == -1
        Product productWithNegativeQuantity = createProductSample(
            1L,
            "Valid Title",
            null,
            null,
            1,
            -1,
            null,
            BigDecimal.TEN,
            ProductStatus.IN_STOCK,
            null,
            Instant.now()
        );
        Set<ConstraintViolation<Product>> violations_invalid = validator.validate(productWithNegativeQuantity);
        // Assert
        System.err.println(violations_invalid);
        assertEquals("quantityInStock", violations_invalid.iterator().next().getPropertyPath().toString());

        // T37
        // Invalid case QuantityInStock == string
        // This test case is not applicable in Java as quantityInStock is of type Integer

        // T38
        // Invalid case QuantityInStock == null
        Product productWithNullQuantity = createProductSample(
            1L,
            "Valid Title",
            null,
            null,
            1,
            null,
            null,
            BigDecimal.TEN,
            ProductStatus.IN_STOCK,
            null,
            Instant.now()
        );
        Set<ConstraintViolation<Product>> violations_invalid2 = validator.validate(productWithNullQuantity);
        // Assert
        System.err.println(violations_invalid2);
        assertEquals("quantityInStock", violations_invalid2.iterator().next().getPropertyPath().toString());
    }

    @Test
    public void testProductStatus() {
        // T39
        // Valid case ProductStatus == IN_STOCK
        Product productInStock = createProductSample(
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
        Set<ConstraintViolation<Product>> violations_valid = validator.validate(productInStock);
        // Assert
        System.err.println(violations_valid);
        assertTrue(violations_valid.isEmpty());
        when(productRepository.save(productInStock)).thenReturn(productInStock);
        Product savedProduct = productService.save(productInStock);
        assertEquals(productInStock, savedProduct);

        // T40
        // Valid case ProductStatus == OUT_OF_STOCK
        Product productOutOfStock = createProductSample(
            1L,
            "Valid Title",
            null,
            null,
            1,
            1,
            null,
            BigDecimal.TEN,
            ProductStatus.OUT_OF_STOCK,
            null,
            Instant.now()
        );
        Set<ConstraintViolation<Product>> violations_valid2 = validator.validate(productOutOfStock);
        // Assert
        System.err.println(violations_valid2);
        assertTrue(violations_valid2.isEmpty());
        when(productRepository.save(productOutOfStock)).thenReturn(productOutOfStock);
        Product savedProduct2 = productService.save(productOutOfStock);
        assertEquals(productOutOfStock, savedProduct2);

        // T41
        // Valid case ProductStatus == PREORDER
        Product productPreorder = createProductSample(
            1L,
            "Valid Title",
            null,
            null,
            1,
            1,
            null,
            BigDecimal.TEN,
            ProductStatus.PREORDER,
            null,
            Instant.now()
        );
        Set<ConstraintViolation<Product>> violations_valid3 = validator.validate(productPreorder);
        // Assert
        System.err.println(violations_valid3);
        assertTrue(violations_valid3.isEmpty());
        when(productRepository.save(productPreorder)).thenReturn(productPreorder);
        Product savedProduct3 = productService.save(productPreorder);
        assertEquals(productPreorder, savedProduct3);

        // T42
        // Valid case ProductStatus == DISCONTINUED
        Product productDiscontinued = createProductSample(
            1L,
            "Valid Title",
            null,
            null,
            1,
            1,
            null,
            BigDecimal.TEN,
            ProductStatus.DISCONTINUED,
            null,
            Instant.now()
        );
        Set<ConstraintViolation<Product>> violations_valid4 = validator.validate(productDiscontinued);
        // Assert
        System.err.println(violations_valid4);
        assertTrue(violations_valid4.isEmpty());
        when(productRepository.save(productDiscontinued)).thenReturn(productDiscontinued);
        Product savedProduct4 = productService.save(productDiscontinued);
        assertEquals(productDiscontinued, savedProduct4);

        // T43
        // Invalid case ProductStatus == string
        // This test case is not applicable in Java as ProductStatus is of type Enum

        // T44
        // Invalid case ProductStatus == null
        Product productNull = createProductSample(1L, "Valid Title", null, null, 1, 1, null, BigDecimal.TEN, null, null, Instant.now());
        Set<ConstraintViolation<Product>> violations_invalid = validator.validate(productNull);
        // Assert
        System.err.println(violations_invalid);
        assertEquals("status", violations_invalid.iterator().next().getPropertyPath().toString());
    }

    @Test
    public void testWeight() {
        // T45
        // Valid case Weight == null
        Product productNullWeight = createProductSample(
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
        Set<ConstraintViolation<Product>> violations_valid = validator.validate(productNullWeight);
        // Assert
        System.err.println(violations_valid);
        assertTrue(violations_valid.isEmpty());
        when(productRepository.save(productNullWeight)).thenReturn(productNullWeight);
        Product savedProduct = productService.save(productNullWeight);
        assertEquals(productNullWeight, savedProduct);

        // T46
        // Valid case Weight == 0
        Product productZeroWeight = createProductSample(
            1L,
            "Valid Title",
            null,
            null,
            1,
            1,
            null,
            BigDecimal.TEN,
            ProductStatus.IN_STOCK,
            0.0,
            Instant.now()
        );
        Set<ConstraintViolation<Product>> violations_valid2 = validator.validate(productZeroWeight);
        // Assert
        System.err.println(violations_valid2);
        assertTrue(violations_valid2.isEmpty());
        when(productRepository.save(productZeroWeight)).thenReturn(productZeroWeight);
        Product savedProduct2 = productService.save(productZeroWeight);
        assertEquals(productZeroWeight, savedProduct2);

        // T47
        // Valid case Weight == 2
        Product productWeight2 = createProductSample(
            1L,
            "Valid Title",
            null,
            null,
            1,
            1,
            null,
            BigDecimal.TEN,
            ProductStatus.IN_STOCK,
            2.0,
            Instant.now()
        );

        Set<ConstraintViolation<Product>> violations_valid3 = validator.validate(productWeight2);
        // Assert
        System.err.println(violations_valid3);
        assertTrue(violations_valid3.isEmpty());
        when(productRepository.save(productWeight2)).thenReturn(productWeight2);
        Product savedProduct3 = productService.save(productWeight2);
        assertEquals(productWeight2, savedProduct3);

        // T48
        // Valid case Weight == 5.5
        Product productWeight5_5 = createProductSample(
            1L,
            "Valid Title",
            null,
            null,
            1,
            1,
            null,
            BigDecimal.TEN,
            ProductStatus.IN_STOCK,
            5.5,
            Instant.now()
        );
        Set<ConstraintViolation<Product>> violations_valid4 = validator.validate(productWeight5_5);
        // Assert
        System.err.println(violations_valid4);
        assertTrue(violations_valid4.isEmpty());
        when(productRepository.save(productWeight5_5)).thenReturn(productWeight5_5);
        Product savedProduct4 = productService.save(productWeight5_5);
        assertEquals(productWeight5_5, savedProduct4);

        // T49
        // Invalid case Weight == -0.1
        Product productNegativeWeight = createProductSample(
            1L,
            "Valid Title",
            null,
            null,
            1,
            1,
            null,
            BigDecimal.TEN,
            ProductStatus.IN_STOCK,
            -0.1,
            Instant.now()
        );
        Set<ConstraintViolation<Product>> violations_invalid = validator.validate(productNegativeWeight);
        // Assert
        System.err.println(violations_invalid);
        assertEquals("weight", violations_invalid.iterator().next().getPropertyPath().toString());
        // T50
        // Invalid case Weight == string
        // This test case is not applicable in Java as weight is of type Double
    }

    @Test
    public void testDimensions() {
        // T51
        // Valid case Dimensions == null
        Product productNullDimensions = createProductSample(
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
        Set<ConstraintViolation<Product>> violations_valid = validator.validate(productNullDimensions);
        // Assert
        System.err.println(violations_valid);
        assertTrue(violations_valid.isEmpty());
        when(productRepository.save(productNullDimensions)).thenReturn(productNullDimensions);
        Product savedProduct = productService.save(productNullDimensions);
        assertEquals(productNullDimensions, savedProduct);

        // T52
        // Valid case Dimensions == 0 char (empty)
        Product productEmptyDimensions = createProductSample(
            1L,
            "Valid Title",
            null,
            null,
            1,
            1,
            "",
            BigDecimal.TEN,
            ProductStatus.IN_STOCK,
            null,
            Instant.now()
        );
        Set<ConstraintViolation<Product>> violations_valid2 = validator.validate(productEmptyDimensions);
        // Assert
        System.err.println(violations_valid2);
        assertTrue(violations_valid2.isEmpty());
        when(productRepository.save(productEmptyDimensions)).thenReturn(productEmptyDimensions);
        Product savedProduct2 = productService.save(productEmptyDimensions);
        assertEquals(productEmptyDimensions, savedProduct2);

        // T53
        // Valid case Dimensions == valid string
        Product productValidDimensions = createProductSample(
            1L,
            "Valid Title",
            null,
            null,
            1,
            1,
            "100cm, 50cm, 70cm",
            BigDecimal.TEN,
            ProductStatus.IN_STOCK,
            null,
            Instant.now()
        );
        Set<ConstraintViolation<Product>> violations_valid3 = validator.validate(productValidDimensions);
        // Assert
        System.err.println(violations_valid3);
        assertTrue(violations_valid3.isEmpty());
        when(productRepository.save(productValidDimensions)).thenReturn(productValidDimensions);
        Product savedProduct3 = productService.save(productValidDimensions);
        assertEquals(productValidDimensions, savedProduct3);

        // T54
        // Invalid case Dimensions > 50 char
        Product productOver50CharDimensions = createProductSample(
            1L,
            "Valid Title",
            null,
            null,
            1,
            1,
            "a".repeat(51),
            BigDecimal.TEN,
            ProductStatus.IN_STOCK,
            null,
            Instant.now()
        );
        Set<ConstraintViolation<Product>> violations_invalid = validator.validate(productOver50CharDimensions);
        // Assert
        System.err.println(violations_invalid);
        assertEquals("dimensions", violations_invalid.iterator().next().getPropertyPath().toString());
    }
}
