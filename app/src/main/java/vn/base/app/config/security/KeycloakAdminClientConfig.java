package vn.base.app.config.security;

import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import vn.base.app.utils.CustomLogger;

@Configuration
public class KeycloakAdminClientConfig {

    @Value("${base.app.keycloak.url}")
    String KEYCLOAK_SERVER_URL;
    @Value("${base.app.keycloak.realm}")
    String KEYCLOAK_REALM;
    @Value("${base.app.keycloak.client-id}")
    String KEYCLOAK_CLIENT_ID;
    @Value("${base.app.keycloak.client-secret}")
    String KEYCLOAK_CLIENT_SECRET;
    
    @Bean
    Keycloak keycloak() {
        CustomLogger.info(KEYCLOAK_SERVER_URL);
        CustomLogger.info(KEYCLOAK_REALM);
        CustomLogger.info(KEYCLOAK_CLIENT_ID);
        CustomLogger.info(KEYCLOAK_CLIENT_SECRET);
        Keycloak keycloak = KeycloakBuilder.builder()
                .serverUrl(this.KEYCLOAK_SERVER_URL)
                .realm(this.KEYCLOAK_REALM)
                .grantType(OAuth2Constants.CLIENT_CREDENTIALS)
                .clientId(this.KEYCLOAK_CLIENT_ID)
                .clientSecret(this.KEYCLOAK_CLIENT_SECRET)
                .build();
        return keycloak;
    }
}
