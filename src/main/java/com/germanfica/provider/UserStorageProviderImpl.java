package com.germanfica.provider;

import com.germanfica.entity.User;
import com.germanfica.repository.UserRepository;
import org.keycloak.component.ComponentModel;
import org.keycloak.credential.CredentialInput;
import org.keycloak.credential.CredentialInputValidator;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.models.UserModel;
import org.keycloak.models.credential.PasswordCredentialModel;
import org.keycloak.storage.StorageId;
import org.keycloak.storage.UserStorageProvider;
import org.keycloak.storage.adapter.AbstractUserAdapter;
import org.keycloak.storage.user.UserLookupProvider;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class UserStorageProviderImpl implements
        UserStorageProvider,
        UserLookupProvider,
        CredentialInputValidator {

    private UserRepository userRepository;
    KeycloakSession keycloakSession;
    ComponentModel componentModel;
    protected Map<String, UserModel> loadedUsers = new HashMap<>();

    public UserStorageProviderImpl(KeycloakSession session, ComponentModel model,
                                   UserRepository userRepository) {
        this.userRepository = userRepository;
        this.keycloakSession = session;
        this.componentModel = model;
    }

    @Override
    public void close() {

    }


    @Override
    public UserModel getUserById(String id, RealmModel realm) {
        StorageId storageId = new StorageId(id);
        String username = storageId.getExternalId();
        return getUserByUsername(username, realm);
    }

    @Override
    public UserModel getUserByUsername(String username, RealmModel realm) {
        UserModel adapter = loadedUsers.get(username);
        if (adapter == null) {
            //FIXED: javax.persistence.NoResultException error
            // ERROR [com.germanfica.repository.UserRepositoryImpl] (default task-3) cannot commit transaction: javax.persistence.NoResultException: No entity found for query
            // at deployment.user-storage-spi.jar//com.germanfica.repository.UserRepositoryImpl.findByUsername(UserRepositoryImpl.java:326)
            // https://stackoverflow.com/a/35850043
            // the best way to verify nulls in UserRepository is with an Optional, so let's take advantage of that
            Optional<User> opt = userRepository.findByUsername(username);
            if (!opt.isEmpty()){
                adapter = createAdapter(realm, opt.get());
                loadedUsers.put(username, adapter);
            }
        }
        return adapter;
    }

    @Override
    public UserModel getUserByEmail(String email, RealmModel realm) {
        return null;
    }

    @Override
    public boolean supportsCredentialType(String credentialType) {
        return credentialType.equals(PasswordCredentialModel.TYPE);
    }

    @Override
    public boolean isConfiguredFor(RealmModel realm, UserModel user, String credentialType) {

        try {
            String password = userRepository.findByUsername(user.getUsername()).get().getPassword();
            return credentialType.equals(PasswordCredentialModel.TYPE) && password != null;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    @Override
    public boolean isValid(RealmModel realm, UserModel user, CredentialInput credentialInput) {
        if (!supportsCredentialType(credentialInput.getType())) return false;

        try {
            Optional<User> opt = userRepository.findByUsername(user.getUsername()); //FIXED: ERROR [stderr] (default task-2) java.util.NoSuchElementException: No value present
            String password = null; // password null by default
            if(opt.isEmpty()) return false; // (1) check if request is empty
            if(!opt.isEmpty()) password = opt.get().getPassword(); // get the password before checking
            if (password == null) return false; // (2) check if password is empty
            return password.equals(credentialInput.getChallengeResponse());
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private UserModel createAdapter(RealmModel realm, User user) {
        return new AbstractUserAdapter(keycloakSession, realm, componentModel) {
            @Override
            public String getUsername() {
                return user.getUsername();
            }
        };
    }
}
