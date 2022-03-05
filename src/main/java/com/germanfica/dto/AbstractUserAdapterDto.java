package com.germanfica.dto;

import lombok.ToString;
import lombok.Value;
import org.keycloak.models.GroupModel;
import org.keycloak.models.RoleModel;

import java.util.List;
import java.util.Map;
import java.util.Set;

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
