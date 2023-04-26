package com.example.readingisgood.config;

import com.example.readingisgood.service.JwtUserDetailsService;
import com.example.readingisgood.util.JwtAuthenticationEntryPoint;
import com.example.readingisgood.util.JwtRequestFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableGlobalMethodSecurity(
        prePostEnabled = true)
public class WebSecurityConfig {

    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final  JwtRequestFilter jwtRequestFilter;
    private final JwtUserDetailsService jwtUserDetailsService;

    public WebSecurityConfig(final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint,
                             final JwtRequestFilter jwtRequestFilter,
                             final JwtUserDetailsService jwtUserDetailsService) {
        this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
        this.jwtRequestFilter = jwtRequestFilter;
        this.jwtUserDetailsService = jwtUserDetailsService;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

        authProvider.setUserDetailsService(jwtUserDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }


    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        // We don't need CSRF for this example
        httpSecurity.headers().frameOptions().disable();
        httpSecurity.csrf().disable()
                .authorizeRequests().antMatchers("/api/v1/books").hasRole("ADMIN")
                .and()
                .authorizeRequests().antMatchers(HttpMethod.GET,"/api/v1/customers/**","/api/v1/statistics/**").hasRole("USER")
                .and()
                .authorizeRequests().antMatchers(HttpMethod.POST,"/api/v1/orders").hasRole("USER")
                .and()
                // dont authenticate this particular request
                .authorizeRequests().antMatchers("/v2/api-docs",
                        "/configuration/ui",
                        "/swagger-resources/**",
                        "/configuration/security",
                        "/swagger-ui.html",
                        "/webjars/**").permitAll()
                .and()
                .authorizeRequests().antMatchers("/h2-console/**").permitAll()
                .and()
                .authorizeRequests().antMatchers("/api/v1/customers/register").permitAll()
                .and()
                .authorizeRequests().antMatchers("/authenticate").permitAll().
                        anyRequest().authenticated().and().
                        exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint).and().sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        return httpSecurity.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class).build();
    }
}
