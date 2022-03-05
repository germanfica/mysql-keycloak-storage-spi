package com.germanfica.dto;

import lombok.ToString;
import lombok.Value;

@Value
@ToString
public class UserDto {
    private int id;

    private String username;

    private String firstName;

    private String lastName;

    private String email;

    private String password;
}
