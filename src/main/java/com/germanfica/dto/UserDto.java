package com.germanfica.dto;

import lombok.ToString;
import lombok.Value;

/**
 * Disclaimer: This class is for testing and data representation only. I do not recommend using it in production.
 * You can delete this class.
 */
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
