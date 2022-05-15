package ru.netology.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

@Controller
public class DetailedErrorController implements ErrorController {
    private static final String PATH = "/error";

    @RequestMapping(value = PATH)
    public ResponseEntity<String> handleError(HttpServletRequest request) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        Object error = request.getAttribute(RequestDispatcher.ERROR_EXCEPTION);
        String errorMessage = request.getAttribute(RequestDispatcher.ERROR_MESSAGE).toString();

        if (error != null) {
            errorMessage = errorMessage + "\nDetailed:\n" + error;
        }

        if (status != null) {
            int statusCode = Integer.parseInt(status.toString());
            return ResponseEntity.status(statusCode).body(
                    String.format("[HTTP status: %d] %s", statusCode, errorMessage));
        }

        return ResponseEntity.status(500).body(errorMessage);
    }

    public String getErrorPath() {
        return PATH;
    }
}