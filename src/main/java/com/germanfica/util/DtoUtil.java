package com.germanfica.util;

import com.germanfica.dto.UserDto;
import com.germanfica.entity.User;

import java.util.HashSet;
import java.util.Set;

public class DtoUtil {
    public static final Set<UserDto> convertToDto(Iterable<User> users) {
        Set<UserDto> usersDto = new HashSet<>();

        users.forEach(user -> usersDto.add(convertToDto(user)));

        return usersDto;
    }

    public static final UserDto convertToDto(User user) {
        return new UserDto(
                user.getId(),
                user.getUsername(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getPassword()
        );
    }
}
