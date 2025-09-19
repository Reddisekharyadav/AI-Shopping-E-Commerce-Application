package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class DemoController {
    @GetMapping("/")
    public RedirectView rootRedirect() {
        return new RedirectView("/home");
    }

    @GetMapping("/home")
    public String home() {
        // Serve the static home.html page
        return "forward:/AI-shopping/AI-shopping/mrs.html";
    }
}