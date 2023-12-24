package com.example.courseWork.controllers;

import com.example.courseWork.models.Institute;
import com.example.courseWork.models.Retake;
import com.example.courseWork.models.RetakeDTO;
import com.example.courseWork.models.User;
import com.example.courseWork.services.RegistrationService;
import com.example.courseWork.services.RetakeService;
import com.example.courseWork.services.UntreatedStudentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@Controller
@RequestMapping("/retakes")
public class RetakeController {
    private final RetakeService retakeService;
    private final UntreatedStudentService untreatedStudentService;

    @Autowired
    public RetakeController(RetakeService retakeService, RegistrationService registrationService,
                            UntreatedStudentService untreatedStudentService) {
        this.retakeService = retakeService;
        this.untreatedStudentService = untreatedStudentService;

    }

    @PostMapping("/{id}")
    public String set(@ModelAttribute("retake") RetakeDTO retake, @PathVariable Integer id){
        retake.setUser(untreatedStudentService.findById(id).getUser());
        retake.setAttempts(new ArrayList<>());
        for (int i = 0; i < retake.getCourses().size(); i++){
            retake.getAttempts().add(3);
        }
        retakeService.set(retake);
        untreatedStudentService.delete(id);
        return "redirect:/untreatedStudents";
    }


}
