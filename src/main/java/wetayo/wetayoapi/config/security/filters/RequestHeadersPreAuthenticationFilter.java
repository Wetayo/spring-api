package wetayo.wetayoapi.config.security.filters;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;

import javax.servlet.http.HttpServletRequest;

import static wetayo.wetayoapi.config.security.GraphQLSecurityConfig.USER_ID_PRE_AUTH_HEADER;


public class RequestHeadersPreAuthenticationFilter extends
        AbstractPreAuthenticatedProcessingFilter {

    @Override
    protected Object getPreAuthenticatedPrincipal(HttpServletRequest request) {
        return request.getHeader(USER_ID_PRE_AUTH_HEADER);
    }

    @Override
    protected Object getPreAuthenticatedCredentials(HttpServletRequest request) {
        return StringUtils.EMPTY;
    }

}