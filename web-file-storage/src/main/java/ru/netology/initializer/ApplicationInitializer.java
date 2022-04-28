package ru.netology.initializer;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.CompositeFilter;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletContext;


public class ApplicationInitializer implements WebApplicationInitializer {
    private static final String TMP_FOLDER = "/";
    private static final int MAX_UPLOAD_SIZE = 5 * 1024 * 1024;

    @Override
    public void onStartup(ServletContext servletContext) {
        final var context = new AnnotationConfigWebApplicationContext();
        context.scan("ru.netology");
        servletContext.addListener(new ContextLoaderListener(context));

        final var servletRegistration = servletContext.addServlet(
                "app",
                new DispatcherServlet(context)
        );

        MultipartConfigElement multipartConfigElement = new MultipartConfigElement(TMP_FOLDER,
                MAX_UPLOAD_SIZE, MAX_UPLOAD_SIZE * 2L, MAX_UPLOAD_SIZE / 2);

        servletRegistration.setMultipartConfig(multipartConfigElement);
        servletRegistration.setLoadOnStartup(1);
        servletRegistration.addMapping("/*");

        final var anonymousFilterRegistration = servletContext.addFilter(
                "anonymousFilter",
                new DelegatingFilterProxy("AnonymousFilter")
        );
        anonymousFilterRegistration.addMappingForUrlPatterns(null, true, "/*");
        final var authorizeFilterRegistration = servletContext.addFilter(
                "authorizeFilter",
                new DelegatingFilterProxy("AuthorizeFilter")
        );
        authorizeFilterRegistration.addMappingForUrlPatterns(null, true, "/*");
    }
}
