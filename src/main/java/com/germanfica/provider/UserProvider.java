package com.germanfica.provider;

import com.germanfica.entity.User;
import com.germanfica.repository.UserRepository;
import com.germanfica.util.DtoUtil;
import lombok.extern.jbosslog.JBossLog;
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

//FIXED: Rename provider as the documentation expected
// Provider factory classes must specify the concrete provider class
// as a template parameter when implementing the UserStorageProviderFactory.
// This is a must as the runtime will introspect this class to scan
// for its capabilities (the other interfaces it implements).
// So for example, if your provider class is named FileProvider,
// then the factory class should look like this: FileProviderFactory.
// So what is a template argument?
// Basically, when the compiler tries to find a template to match
// the template parameter, it only considers if the name are the same.
// Well that's what I'm thinking, is not much clear here in the docs:
// https://www.keycloak.org/docs/latest/server_development/index.html#provider-interfaces
@JBossLog
public class UserProvider implements
        UserStorageProvider,
        UserLookupProvider,
        CredentialInputValidator {
    // == fields ==
    private KeycloakSession keycloakSession;
    private ComponentModel componentModel;
    private UserRepository userRepository;
    protected Map<String, UserModel> loadedUsers = new HashMap<>();

    // == constructors ==
    public UserProvider(KeycloakSession session, ComponentModel model,
                        UserRepository userRepository) {
        this.userRepository = userRepository;
        this.keycloakSession = session;
        this.componentModel = model;
    }

    // == methods ==
    @Override
    public boolean supportsCredentialType(String credentialType) {
        return credentialType.equals(PasswordCredentialModel.TYPE);
    }

    @Override
    public boolean isConfiguredFor(RealmModel realm, UserModel user, String credentialType) {

        try {
            //FIXED: use opt.isEmpty() here in case of a null password!!!
            Optional<User> opt = userRepository.findByUsername(user.getUsername());
            String password = null;
            if(!opt.isEmpty()) password = opt.get().getPassword();
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

    @Override
    public void close() {

    }

    @Override
    public UserModel getUserById(RealmModel realm, String id) {
        log.warn("Esta llamando al getUserById &&&&&&&&&&&&&&&&&&&&&");
        log.warn("id: " + id);
        log.warn("realm: " + realm);
        StorageId storageId = new StorageId(id);
        String username = storageId.getExternalId();


        log.error("storageId: " + storageId);
        log.error("username: " + username);

        return getUserByUsername(username, realm); //FIXME: Fix the get user by id. The user id is not the username. THIS IS PRETTY BAD
    }

    @Override
    @Deprecated
    public UserModel getUserById(String id, RealmModel realm) {
        log.warn("Esta llamando al getUserById @Deprecated!!!!");
        log.warn("id: " + id);
        log.warn("realm: " + realm);
        return this.getUserById(realm, id);
    }

    @Override
    public UserModel getUserByUsername(RealmModel realm, String username) {
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
    @Deprecated
    public UserModel getUserByUsername(String username, RealmModel realm) {
        log.warn("Esta llamando al getUserByUsername @Deprecated!!!!");
        log.warn("username: " + username);
        log.warn("realm: " + realm);
        return getUserByUsername(realm,username);
    }

    @Override
    @Deprecated
    public UserModel getUserByEmail(String email, RealmModel realm) {
        log.warn("Esta llamando al getUserByEmail @Deprecated!!!!");
        log.warn("username: " + email);
        log.warn("realm: " + realm);
        return null;
    }

    private UserModel createAdapter(RealmModel realm, User user) {
        log.warn("Creando adapter...");
        log.warn("realm: " + realm);
        log.warn("user: " + DtoUtil.convertToDto(user));

        AbstractUserAdapter adpt = new AbstractUserAdapter(keycloakSession, realm, componentModel) {
            @Override
            public String getUsername() {
                return user.getUsername();
            }

            @Override
            public String getEmail() {
                return user.getEmail();
            }

            @Override
            public String getFirstName() {
                return user.getFirstName();
            }

            @Override
            public String getLastName() {
                return user.getLastName();
            }
        };

        log.warn("# Adaptador creado: " + DtoUtil.convertToDto(adpt));

        return adpt;
    }
}
