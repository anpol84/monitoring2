package com.example.courseWork.controllers;

import com.example.courseWork.models.RetakeDTO;
import com.example.courseWork.models.Specialization;
import com.example.courseWork.models.UntreatedStudent;
import com.example.courseWork.services.RegistrationService;
import com.example.courseWork.services.SpecializationService;
import com.example.courseWork.services.UntreatedStudentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/untreatedStudents")
public class UntreatedStudentController {
    private final UntreatedStudentService untreatedStudentService;
    private final SpecializationService specializationService;

    @Autowired
    public UntreatedStudentController(UntreatedStudentService untreatedStudentService,
                                      SpecializationService specializationService) {
        this.untreatedStudentService = untreatedStudentService;
        this.specializationService = specializationService;

    }

    @GetMapping
    public String findAll(Model model){
        List<UntreatedStudent> untreatedStudents = untreatedStudentService.findAll();
        model.addAttribute("untreatedStudents", untreatedStudents);
        return "untreatedStudents/untreatedStudents";
    }

    @GetMapping("/{id}")
    public String findById(@PathVariable Integer id, Model model){
        Integer specialityId  = untreatedStudentService.findById(id).getUser().getSpecialization().getId();
        model.addAttribute("courses", specializationService.findCoursesBySpecializationId(specialityId));
        model.addAttribute("retake", new RetakeDTO());
        model.addAttribute("user", untreatedStudentService.findById(id));
        return "untreatedStudents/untreatedStudent";
    }


}
