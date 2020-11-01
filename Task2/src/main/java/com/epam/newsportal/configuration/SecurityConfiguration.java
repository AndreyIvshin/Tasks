package com.epam.newsportal.configuration;

import com.epam.newsportal.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static com.epam.newsportal.model.enumeration.Authority.READ_NEWS;
import static com.epam.newsportal.model.enumeration.Authority.WRITE_NEWS;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserService userService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                    .antMatchers("/", "/static/**").permitAll()
                    .antMatchers(GET, "/sign/in", "/sign/up").anonymous()
                    .antMatchers(POST, "/sign/in/process", "/sign/up/process").anonymous()
                    .antMatchers(POST, "/sign/out/process").authenticated()
                    .antMatchers("/news/list", "/news/show/**").hasAuthority(READ_NEWS.getAuthority())
                    .antMatchers("/news/**").hasAuthority(WRITE_NEWS.getAuthority())
                .and()
                .formLogin()
                    .loginPage("/sign/in")
                    .loginProcessingUrl("/sign/in/process")
                    .defaultSuccessUrl("/")
                .and()
                .logout()
                    .logoutUrl("/sign/out/process")
                    .logoutSuccessUrl("/")
                .and()
                .exceptionHandling()
                    .accessDeniedPage("/denied");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(passwordEncoder());
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
