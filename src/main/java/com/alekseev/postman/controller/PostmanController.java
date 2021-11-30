package com.alekseev.postman.controller;

import com.alekseev.postman.model.Postman;
import com.alekseev.postman.service.PostmanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/postmen")
public class PostmanController {

    private final PostmanService postmanService;

    @Autowired
    public PostmanController(PostmanService postmanService) {
        this.postmanService = postmanService;
    }

    @GetMapping
    public String getPostmen(Model model) {
        List<Postman> postmen = postmanService.getPostmen();
        model.addAttribute("postmen", postmen);
        model.addAttribute("postman", new Postman());

        return "addPostman";
    }

    @PostMapping
    public String createPostman(@ModelAttribute Postman postman) {
        postmanService.addPostman(postman);

        return "redirect:/postmen";
    }

}
