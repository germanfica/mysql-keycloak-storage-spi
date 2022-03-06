package com.germanfica.dto;

import lombok.ToString;
import lombok.Value;
import org.keycloak.models.RoleContainerModel;
import org.keycloak.models.RoleModel;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

@ToString
@Value
public class RoleModelDto {
    Map<String, List<String>> attributes;
    Set<RoleModel> composites;
    Stream<RoleModel> compositesStream;
    RoleContainerModel container;
    String containerId;
    String description;
    String id;
    String name;
}
