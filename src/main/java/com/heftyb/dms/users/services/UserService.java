package com.heftyb.dms.users.services;

import com.heftyb.dms.users.User;
import com.heftyb.dms.users.UserDTO;
import com.heftyb.dms.validation.PasswordResetToken;
import com.heftyb.dms.validation.VerificationToken;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> findAll();

    User registerNewUserAccount(UserDTO userDTO);

    User getUser(String verificationToken);

    User saveRegisteredUser(User user);

    void deleteUser(User user);

    void createVerificationTokenForUser(User user, String token);

    VerificationToken getVerificationToken(String verificationToken);

    VerificationToken generateNewVerificationToken(String token);

    void createPasswordResetTokenForUser(User user, String token);

    User findUserByUsername(String username);

    User findUserByEmail(String email);

    List<User> findUsersByEmail(String email);

    PasswordResetToken getPasswordResetToken(String token);

    Optional<User> getUserByPasswordResetToken(String token);

    Optional<User> getUserByID(long id);

    void changeUserPassword(User user, String password);

    boolean checkIfValidOldPassword(User user, String password);

    String validateVerificationToken(String token);
}
