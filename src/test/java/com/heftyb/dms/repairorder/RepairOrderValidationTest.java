package com.heftyb.dms.repairorder;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;

class RepairOrderValidationTest {

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

    @Test
    void defaultPriorityIsValid() {
        assertThat(validator.validateProperty(new RepairOrder(), "priority")).isEmpty();
    }

    @ParameterizedTest
    @ValueSource(strings = {"0", "5", "9"})
    void singleDigitPriorityIsValid(String priority) {
        RepairOrder ro = new RepairOrder();
        ro.setPriority(priority);

        assertThat(validator.validateProperty(ro, "priority")).isEmpty();
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "a", "10", "-1"})
    void anythingElseIsRejected(String priority) {
        RepairOrder ro = new RepairOrder();
        ro.setPriority(priority);

        assertThat(validator.validateProperty(ro, "priority")).isNotEmpty();
    }
}
