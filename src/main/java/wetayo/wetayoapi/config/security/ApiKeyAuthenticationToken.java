package wetayo.wetayoapi.config.security;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

import static wetayo.wetayoapi.config.security.GraphQLSecurityConfig.USER_ID_PRE_AUTH_HEADER;

public class ApiKeyAuthenticationToken extends AbstractAuthenticationToken {
    private final String apiKey;
    private final Object principal = USER_ID_PRE_AUTH_HEADER;

    public ApiKeyAuthenticationToken(String apiKey) {
        super(null);
        this.apiKey = apiKey;
    }

    public ApiKeyAuthenticationToken(Collection<? extends GrantedAuthority> authorities, String apiKey) {
        super(authorities);
        this.apiKey = apiKey;
        setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return apiKey;
    }

    @Override
    public Object getPrincipal() {
        return principal;
    }
}
