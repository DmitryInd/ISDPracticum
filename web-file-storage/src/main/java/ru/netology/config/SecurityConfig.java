package ru.netology.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.session.HttpSessionEventPublisher;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Bean(name = "dataSource")
    public DriverManagerDataSource dataSource() {
        DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
        driverManagerDataSource.setUrl("jdbc:mysql://mysql-server:3306/userbase");
        driverManagerDataSource.setUsername("web");
        driverManagerDataSource.setPassword("root");
        return driverManagerDataSource;
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication().dataSource(dataSource()).passwordEncoder(passwordEncoder())
                .usersByUsernameQuery(
                        "SELECT username, password, enabled FROM users WHERE username=?")
                .authoritiesByUsernameQuery(
                        "SELECT username, role FROM user_roles WHERE username=?");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.
                csrf().disable().
                sessionManagement().sessionFixation().migrateSession().
                and().
                exceptionHandling().accessDeniedPage("/accessDenied").
                and().
                formLogin().loginPage("/").loginProcessingUrl("/authorize").usernameParameter("user").
                successForwardUrl("/home").failureForwardUrl("/home?error=true").permitAll().
                and().
                logout().
                and().
                authorizeRequests().anyRequest().authenticated();
    }

    @Bean
    public HttpSessionEventPublisher httpSessionEventPublisher() {
        return new HttpSessionEventPublisher();
    }
}
