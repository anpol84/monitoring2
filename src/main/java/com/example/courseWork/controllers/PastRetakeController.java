package com.example.courseWork.controllers;

import com.example.courseWork.models.User;
import com.example.courseWork.security.PersonDetails;
import com.example.courseWork.services.PastRetakeService;
import com.example.courseWork.services.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class PastRetakeController {
    private final PastRetakeService pastRetakeService;
    private final RegistrationService registrationService;

    @Autowired
    public PastRetakeController(PastRetakeService pastRetakeService, RegistrationService registrationService) {
        this.pastRetakeService = pastRetakeService;
        this.registrationService = registrationService;
    }

    @GetMapping("/auth/pastRetakes/{id}")
    public String pastRetakes(@PathVariable("id") Integer id, Model model){
        if (!registrationService.findById(id).getRole().equals("ROLE_STUDENT")){
            return "auth/login";
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
        if (!personDetails.getUser().getRole().equals("ROLE_ADMIN")
                && personDetails.getUser().getId() != id) {
            return "auth/login";
        }
        User user = registrationService.findById(id);
        model.addAttribute("pastRetakes", pastRetakeService.findAllByUser(user));
        return "auth/pastRetakes";
    }
}
