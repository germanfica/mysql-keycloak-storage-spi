package com.germanfica.dto;

import lombok.ToString;
import lombok.Value;
import org.keycloak.models.RoleContainerModel;
import org.keycloak.models.RoleModel;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

/**
 * Disclaimer: This class is for testing and data representation only. I do not recommend using it in production.
 * You can delete this class.
 */
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
