package wetayo.wetayoapi.config.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import wetayo.wetayoapi.config.security.filters.ApiKeyAuthenticationFilter;

@Slf4j
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class GraphQLSecurityConfig extends WebSecurityConfigurerAdapter {
    public static final String USER_ID_PRE_AUTH_HEADER = "api-key";

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        log.info("Configuring spring security");
        http
                // api-key auth
                .addFilterBefore(new ApiKeyAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests()
                // All endpoints require authentication
                .antMatchers("/login").permitAll()
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
        web.ignoring().antMatchers("/actuator/health");
        web.ignoring().antMatchers("/playground");
        web.ignoring().antMatchers("/login");
    }
}