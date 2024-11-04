package com.scm.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.scm.service.SecurityCustomUserDetailService;

@Configuration
public class SecurityConfig {

    @Autowired
    private SecurityCustomUserDetailService userDetailsService;

    @Autowired
    private OAuthAuthenticationSuccessHandler successHandler;

    // InMemory Authentication
    /*
     * @Bean
     * public UserDetailsService userDetailsService() {
     * UserDetails userDetails = User.builder()
     * .username("userName")
     * .password(passwordEncoder().encode("password"))
     * .build();
     * InMemoryUserDetailsManager userDetailsManager = new
     * InMemoryUserDetailsManager(userDetails);
     * return userDetailsManager;
     * }
     */

    // Database Authentication
    // Configuration of authentication provider
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }

    // URL configuration
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .authorizeHttpRequests(http -> {
                    http.requestMatchers("/user/**").authenticated();
                    http.requestMatchers("/api/**").authenticated();
                    http.anyRequest().permitAll();
                })
                // default form login
                // .formLogin(Customizer.withDefaults())
                // Custom form login
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(form -> {
                    form.loginPage("/login");
                    form.loginProcessingUrl("/authenticate");
                    form.usernameParameter("email");
                    form.passwordParameter("password");
                    form.successForwardUrl("/user/dashboard");
                    form.defaultSuccessUrl("/user/dashboard");
                    // form.failureForwardUrl("/login?error=true");
                    // form.defaultFailureUrl("/login-error");
                })
                .logout(form -> {
                    form.logoutUrl("/logout");
                    form.logoutSuccessUrl("/login?logout=true");
                    // form.invalidateHttpSession(true);
                    // form.clearAuthentication(true);
                    // form.permitAll();
                })
                // default oauth2
                // .oauth2Login(Customizer.withDefaults())
                // custom oauth2
                .oauth2Login(oauth -> {
                    oauth.loginPage("/login");
                    oauth.defaultSuccessUrl("/user/dashboard");
                    // handle successful login
                    oauth.successHandler(successHandler);
                    // handle failure
                    oauth.failureHandler(null);
                })
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}