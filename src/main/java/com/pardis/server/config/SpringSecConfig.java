package com.pardis.server.config;

import org.jasypt.springsecurity3.authentication.encoding.PasswordEncoder;
import org.jasypt.util.password.StrongPasswordEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

@Configuration
@EnableWebSecurity
@EnableAutoConfiguration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SpringSecConfig extends WebSecurityConfigurerAdapter {

    private AuthenticationProvider authenticationProvider;

    @Autowired
    @Qualifier("daoAuthenticationProvider")
    public void setAuthenticationProvider(AuthenticationProvider authenticationProvider) {
        this.authenticationProvider = authenticationProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder(StrongPasswordEncryptor passwordEncryptor){
        PasswordEncoder passwordEncoder = new PasswordEncoder();
        passwordEncoder.setPasswordEncryptor(passwordEncryptor);
        return passwordEncoder;
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(PasswordEncoder passwordEncoder,
                                                               UserDetailsService userDetailsService){

        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        return daoAuthenticationProvider;
    }

    @Autowired
    public void configureAuthManager(AuthenticationManagerBuilder authenticationManagerBuilder){
        authenticationManagerBuilder.authenticationProvider(authenticationProvider);
    }
    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
           httpSecurity
                .authorizeRequests()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login").permitAll()
                .and()
                .authorizeRequests()
                .antMatchers("/", "/login", "/logout").permitAll()
                .and()
                .authorizeRequests()
//                .antMatchers("/giveRole", "/console").hasAuthority("SUPERADMIN")
//                .antMatchers("/device", "/findOneDevice", "/saveRelation", "/saveDevice", "/post", "/id", "/delete", "/findOneDevice").hasAnyAuthority("USER", "ADMIN", "SUPERADMIN")
//                .antMatchers("/register", "/registerSuccessful", "/members").hasAnyAuthority("ADMIN", "SUPERADMIN")
                .antMatchers("/giveRole", "/console").hasRole("SUPERADMIN")
                .antMatchers("/device", "/findOneDevice", "/saveRelation", "/saveDevice", "/post", "/id", "/delete", "/findOneDevice").access("hasRole('USER') and hasRole('ADMIN') and hasRole('SUPERADMIN')")
                .antMatchers("/register", "/registerSuccessful", "/members").access("hasRole('ADMIN') and hasRole('SUPERADMIN')")
                .anyRequest().authenticated()
                .and()
                .logout()
                .logoutSuccessUrl("/login.html")
                .logoutUrl("/performing_logout")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .permitAll()
                .and()
                .rememberMe().key("uniqueAndSecret").tokenValiditySeconds(86400)
                ;

        httpSecurity.csrf().disable();
        
    }


}