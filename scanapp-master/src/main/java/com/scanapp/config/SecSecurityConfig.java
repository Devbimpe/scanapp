package com.scanapp.config;
import com.scanapp.repositories.RoleRepository;
import com.scanapp.services.CustomUserDetailsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecSecurityConfig extends WebSecurityConfigurerAdapter {


    @Autowired
    private RoleRepository roleRepository;




    @Autowired
    CustomUserDetailsService userDetailsService;
    protected void init(AuthenticationManagerBuilder auth) throws Exception {
        System.out.println("I'm here");
        auth.authenticationProvider(authProvider());
    }


    public DaoAuthenticationProvider authProvider() {
        System.out.println("got here");
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(BCryptPasswordEncoder());
        return authProvider;
    }


    @Bean
    public PasswordEncoder BCryptPasswordEncoder()
    {

        return new BCryptPasswordEncoder();
   }







    @Override
        protected void configure(HttpSecurity http) throws Exception {

            http.authorizeRequests()
                    .antMatchers("/resources**").permitAll()
                    .anyRequest().permitAll()
                    .and().formLogin().loginPage("/login").defaultSuccessUrl("/index", true).failureUrl("/login?error").permitAll()
                    .and().logout().logoutSuccessUrl("/login").logoutUrl("/logout")
                    .and().sessionManagement().invalidSessionUrl("/login")
                    .and()
                    .csrf().disable();

        System.out.println("got here too");
        }


    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(BCryptPasswordEncoder());
    }

    }




