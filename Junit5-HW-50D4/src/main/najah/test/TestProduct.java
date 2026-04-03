package main.najah.test;

import main.najah.code.Product;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

@Execution(ExecutionMode.CONCURRENT)
public class TestProduct {

    private Product product;

    @BeforeEach
    void setup() {
        product = new Product("Laptop", 100.0);
    }

    @Test
    @DisplayName("Test Product Constructor - Valid and Invalid Price")
    void testConstructor() {
        assertAll(
            () -> assertEquals("Laptop", product.getName()),
            () -> assertEquals(100.0, product.getPrice()),
            () -> {
                Exception exception = assertThrows(IllegalArgumentException.class, () -> {
                    new Product("Phone", -50.0);
                });
                assertEquals("Price must be non-negative", exception.getMessage());
            }
        );
    }

    @ParameterizedTest
    @ValueSource(doubles = {0.0, 10.5, 25.0, 50.0})
    @DisplayName("Parameterized Test for Valid Discounts")
    void testApplyDiscountValid(double validDiscount) {
        product.applyDiscount(validDiscount);
        assertEquals(validDiscount, product.getDiscount());
    }

    @Test
    @DisplayName("Test Invalid Discounts")
    void testApplyDiscountInvalid() {
        assertAll(
            () -> assertThrows(IllegalArgumentException.class, () -> product.applyDiscount(-1.0)),
            () -> assertThrows(IllegalArgumentException.class, () -> product.applyDiscount(51.0))
        );
    }

    @Test
    @DisplayName("Test Final Price Calculation with Timeout")
    void testGetFinalPrice() {
        assertTimeout(Duration.ofMillis(500), () -> {
            assertEquals(100.0, product.getFinalPrice());

            product.applyDiscount(20.0);
            assertEquals(80.0, product.getFinalPrice());
            
            product.applyDiscount(50.0);
            assertEquals(50.0, product.getFinalPrice());
        });
    }
}