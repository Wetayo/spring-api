package wetayo.wetayoapi.config.security;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.Transient;

import java.util.Collection;

@Transient
public class ApiKeyAuthenticationToken extends AbstractAuthenticationToken {
    private String apiKey;

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
    return null;
    }

    @Override
    public Object getPrincipal() {
        return apiKey;
    }
}
