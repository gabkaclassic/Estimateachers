package org.gab.estimateachers.app.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.firewall.HttpFirewall;
import org.springframework.security.web.firewall.StrictHttpFirewall;
import org.springframework.security.web.session.HttpSessionEventPublisher;

import java.util.Arrays;

@Slf4j
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {
    
    private static final int PASSWORD_STRENGTH = 8;
    
    @Autowired
    @Qualifier("userService")
    private UserDetailsService userService;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Bean("passwordEncoder")
    public PasswordEncoder getPasswordEncoder() {
        
        log.info("Created bean password encoder");
        
        return new BCryptPasswordEncoder(PASSWORD_STRENGTH);
    }
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.
        authorizeRequests()
                .antMatchers("/", "/users/registry", "/static/**", "/img/**", "/cards/list/**", "/confirm/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin().loginPage("/users/login").permitAll().defaultSuccessUrl("/users/online").failureUrl("/users/online")
                .and()
                .rememberMe()
                .and()
                .logout().logoutUrl("/users/logout").invalidateHttpSession(true).deleteCookies("JSESSIONID").permitAll()
                .and()
                .csrf().and().cors()
                .and()
                .sessionManagement().maximumSessions(3).maxSessionsPreventsLogin(true);
        
        log.info("Configured sessions, permissions, protocols");
    }
    
    @Bean
    public HttpFirewall allowSlashInUrl() {
    
        StrictHttpFirewall firewall = new StrictHttpFirewall();
        firewall.setAllowUrlEncodedSlash(true);
        firewall.setAllowBackSlash(true);
        firewall.setAllowUrlEncodedDoubleSlash(true);

        log.info("Created bean firewall");
        
        return firewall;
    }
    
    @Bean
    public static ServletListenerRegistrationBean<HttpSessionEventPublisher> httpSessionEventPublisher() {
        
        log.info("Created bean from http-session event publisher");
        
        return new ServletListenerRegistrationBean<>(new HttpSessionEventPublisher());
    }
    
    @Override
    public void configure(WebSecurity web) throws Exception {

        try{
            super.configure(web);
            web.httpFirewall(allowSlashInUrl());
        }
        catch (Exception exception) {
        
            log.warn(String.format("Failed web security configure. Exception: %s, reason: %s, stack trace: %s",
                    exception.getMessage(), exception.getCause(), Arrays.toString(exception.getStackTrace())));
        }
        log.info("Configured web security");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        
        try {
            auth
                    .userDetailsService(userService)
                    .passwordEncoder(passwordEncoder);
        }
        catch (Exception exception) {
            
            log.warn(String.format("Failed authentication manager configure. Exception: %s, reason: %s, stack trace: %s",
                    exception.getMessage(), exception.getCause(), Arrays.toString(exception.getStackTrace())));
        }
        
        log.info("Configured authentication manager");
    }
}