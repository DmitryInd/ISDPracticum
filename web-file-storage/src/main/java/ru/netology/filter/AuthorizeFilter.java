package ru.netology.filter;

import org.springframework.core.annotation.Order;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Objects;

@Component("AuthorizeFilter")
@Order(2)
public class AuthorizeFilter extends GenericFilterBean {
    private final JdbcTemplate jdbcTemplate;
    private final String getUserPasswordSql = "SELECT password, enabled FROM users WHERE username='%s'";
    private final String getUserRoleSql = "SELECT role FROM user_roles WHERE username='%s'";

    public AuthorizeFilter(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void doFilter(ServletRequest request,
                         ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        final var servletRequest = ((HttpServletRequest) request);
        final var session = servletRequest.getSession();
        if (Objects.equals(servletRequest.getRequestURI(), "/authorize")) {
            String redirectPath;

            final String user = servletRequest.getParameter("user");
            final String password = servletRequest.getParameter("password");
            if (!Objects.equals(servletRequest.getMethod(), "POST") ||
                    Objects.isNull(user) || Objects.isNull(password)) {
                redirectPath = "/accessDenied";
            } else {
                redirectPath = authorization(session, user, password);
            }
            request.getRequestDispatcher(redirectPath)
                    .forward(request, response);
        }

        chain.doFilter(request, response);
    }

    private String authorization(HttpSession session, String user, String password) {
        AuthenticationToken token = (AuthenticationToken) session.getAttribute("authenticationToken");
        var userData = jdbcTemplate.queryForRowSet(
                String.format(getUserPasswordSql, user)
        );
        if (userData.first() &&
                userData.getBoolean("enabled") &&
                Objects.equals(userData.getString("password"), password)) {
            var userRole = jdbcTemplate.queryForObject(
                    String.format(getUserRoleSql, user),
                    String.class);
            session.setAttribute("authenticationToken",
                    new AuthenticationToken(user, userRole));
            return "/home";
        } else {
            return "/home?error=true";
        }
    }
}
