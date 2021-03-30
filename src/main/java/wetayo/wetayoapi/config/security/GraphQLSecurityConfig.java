package wetayo.wetayoapi.config.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import wetayo.wetayoapi.config.properties.AuthProperties;
import wetayo.wetayoapi.config.security.filters.ApiKeyAuthenticationFilter;
import wetayo.wetayoapi.config.security.filters.RequestHeadersPreAuthenticationFilter;

import javax.servlet.Filter;

@Slf4j
@Configuration
@EnableWebSecurity // Debug = true, will print the execution of the FilterChainProxy
@EnableGlobalMethodSecurity(
        securedEnabled = true,
        prePostEnabled = true)
@RequiredArgsConstructor
public class GraphQLSecurityConfig extends WebSecurityConfigurerAdapter {
    public static final String USER_ROLES_PRE_AUTH_HEADER = "roles";
    public static final String USER_ID_PRE_AUTH_HEADER = "api_key";

    //private final PreAuthenticatedAuthenticationProvider preAuthenticatedAuthenticationProvider;

    private final AuthProperties authProperties;



    @Override
    protected void configure(HttpSecurity http) throws Exception {
        log.info("Configuring spring security");

        http
                // api-key auth
                .addFilterBefore(new ApiKeyAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                // oAuth와 같은 인증과 같이 SecurityContextpersistencefilter전에 헤더 인증
                //.addFilterBefore(createRequestHeadersPreAuthenticationFilter(), AbstractPreAuthenticatedProcessingFilter.class)
                .authorizeRequests()
                // All endpoints require authentication
                .anyRequest().authenticated()
                .and()
                // Disable the session management filter
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                // Disable CSRF Token generation
                .csrf().disable()
                // Disable the default HTTP Basic-Auth
                .httpBasic().disable()
                // Disable the /logout filter
                .logout().disable()
                // Disable anonymous users
                .anonymous().disable();
    }

    @Override
    public void configure(WebSecurity web) {
        // Permit actuator health endpoint for uptime checks etc
        //web.ignoring().antMatchers("/actuator/health");
        web.ignoring().antMatchers("/playground");
    }

    private Filter createRequestHeadersPreAuthenticationFilter() throws Exception {
        var filter = new RequestHeadersPreAuthenticationFilter();
        filter.setAuthenticationDetailsSource(new GrantedAuthoritiesAuthenticationDetailsSource());
        filter.setAuthenticationManager(authenticationManager());
        filter.setContinueFilterChainOnUnsuccessfulAuthentication(false);
        return filter;
    }
}