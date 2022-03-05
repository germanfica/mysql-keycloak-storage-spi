package com.germanfica.provider;

import com.germanfica.repository.UserRepositoryImpl;
import org.keycloak.component.ComponentModel;
import org.keycloak.models.KeycloakSession;
import org.keycloak.storage.UserStorageProviderFactory;

public class UserProviderFactory implements UserStorageProviderFactory<UserProvider> {
    public static final String PROVIDER_NAME = "user-provider";

    @Override
    public UserProvider create(KeycloakSession session, ComponentModel model) {
        return new UserProvider(session, model, new UserRepositoryImpl());
    }

    @Override
    public String getId() {
        return PROVIDER_NAME;
    }
}
