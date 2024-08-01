package vn.base.app.config.security;

import java.io.IOException;
import java.util.Collections;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.preauth.RequestHeaderAuthenticationFilter;
import org.springframework.stereotype.Component;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;

@Component
public class ApiKeyAuthFilter extends RequestHeaderAuthenticationFilter {

    private static final String API_KEY_HEADER = "X-API-Key";
    private static final String API_SECRET_HEADER = "X-API-Key";

    public ApiKeyAuthFilter() {
        super();
        this.setPrincipalRequestHeader(API_KEY_HEADER);
        this.setCredentialsRequestHeader(API_SECRET_HEADER);
        this.setExceptionIfHeaderMissing(false);
        this.setAuthenticationManager(new AuthenticationManager() {
            @Override
            public Authentication authenticate(Authentication authentication) throws AuthenticationException {
                System.out.println(authentication);
                String apiKeyName = (String) authentication.getPrincipal();
                System.out.println(apiKeyName);
                String apiKeySecret = (String) authentication.getCredentials();
                System.out.println(apiKeySecret);
                if ("valid-api-key".equals(apiKeyName) && "valid-api-key".equals(apiKeySecret)) {
                    return new ApiKeyAuthenticationToken(apiKeyName, apiKeySecret,
                            Collections.singletonList(new SimpleGrantedAuthority("ROLE_ADMIN")));
                } else {
                    return null;
                }
            }
        });
    }

    @Override
    public void setExceptionIfHeaderMissing(boolean exceptionIfHeaderMissing) {
        super.setExceptionIfHeaderMissing(exceptionIfHeaderMissing);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        super.doFilter(request, response, chain);
    }

}
