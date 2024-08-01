package vn.base.app.config.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;

import vn.base.app.utils.CustomLogger;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Value("${base.app.keycloak.client-id}")
    String KEYCLOAK_CLIENT_ID;

    @Bean
    @Order(1)
    SecurityFilterChain filterChainSwaggerAndPublic(HttpSecurity http) throws Exception {
        http
                .csrf(cfrs -> cfrs.disable())
                .cors(cors -> cors.disable())
                .sessionManagement((sessionManagement) -> sessionManagement
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .securityMatcher("/swagger**,/swagger-ui/**,/v3/api-docs/**,/api/public/**")
                .authorizeHttpRequests(authorizeRequests -> authorizeRequests.anyRequest().permitAll());
        return http.build();
    }

    @Bean
    @Order(2)
    SecurityFilterChain filterChainAuth(HttpSecurity http) throws Exception {
        http
                .csrf(cfrs -> cfrs.disable())
                .cors(cors -> cors.disable())
                .sessionManagement((sessionManagement) -> sessionManagement
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .securityMatcher("/api/internal/**")
                .authorizeHttpRequests(authorizeRequests -> authorizeRequests
                        .requestMatchers("/api/internal/test-auth").authenticated()
                        .requestMatchers("/api/internal/test-auth-user").hasAnyAuthority("ROLE_USER")
                        .requestMatchers("/api/internal/test-auth-admin").hasAnyAuthority("ROLE_ADMIN")
                        // API INTERNAL
                        .requestMatchers("/api/internal/v1/**").permitAll()
                        // Other
                        .anyRequest().authenticated())
                .addFilter(new ApiKeyAuthFilter())
                .oauth2ResourceServer((resourceServer) -> {
                    resourceServer.jwt((jwt) -> {
                        jwt.jwtAuthenticationConverter(jwtAuthenticationConverterForKeycloak());
                    });
                });
        return http.build();
    }

    @Bean
    JwtAuthenticationConverter jwtAuthenticationConverterForKeycloak() {
        Converter<Jwt, Collection<GrantedAuthority>> jwtGrantedAuthoritiesConverter = (jwt) -> {
            Map<String, Map<String, List<String>>> resourceAccess = jwt.getClaim("resource_access");
            CustomLogger.info(resourceAccess);
            Map<String, List<String>> client = resourceAccess.get(this.KEYCLOAK_CLIENT_ID);
            CustomLogger.info(client);
            List<String> clientRoles = new ArrayList<>(client.getOrDefault("roles", new ArrayList<>()));
            CustomLogger.info(clientRoles);
            return clientRoles.stream()
                    .map(role -> new SimpleGrantedAuthority("ROLE_" + role.toUpperCase().replace(" ", "_")))
                    .collect(Collectors.toList());
        };
        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter);
        return jwtAuthenticationConverter;
    }

}