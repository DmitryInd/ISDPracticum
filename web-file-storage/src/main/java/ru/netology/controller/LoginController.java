package ru.netology.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.netology.filter.AuthenticationToken;

import java.util.Objects;

@Controller
@SessionAttributes("authenticationToken")
public class LoginController {
    @GetMapping("/")
    public String loginPage(@ModelAttribute("authenticationToken") AuthenticationToken token) {
        if (!token.isAnonymous()) {
            return "forward:/home";
        } else {
            return "login";
        }
    }
    @RequestMapping(value = "/home", method = { RequestMethod.GET, RequestMethod.POST })
    public ResponseEntity<String> homePage(
            @ModelAttribute("authenticationToken") AuthenticationToken token,
            @RequestParam(defaultValue = "false", required = false) String error) {
        if (Objects.equals(error, "true")) {
            return ResponseEntity.status(403).body("Invalid username or password.");
        } else {
            return ResponseEntity.ok().body(
                    String.format("Hi, %s! There you can upload and download files.",
                            token.getName())
            );
        }
    }

    @RequestMapping(value = "/accessDenied", method = { RequestMethod.GET, RequestMethod.POST })
    public ResponseEntity<String> accessDeniedPage() {
        return ResponseEntity.status(403).body("[403] Forbidden");
    }
}
