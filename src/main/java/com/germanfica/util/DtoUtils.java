package com.germanfica.util;

import com.germanfica.dto.AbstractUserAdapterDto;
import com.germanfica.dto.RoleModelDto;
import com.germanfica.dto.UserDto;
import com.germanfica.entity.User;
import org.keycloak.models.RoleModel;
import org.keycloak.storage.adapter.AbstractUserAdapter;
import org.keycloak.storage.adapter.AbstractUserAdapterFederatedStorage;

import java.util.HashSet;
import java.util.Set;

/**
 * Disclaimer: This class is for testing and data representation only. I do not recommend using it in production.
 * You can delete this class.
 */
public class DtoUtils {
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

    public static final AbstractUserAdapterDto convertToDto(AbstractUserAdapter abstractUserAdapter) {
        return new AbstractUserAdapterDto(
                abstractUserAdapter.getAttributes(),
                abstractUserAdapter.getCreatedTimestamp(),
                abstractUserAdapter.getEmail(),
                abstractUserAdapter.getFederationLink(),
                abstractUserAdapter.getFirstName(),
                abstractUserAdapter.getGroups(),
                abstractUserAdapter.getId(),
                abstractUserAdapter.getLastName(),
                abstractUserAdapter.getRealmRoleMappings(),
                abstractUserAdapter.getRequiredActions(),
                abstractUserAdapter.getRoleMappings(),
                abstractUserAdapter.getServiceAccountClientLink()
        );
    }

    public static final AbstractUserAdapterDto convertToDto(AbstractUserAdapterFederatedStorage abstractUserAdapter) {
        return new AbstractUserAdapterDto(
                abstractUserAdapter.getAttributes(),
                abstractUserAdapter.getCreatedTimestamp(),
                abstractUserAdapter.getEmail(),
                abstractUserAdapter.getFederationLink(),
                abstractUserAdapter.getFirstName(),
                abstractUserAdapter.getGroups(),
                abstractUserAdapter.getId(),
                abstractUserAdapter.getLastName(),
                abstractUserAdapter.getRealmRoleMappings(),
                abstractUserAdapter.getRequiredActions(),
                abstractUserAdapter.getRoleMappings(),
                abstractUserAdapter.getServiceAccountClientLink()
        );
    }

    public static final RoleModelDto convertToDto(RoleModel roleModel) {
        return new RoleModelDto(
                roleModel.getAttributes(),
                roleModel.getComposites(),
                roleModel.getCompositesStream(),
                roleModel.getContainer(),
                roleModel.getContainerId(),
                roleModel.getDescription(),
                roleModel.getId(),
                roleModel.getName()
        );
    }
}
