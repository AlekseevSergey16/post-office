package com.alekseev.postman.controller.page;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PublisherPageController {

    @GetMapping("/publishers-page")
    public String getPublishersPage() {
        return "publisher-page";
    }

    @GetMapping("/publications-page")
    public String getPublicationsPage() {
        return "publications-page";
    }

}
