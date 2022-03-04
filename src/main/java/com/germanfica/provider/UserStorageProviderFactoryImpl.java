package com.germanfica.provider;

import com.germanfica.repository.UserRepositoryImpl;
import org.keycloak.component.ComponentModel;
import org.keycloak.models.KeycloakSession;
import org.keycloak.storage.UserStorageProviderFactory;

public class UserStorageProviderFactoryImpl implements UserStorageProviderFactory<UserStorageProviderImpl> {
    public static final String PROVIDER_NAME = "mysql-user-store";

    @Override
    public UserStorageProviderImpl create(KeycloakSession session, ComponentModel model) {
        return new UserStorageProviderImpl(session, model, new UserRepositoryImpl());
    }

    @Override
    public String getId() {
        return PROVIDER_NAME;
    }
}
