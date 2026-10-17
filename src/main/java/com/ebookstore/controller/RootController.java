package com.ebookstore.controller;

import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Redirects the browser root URL (http://localhost:8080/) to Swagger UI,
 * eliminating the NoResourceFoundException that appears when opening the base URL.
 */
@Hidden
@Controller
public class RootController {

    @GetMapping("/")
    public String root() {
        return "redirect:/swagger-ui.html";
    }
}
