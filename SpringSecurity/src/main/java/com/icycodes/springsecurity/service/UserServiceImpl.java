package com.icycodes.springsecurity.service;

import com.icycodes.springsecurity.entity.PasswordResetToken;
import com.icycodes.springsecurity.entity.User;
import com.icycodes.springsecurity.entity.VerificationToken;
import com.icycodes.springsecurity.model.UserModel;
import com.icycodes.springsecurity.repository.PasswordResetTokenRepository;
import com.icycodes.springsecurity.repository.UserRepository;
import com.icycodes.springsecurity.repository.VerificationTokenRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Optional;
import java.util.UUID;

@Service
@Log4j2
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Autowired
    private VerificationTokenRepository verificationTokenRepository;



    @Autowired
    private PasswordResetTokenRepository passwordResetTokenRepository;



    @Override
    public User registerUser(UserModel userModel) {

        User user = User.builder()
                .firstName(userModel.getFirstName())
                .lastName(userModel.getLastName())
                .email(userModel.getEmail())
                .password(passwordEncoder.encode(userModel.getPassword()))
                .isDeleted(false)
                .enabled(false)
                .role("User")
                .build();
        user = userRepository.save(user);
        log.info("User saved in db");
        return user;
    }

    @Override
    public void saveVerificationTokenForUser(String token, User user) {

        VerificationToken verificationToken = new VerificationToken(user,token);
        verificationTokenRepository.save(verificationToken);

    }

    @Override
    public String validateVerificationToken(String token){
        VerificationToken verificationToken = verificationTokenRepository.findByToken(token);
        if(verificationToken == null){
            return "invalid";
        }
        User user = verificationToken.getUser();
        Calendar cal = Calendar.getInstance();
        if(verificationToken.getExpirationTime().getTime() - cal.getTime().getTime() <=0){
            verificationTokenRepository.delete(verificationToken);
            return "token is expired";
        }

        user.setEnabled(true);
        userRepository.save(user);
        return "valid";


    }

    @Override
    public VerificationToken generateNewVerificationToken(String oldToken) {

        VerificationToken verificationToken = verificationTokenRepository.findByToken(oldToken);
        verificationToken.setToken(UUID.randomUUID().toString());
        verificationTokenRepository.save(verificationToken);

        return verificationToken;
    }

    @Override
    public User findUserByEmail(String email) {

        User user = userRepository.findByEmail(email);
        if(user != null){
            return user;
        }
        return null;
    }

    @Override
    public void sendPasswordResetTokenToUser(String token, User user) {
        PasswordResetToken passwordResetToken = new PasswordResetToken(user,token);
        passwordResetTokenRepository.save(passwordResetToken);
    }

    @Override
    public String validatePasswordResetToken(String token) {
        PasswordResetToken passwordResetToken = passwordResetTokenRepository.findByToken(token);
        if(passwordResetToken == null){
            return "invalid";
        }
        User user = passwordResetToken.getUser();
        Calendar cal = Calendar.getInstance();
        if(passwordResetToken.getExpirationTime().getTime() - cal.getTime().getTime() <=0){
            passwordResetTokenRepository.delete(passwordResetToken);
            return "token is expired";
        }
        return "valid";
    }

    @Override
    public Optional<User> getUserByPasswordResetToken(String token) {

        return Optional.ofNullable(passwordResetTokenRepository.findByToken(token).getUser());
    }

    @Override
    public void saveNewPassword(User user, String newPassword) {

        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
        log.info("user password is saved");

    }

    @Override
    public boolean matchOldPassword(User user, String oldPassword) {
        return passwordEncoder.matches(oldPassword, user.getPassword());
    }


}
