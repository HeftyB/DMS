package com.heftyb.dms.users.services;

import com.heftyb.dms.crm.Employee;
import com.heftyb.dms.crm.repositories.EmployeeRepository;
import com.heftyb.dms.exceptions.DataNotFoundException;
import com.heftyb.dms.exceptions.UserAlreadyExistException;
import com.heftyb.dms.users.Role;
import com.heftyb.dms.users.User;
import com.heftyb.dms.users.UserDTO;
import com.heftyb.dms.users.repositories.RoleRepository;
import com.heftyb.dms.users.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class UserServiceImpTest {

    private static final String PASSWORD = "correct-horse";

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private EmployeeRepository employeeRepo;

    @Autowired
    private RoleRepository roleRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void createUserRole() {
        roleRepo.save(new Role("USER"));
    }

    @Test
    void oldPasswordCheckAcceptsCurrentPassword() {
        User sam = existingUser("sam", "sam@example.com");

        assertThat(userService.checkIfValidOldPassword(sam, PASSWORD)).isTrue();
    }

    @Test
    void oldPasswordCheckRejectsWrongPassword() {
        User sam = existingUser("sam", "sam@example.com");

        assertThat(userService.checkIfValidOldPassword(sam, "wrong-horse")).isFalse();
    }

    @Test
    void registrationAllowsEmailContainedInAnExistingOne() {
        existingUser("sam", "sam@example.com");

        User am = userService.registerNewUserAccount(registration("am", "am@example.com"));

        assertThat(am.getEmail()).isEqualTo("am@example.com");
    }

    @Test
    void registrationRejectsDuplicateEmailIgnoringCase() {
        existingUser("sam", "sam@example.com");

        assertThatThrownBy(() -> userService.registerNewUserAccount(registration("sam2", "SAM@example.com")))
                .isInstanceOf(UserAlreadyExistException.class);
    }

    @Test
    void unknownPasswordResetTokenYieldsEmpty() {
        assertThat(userService.getUserByPasswordResetToken("no-such-token")).isEmpty();
    }

    @Test
    void knownPasswordResetTokenYieldsItsUser() {
        User sam = existingUser("sam", "sam@example.com");
        userService.createPasswordResetTokenForUser(sam, "reset-123");

        assertThat(userService.getUserByPasswordResetToken("reset-123"))
                .map(User::getId)
                .contains(sam.getId());
    }

    @Test
    void unknownVerificationTokenErrorNamesTheToken() {
        assertThatThrownBy(() -> userService.getUser("no-such-token"))
                .isInstanceOf(DataNotFoundException.class)
                .hasMessageContaining("no-such-token");
    }

    @Test
    void regeneratingUnknownVerificationTokenThrowsNotFound() {
        assertThatThrownBy(() -> userService.generateNewVerificationToken("no-such-token"))
                .isInstanceOf(DataNotFoundException.class)
                .hasMessageContaining("no-such-token");
    }

    private User existingUser(String username, String email) {
        return userRepo.save(new User(username, passwordEncoder.encode(PASSWORD), email, employee()));
    }

    private UserDTO registration(String username, String email) {
        return new UserDTO(username, PASSWORD, PASSWORD, email, employee());
    }

    private Employee employee() {
        Employee e = new Employee();
        e.setFirstName("Sam");
        e.setLastName("Ortiz");
        e.setPreferredName("Sam");
        return employeeRepo.save(e);
    }
}
