package com.icycodes.springsecurity.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PasswordModel {

    private String email;

    private String oldPassword;

    private String newPassword;

}
