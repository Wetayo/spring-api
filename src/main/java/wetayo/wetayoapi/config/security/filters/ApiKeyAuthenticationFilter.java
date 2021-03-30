package wetayo.wetayoapi.config.security.filters;

import org.springframework.security.core.context.SecurityContextHolder;
import wetayo.wetayoapi.config.security.ApiKeyAuthenticationToken;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static wetayo.wetayoapi.config.security.GraphQLSecurityConfig.USER_ID_PRE_AUTH_HEADER;

public class ApiKeyAuthenticationFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if(request instanceof HttpServletRequest && response instanceof HttpServletResponse){
            String apiKey = getApiKey((HttpServletRequest) request);
            if(apiKey != null){
                ApiKeyAuthenticationToken apiToken = new ApiKeyAuthenticationToken(apiKey);
                SecurityContextHolder.getContext().setAuthentication(apiToken);
            }else {
                HttpServletResponse httpResponse = (HttpServletResponse) response;
                httpResponse.setStatus(403);
            }
            chain.doFilter(request, response);
        }
    }
    private String getApiKey(HttpServletRequest httpRequest) {
        return httpRequest.getHeader(USER_ID_PRE_AUTH_HEADER);
    }
}
