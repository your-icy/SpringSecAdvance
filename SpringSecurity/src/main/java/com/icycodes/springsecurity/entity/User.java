package com.icycodes.springsecurity.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {

    @TableGenerator(
            name="userGen",
            table="USER_ID_GEN",
            pkColumnName="GEN_KEY",
            valueColumnName="GEN_VALUE",
            pkColumnValue="USER_ID",
            allocationSize=1)
    @Id
    @GeneratedValue(strategy=GenerationType.TABLE, generator="userGen")
    private Long id;

    private String firstName;

    private String lastName;

    private String email;


    @Column(length = 60)
    private String password;

    private String role;

    private Boolean enabled=false;

    private Boolean isDeleted=false;




}
