package com.example.darts.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {
    @GetMapping("/")
    public ModelAndView index() {
        return new ModelAndView("index");
    }

    @GetMapping("/about")
    public ModelAndView about() {
        return new ModelAndView("about");
    }

    @GetMapping("/contact")
    public ModelAndView contact() {
        return new ModelAndView("contact");
    }

    @GetMapping("/blog")
    public ModelAndView blog() {
        return new ModelAndView("blog");
    }

    @GetMapping("/elements")
    public ModelAndView elements() {
        return new ModelAndView("elements");
    }

    @GetMapping("/jobs")
    public ModelAndView jobs() {
        return new ModelAndView("job_listing");
    }

    @GetMapping("/jobs/details")
    public ModelAndView jobDetails() {
        return new ModelAndView("job_details");
    }

    @GetMapping("/blog/single")
    public ModelAndView singleBlog() {
        return new ModelAndView("single-blog");
    }
}
