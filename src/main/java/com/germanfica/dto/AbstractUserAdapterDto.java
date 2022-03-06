package com.germanfica.dto;

import lombok.ToString;
import lombok.Value;
import org.keycloak.models.GroupModel;
import org.keycloak.models.RoleModel;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Disclaimer: This class is for testing and data representation only. I do not recommend using it in production.
 * You can delete this class.
 */
@ToString
@Value
public class AbstractUserAdapterDto {
    Map<String, List<String>> attributes;
    Long createdTimestamp;
    String email;
    String federationLink;
    String firstName;
    Set<GroupModel> groups;
    String id;
    String lastName;
    Set<RoleModel> realmRoleMappings;
    Set<String> requiredActions;
    Set<RoleModel> roleMappings;
    String serviceAccountClientLink;
}
