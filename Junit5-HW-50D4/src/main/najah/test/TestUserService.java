package main.najah.test;

import main.najah.code.UserService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

public class TestUserService {

    private UserService userService;

    @BeforeEach
    void setup() {
        userService = new UserService();
    }

    @ParameterizedTest
    @CsvSource({
            "student@najah.edu, true",
            "test@example.com, true",
            "invalidemail.com, false",
            "missingdot@com, false",
            "missingat.najah.edu, false"
    })
    @DisplayName("Parameterized Test for Email Validation (Valid/Invalid)")
    void testIsValidEmail(String email, boolean expected) {
        assertEquals(expected, userService.isValidEmail(email));
    }

    @Test
    @DisplayName("Test Email Validation with Null")
    void testIsValidEmailNull() {
        assertFalse(userService.isValidEmail(null));
    }

    @Test
    @DisplayName("Test Authentication - Valid and Invalid Credentials with Timeout")
    void testAuthenticate() {
        assertTimeout(Duration.ofSeconds(1), () -> {
            assertAll(
                () -> assertTrue(userService.authenticate("admin", "1234")),
                () -> assertFalse(userService.authenticate("user", "1234")),
                () -> assertFalse(userService.authenticate("admin", "0000")),
                () -> assertFalse(userService.authenticate(null, null))
            );
        });
    }
}