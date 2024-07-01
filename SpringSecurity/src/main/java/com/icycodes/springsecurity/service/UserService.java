package com.icycodes.springsecurity.service;

import com.icycodes.springsecurity.entity.User;
import com.icycodes.springsecurity.entity.VerificationToken;
import com.icycodes.springsecurity.model.UserModel;

import java.util.Optional;

public interface UserService {
    User registerUser(UserModel userModel);

    void saveVerificationTokenForUser(String token, User user);

    String validateVerificationToken(String token);

    VerificationToken generateNewVerificationToken(String oldToken);

    User findUserByEmail(String email);

    void sendPasswordResetTokenToUser(String token, User user);

    String validatePasswordResetToken(String token);

    Optional<User> getUserByPasswordResetToken(String token);

    void saveNewPassword(User user, String newPassword);

    boolean matchOldPassword(User user ,String oldPassword);

}
