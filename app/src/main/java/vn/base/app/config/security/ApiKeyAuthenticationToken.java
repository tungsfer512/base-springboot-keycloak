package vn.base.app.config.security;

import java.util.Collection;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import vn.base.app.exception.CustomException;

public class ApiKeyAuthenticationToken extends AbstractAuthenticationToken {

    private final String apiKeyName;
    private final String apiKeySecret;

    public ApiKeyAuthenticationToken(String apiKeyName, String apiKeySecret) {
        super(null);
        this.apiKeyName = apiKeyName;
        this.apiKeySecret = apiKeySecret;
        setAuthenticated(false);
    }

    public ApiKeyAuthenticationToken(String apiKeyName, String apiKeySecret, Collection<GrantedAuthority> authorities) {
        super(authorities);
        this.apiKeyName = apiKeyName;
        this.apiKeySecret = apiKeySecret;
        super.setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return this.apiKeySecret;
    }

    @Override
    public Object getPrincipal() {
        return this.apiKeyName;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) {
        if (isAuthenticated) {
            throw new CustomException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Cannot set this token to trusted - use constructor which takes a GrantedAuthority list instead");
        }
        super.setAuthenticated(false);
    }

    @Override
    public void eraseCredentials() {
        super.eraseCredentials();
    }

}
