package main.najah.test;


import main.najah.code.Calculator;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import java.time.Duration;
import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestCalculator {

    private Calculator calculator;

    
    @BeforeAll
    static void setupAll() {
        System.out.println("setup complete - Before All Tests");
    }

    @AfterAll
    static void tearDownAll() {
        System.out.println("teardown complete - After All Tests");
    }

    @BeforeEach
    void setup() {
        System.out.println("Before Each Test: Creating Calculator instance");
        calculator = new Calculator();
    }

    @AfterEach
    void tearDown() {
        System.out.println("After Each Test: Test is done\n");
    }


    @Test
    @Order(1) 
    @DisplayName("Test Addition with Multiple Assertions")
    void testAdd() {
        assertAll(
            () -> assertEquals(10, calculator.add(5, 5)),
            () -> assertEquals(-5, calculator.add(-10, 5)),
            () -> assertEquals(0, calculator.add())
        );
    }

    @ParameterizedTest
    @CsvSource({
        "1, 2, 3",
        "0, 0, 0",
        "-1, 1, 0"
    })
    @Order(2)
    @DisplayName("Parameterized Test for Addition")
    void testAddParameterized(int a, int b, int expectedResult) {
        assertEquals(expectedResult, calculator.add(a, b));
    }

    @Test
    @Order(3)
    @DisplayName("Test Division - Valid and Invalid Inputs")
    void testDivide() {
        assertEquals(5, calculator.divide(10, 2));

        Exception exception = assertThrows(ArithmeticException.class, () -> {
            calculator.divide(10, 0);
        });
        assertEquals("Cannot divide by zero", exception.getMessage());
    }

    @Test
    @Order(4)
    @DisplayName("Test Factorial with Timeout")
    void testFactorial() {
        assertTimeout(Duration.ofSeconds(1), () -> {
            assertEquals(120, calculator.factorial(5)); 
            assertEquals(1, calculator.factorial(0));    
            
            assertThrows(IllegalArgumentException.class, () -> {
                calculator.factorial(-5);
            });
        });
    }

    @Test
    @Order(5)
    @DisplayName("Intentionally Failing Test")
    @Disabled("Disabled because it fails intentionally. To fix it, change the expected value to 2.")
    void testFailing() {
        assertEquals(3, calculator.add(1, 1), "1 + 1 should be 2");
    }
}