package ru.netology.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@Controller
public class LoginController {
    @GetMapping("/")
    public String loginPage() {
        final var authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            return "/home";
        } else {
            return "/resources/login.html";
        }
    }

    @RequestMapping(value = "/home", method = { RequestMethod.GET, RequestMethod.POST })
    public ResponseEntity<String> homePage(@RequestParam(defaultValue = "false", required = false) String error) {
        if (Objects.equals(error, "true")) {
            return ResponseEntity.status(403).body("Invalid username or password.");
        } else {
            final var authentication = SecurityContextHolder.getContext().getAuthentication();
            return ResponseEntity.ok().body(
                    String.format("Hi, %s! There you can upload and download files.",
                            authentication.getName())
            );
        }
    }

    @RequestMapping(value = "/accessDenied", method = { RequestMethod.GET, RequestMethod.POST })
    public ResponseEntity<String> accessDeniedPage() {
        return ResponseEntity.status(403).body("[403] Forbidden");
    }
}
