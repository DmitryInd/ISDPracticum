package ru.netology.filter;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;
import ru.netology.entity.user.AuthenticationToken;
import ru.netology.repository.UserAuthorizationInfoRepository;
import ru.netology.repository.UserRoleRepository;

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
    private final UserAuthorizationInfoRepository authorizationInfoRepository;
    private final UserRoleRepository roleRepository;

    public AuthorizeFilter(UserAuthorizationInfoRepository authorizationInfoRepository, UserRoleRepository roleRepository) {
        this.authorizationInfoRepository = authorizationInfoRepository;
        this.roleRepository = roleRepository;
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
        } else {
            chain.doFilter(request, response);
        }
    }

    private String authorization(HttpSession session, String user, String password) {
        AuthenticationToken token = (AuthenticationToken) session.getAttribute("authenticationToken");
        var AuthorizationInfo = authorizationInfoRepository.findById(user);
        if (AuthorizationInfo.isPresent() &&
                AuthorizationInfo.get().getEnabled() &&
                Objects.equals(AuthorizationInfo.get().getPassword(), password)) {
            var userRole = roleRepository.findFirstByAuthorizationInfo(AuthorizationInfo.get());
            session.setAttribute("authenticationToken",
                    new AuthenticationToken(
                            AuthorizationInfo.get().getUsername(),
                            userRole.get().getRole()
                    )
            );
            return "/home";
        } else {
            return "/home?error=true";
        }
    }
}