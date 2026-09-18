package com.heftyb.dms.users;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class UserDTOValidationTest {

    private static ValidatorFactory factory;
    private static Validator validator;

    @BeforeAll
    static void setUp() {
        factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @AfterAll
    static void tearDown() {
        factory.close();
    }

    @ParameterizedTest
    @ValueSource(strings = {"12345678", "correct-horse", "a-29-character-password-here!"})
    void acceptsPasswordsBetween8And30CharactersWithoutWhitespace(String password) {
        assertThat(passwordViolations(password)).isEmpty();
    }

    @ParameterizedTest
    @ValueSource(strings = {"short", "has a space", "this-password-is-longer-than-thirty-chars"})
    void rejectsPasswordsOutsideThePolicy(String password) {
        assertThat(passwordViolations(password)).isNotEmpty();
    }

    @Test
    void rejectsMissingPassword() {
        assertThat(passwordViolations(null)).isNotEmpty();
    }

    @Test
    void explainsWhichRuleFailed() {
        assertThat(passwordViolations("short"))
                .extracting(ConstraintViolation::getMessage)
                .anyMatch(message -> message.contains("8 or more characters"));
    }

    private Set<ConstraintViolation<UserDTO>> passwordViolations(String password) {
        UserDTO dto = new UserDTO();
        dto.setPassword(password);
        return validator.validateProperty(dto, "password");
    }
}
