package wetayo.wetayoapi.config.security.providers;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Component;
import wetayo.wetayoapi.config.properties.AuthProperties;
import wetayo.wetayoapi.config.security.ApiKeyAuthenticationToken;

@Component
public class ApiKeyProvider implements AuthenticationProvider {
    private final AuthProperties authProperties;

    public ApiKeyProvider(AuthProperties authProperties) {
        this.authProperties = authProperties;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        if(authentication.isAuthenticated()) return authentication;
        if(authentication.getCredentials().toString().equals(authProperties.getApiKey().get("user"))){
            return new ApiKeyAuthenticationToken(AuthorityUtils.createAuthorityList("user"),authentication.getCredentials().toString());
        }else if(authentication.getCredentials().toString().equals(authProperties.getApiKey().get("bus"))){
            return new ApiKeyAuthenticationToken(AuthorityUtils.createAuthorityList("bus"),authentication.getCredentials().toString());
        }
        else throw new AccessDeniedException("invalid api-key");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return ApiKeyAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
