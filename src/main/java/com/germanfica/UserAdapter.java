package com.germanfica;

import com.germanfica.entity.User;
import lombok.extern.jbosslog.JBossLog;
import org.keycloak.common.util.MultivaluedHashMap;
import org.keycloak.component.ComponentModel;
import org.keycloak.models.*;
import org.keycloak.storage.StorageId;
import org.keycloak.storage.adapter.AbstractUserAdapterFederatedStorage;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@JBossLog
public class UserAdapter extends AbstractUserAdapterFederatedStorage {
    // == fields ==
    private final User user;
    private final String keycloakId;

    // == constructors ==
    public UserAdapter(KeycloakSession session, RealmModel realm, ComponentModel storageProviderModel, User user) {
        super(session, realm, storageProviderModel);
        this.session = session;
        this.realm = realm;
        this.storageProviderModel = storageProviderModel;
        this.user = user;
        //this.keycloakId = StorageId.keycloakId(storageProviderModel, String.valueOf(user.getUsername()));
        this.keycloakId = StorageId.keycloakId(storageProviderModel, String.valueOf(user.getId()));
    }

    // == user-related methods ==
    @Override
    public String getId() {
        return keycloakId;
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public void setUsername(String username) {
        user.setUsername(username);
    }

    @Override
    public String getEmail() {
        return user.getEmail();
    }

    @Override
    public void setEmail(String email) {
        user.setEmail(email);
    }

    @Override
    public String getFirstName() {
        return user.getFirstName();
    }

    @Override
    public void setFirstName(String firstName) {
        user.setFirstName(firstName);
    }

    @Override
    public String getLastName() {
        return user.getLastName();
    }

    @Override
    public void setLastName(String lastName) {
        user.setLastName(lastName);
    }

    @Override
    public Map<String, List<String>> getAttributes() {
        log.error("getAttributes ;))");
        //MultivaluedHashMap<String, String> attributes = getFederatedStorage().getAttributes(realm, this.getId());
        MultivaluedHashMap<String, String> attributes = new MultivaluedHashMap<>();
        if (attributes == null) attributes = new MultivaluedHashMap<>();

        // add user info
        attributes.add(UserModel.FIRST_NAME, user.getFirstName());
        attributes.add(UserModel.LAST_NAME, user.getLastName());
        attributes.add(UserModel.EMAIL, user.getEmail());
        attributes.add(UserModel.USERNAME, getUsername());
        log.warn("ALL THE ATTRIBUTES: " + attributes);

        return attributes;
    }

    // == role-related methods ==
    /**
     * Gets role mappings from federated storage and automatically appends default roles.
     * Also calls getRoleMappingsInternal() method
     * to pull role mappings from provider.  Implementors can override that method
     *
     * @return
     */
    @Override
    public Set<RoleModel> getRoleMappings() {
        Set<RoleModel> set = new HashSet<>(getFederatedRoleMappings());
        if (appendDefaultRolesToRoleMappings()) set.addAll(realm.getDefaultRole().getCompositesStream().collect(Collectors.toSet()));

        RoleModel adminRole = session.roles().getRealmRole(realm, "realm-admin"); // it works
        //RoleModel teacherRole = session.roles().getRealmRole(realm, "realm-teacher"); // it works
        //RoleModel studentRole = session.roles().getRealmRole(realm, "realm-student"); // it works
        //log.warn("admin role: " + DtoUtils.convertToDto(adminRole));
        //log.warn("teacher role: " + DtoUtils.convertToDto(teacherRole));
        //log.warn("student role: " + DtoUtils.convertToDto(studentRole));

        set.add(adminRole); // add the admin realm role

        set.addAll(getRoleMappingsInternal());
        return set;
    }
}
