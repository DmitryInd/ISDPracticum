package ru.netology.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import javax.sql.DataSource;

@Configuration
public class WebConfig {
    @Bean
    public ViewResolver internalResourceViewResolver() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver(
                "/templates/view/",
                ".html"
        );
        viewResolver.setViewClass(JstlView.class);
        return viewResolver;
    }

//    @Bean
//    public DataSource dataSource() {
//        DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
//        driverManagerDataSource.setUrl("jdbc:mysql://mysql-server:3306/userbase");
//        driverManagerDataSource.setUsername("web");
//        driverManagerDataSource.setPassword("root");
//        return driverManagerDataSource;
//    }
}
