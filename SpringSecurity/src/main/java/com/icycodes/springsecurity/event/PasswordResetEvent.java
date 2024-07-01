package com.icycodes.springsecurity.event;

import com.icycodes.springsecurity.entity.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;


@Getter
@Setter
public class PasswordResetEvent extends ApplicationEvent {

    private User user;
    private String applicationUrl;


    public PasswordResetEvent(User user, String applicationUrl) {
        super(user);
        this.user = user;
        this.applicationUrl = applicationUrl;

    }
}