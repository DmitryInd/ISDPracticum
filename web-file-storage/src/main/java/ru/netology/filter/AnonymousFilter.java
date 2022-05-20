package ru.netology.filter;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

@Component("AnonymousFilter")
@Order(1)
public class AnonymousFilter extends GenericFilterBean {
    private static final List<String> availableURI = List.of("/", "/authorize", "/actuator/prometheus");

    @Override
    public void doFilter(ServletRequest request,
                         ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        final var session = ((HttpServletRequest) request).getSession();
        AuthenticationToken token = (AuthenticationToken) session.getAttribute("authenticationToken");
        if (Objects.isNull(token)) {
            token = AuthenticationToken.getAnonymousToken();
            session.setAttribute("authenticationToken", token);
        }
        String requestURI = ((HttpServletRequest) request).getRequestURI();
        if (token.isAnonymous() &&
                availableURI.stream().noneMatch(requestURI::matches)) {
            request.getRequestDispatcher("/accessDenied")
                    .forward(request, response);
        } else {
            chain.doFilter(request, response);
        }
    }
}
