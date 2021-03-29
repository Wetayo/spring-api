package wetayo.wetayoapi.config.security;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedGrantedAuthoritiesWebAuthenticationDetails;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static wetayo.wetayoapi.config.security.GraphQLSecurityConfig.USER_ROLES_PRE_AUTH_HEADER;

public class GrantedAuthoritiesAuthenticationDetailsSource implements
        AuthenticationDetailsSource<HttpServletRequest, PreAuthenticatedGrantedAuthoritiesWebAuthenticationDetails> {

    @Override
    public PreAuthenticatedGrantedAuthoritiesWebAuthenticationDetails buildDetails(
            HttpServletRequest request) {
        var userRoles = request.getHeader(USER_ROLES_PRE_AUTH_HEADER);
        var authorities = StringUtils.isBlank(userRoles) ? List.<GrantedAuthority>of() :
                getAuthorities(userRoles);
        return new PreAuthenticatedGrantedAuthoritiesWebAuthenticationDetails(request, authorities);
    }

    private List<GrantedAuthority> getAuthorities(String userRoles) {
        return Set.of(userRoles.split(","))
                .stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

}