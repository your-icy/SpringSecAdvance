package com.icycodes.springsecurity.event.listener;

import com.icycodes.springsecurity.entity.User;
import com.icycodes.springsecurity.event.RegistrationCompleteEvent;
import com.icycodes.springsecurity.service.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@Log4j2
public class RegistrationCompleteEventListener implements ApplicationListener<RegistrationCompleteEvent> {

    @Autowired
    private UserService userService;

    @Override
    public void onApplicationEvent(RegistrationCompleteEvent event) {

        User user = event.getUser();
        String token = UUID.randomUUID().toString();
        userService.saveVerificationTokenForUser(token, user);

        //sending email to user
        String url = event.getApplicationUrl() + "/verifyRegistration?token=" + token;
        log.info(" url to hit : {}" + url);

    }




}
