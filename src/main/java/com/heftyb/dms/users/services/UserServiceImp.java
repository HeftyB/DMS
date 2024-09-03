package com.heftyb.dms.users.services;

import com.heftyb.dms.crm.services.EmployeeService;
import com.heftyb.dms.exceptions.DataNotFoundException;
import com.heftyb.dms.exceptions.UserAlreadyExistException;
import com.heftyb.dms.exceptions.UserNotFoundException;
import com.heftyb.dms.users.User;
import com.heftyb.dms.users.UserDTO;
import com.heftyb.dms.users.UserRole;
import com.heftyb.dms.users.repositories.UserRepository;
import com.heftyb.dms.validation.PasswordResetToken;
import com.heftyb.dms.validation.VerificationToken;
import com.heftyb.dms.validation.repositories.PasswordResetTokenRepository;
import com.heftyb.dms.validation.repositories.VerificationTokenRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.InvalidParameterException;
import java.util.*;

@Service(value = "userService")
@Transactional
public class UserServiceImp implements UserService{
    private final UserRepository userRepo;
    private final EmployeeService employeeService;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;
    private final VerificationTokenRepository verificationTokenRepo;
    private final PasswordResetTokenRepository passResetRepo;

    public static final String TOKEN_INVALID = "invalidToken";
    public static final String TOKEN_EXPIRED = "expired";
    public static final String TOKEN_VALID = "valid";

    public UserServiceImp(final UserRepository userRepo,
                          final EmployeeService employeeService,
                          final PasswordEncoder passwordEncoder,
                          final RoleService roleService,
                          final VerificationTokenRepository verificationTokenRepo,
                          final PasswordResetTokenRepository passResetRepo) {
        super();
        this.userRepo = userRepo;
        this.employeeService = employeeService;
        this.passwordEncoder = passwordEncoder;
        this.roleService = roleService;
        this.verificationTokenRepo = verificationTokenRepo;
        this.passResetRepo = passResetRepo;
    }


    private boolean emailExist(String email) {
        List<User> matchingEmails = userRepo.findByEmailContainingIgnoreCase(email);
        return !matchingEmails.isEmpty();
    }

    @Override
    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        userRepo.findAll().iterator().forEachRemaining(users::add);
        return users;
    }

    @Transactional
    @Override
    public User registerNewUserAccount(final UserDTO userDto) {
        if (emailExist(userDto.getEmail())) {
            throw new UserAlreadyExistException(String.format("Error: The email address %s is already in use with an existing account!", userDto.getEmail()));
        }
        User u = new User();
        u.setUsername(userDto.getUsername());
        u.setPassword(passwordEncoder.encode(userDto.getPassword()));
        u.setEmail(userDto.getEmail());
        u.setEmployee(employeeService.findById(userDto.getEmployee().getId()));
        u.setEnabled(true);
        u.getRoles().add(new UserRole(u, roleService.findByRole("USER")));
        return userRepo.save(u);
    }

    @Override
    public User getUser(String verificationToken) {
        VerificationToken token = verificationTokenRepo.findByToken(verificationToken)
                .orElseThrow(()-> new DataNotFoundException(String.format("", verificationToken)));
        return token.getUser();
    }

    @Transactional
    @Override
    public User saveRegisteredUser(User user) {
        User u = new User();
        u.setId(user.getId());
        u.setUsername(user.getUsername());
        u.setPassword(user.getPassword());
        u.setEnabled(user.isEnabled());
        u.setEmail(user.getEmail());
        u.setEmployee(employeeService.findById(user.getEmployee().getId()));
        u.setRoles(user.getRoles());
        return userRepo.save(u);
    }

    @Transactional
    @Override
    public void deleteUser(User user) {
        Optional<VerificationToken> token = verificationTokenRepo.findByUser(user);
        if (token.isPresent()) { verificationTokenRepo.delete(token.get()); }

        Optional<PasswordResetToken> passwordResetToken = passResetRepo.findByUser(user);
        if(passwordResetToken.isPresent()) { passResetRepo.delete(passwordResetToken.get()); }

        userRepo.deleteById(user.getId());
    }

    @Transactional
    @Override
    public void createVerificationTokenForUser(User user, String token) {
        verificationTokenRepo.save(new VerificationToken(token, user));
    }

    @Override
    public VerificationToken getVerificationToken(String verificationToken) {
        return verificationTokenRepo.findByToken(verificationToken).orElseThrow(
                ()-> new DataNotFoundException(String.format("Cannot find VerificationToken %s", verificationToken))
        );
    }

    @Transactional
    @Override
    public VerificationToken generateNewVerificationToken(String token) {
        VerificationToken newToken = verificationTokenRepo.findByToken(token).orElseThrow(
                ()-> new DataNotFoundException(String.format(
                        "VerificationToken %s can't be found!"
                ))
        );
        newToken.updateToken(UUID.randomUUID().toString());
        return verificationTokenRepo.save(newToken);
    }

    @Transactional
    @Override
    public void createPasswordResetTokenForUser(User user, String token) {
        PasswordResetToken passToken = new PasswordResetToken(token, user);
        passResetRepo.save(passToken);
    }

    @Override
    public User findUserByUsername(String username) {
        return userRepo.findByUsernameIgnoreCase(username)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("Error: username %s not found!", username)));
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepo.findByEmailIgnoreCase(email).orElseThrow(
                ()-> new DataNotFoundException(String.format(
                        "Can't find user by email %s", email
                ))
        );
    }

    @Override
    public List<User> findUsersByEmail(String email) {
        return userRepo.findByEmailContainingIgnoreCase(email);
    }

    @Override
    public PasswordResetToken getPasswordResetToken(String token) {
        return passResetRepo.findByToken(token).orElseThrow(
                ()-> new DataNotFoundException(String.format(
                        "Could not find PasswordResetToken %s", token
                ))
        );
    }

    @Override
    public Optional<User> getUserByPasswordResetToken(String token) {
        return Optional.ofNullable(passResetRepo.findByToken(token).get().getUser());
    }

    @Override
    public Optional<User> getUserByID(long id) {
        return userRepo.findById(id);
    }

    @Transactional
    @Override
    public void changeUserPassword(User user, String password) {
        User u = userRepo.findById(user.getId())
                .orElseThrow(() -> new UserNotFoundException(String.format(
                        "Could not find user %s", user.getId()
                )));
        u.setPassword(passwordEncoder.encode(password));
        userRepo.save(u);
    }

    @Override
    public boolean checkIfValidOldPassword(User user, String password) {
        return passwordEncoder.matches(user.getPassword(), password);
    }

    @Override
    public String validateVerificationToken(String token) {
        Optional<VerificationToken> token1 = verificationTokenRepo.findByToken(token);

        if(token1.isEmpty()) {
            return TOKEN_INVALID;
        }

        User u = token1.get().getUser();
        Calendar cal = Calendar.getInstance();

        if ((token1.get().getExpiryDate()
                .getTime() - cal.getTime()
        .getTime()) <= 0) {
            verificationTokenRepo.delete(token1.get());
            return TOKEN_EXPIRED;
        }

        u.setEnabled(true);
        userRepo.save(u);
        return TOKEN_VALID;
    }
}
