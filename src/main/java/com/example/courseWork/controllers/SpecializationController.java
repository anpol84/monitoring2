package com.example.courseWork.controllers;

import com.example.courseWork.models.Institute;
import com.example.courseWork.models.Specialization;
import com.example.courseWork.services.InstituteService;
import com.example.courseWork.services.SpecializationService;
import com.example.courseWork.util.SpecializationValidator;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/specializations")
public class SpecializationController {
    private final SpecializationService specializationService;
    private final SpecializationValidator specializationValidator;
    private final InstituteService instituteService;

    @Autowired
    public SpecializationController(SpecializationService specializationService,
                                    SpecializationValidator specializationValidator, InstituteService instituteService) {
        this.specializationService = specializationService;
        this.specializationValidator = specializationValidator;
        this.instituteService = instituteService;
    }
    @GetMapping
    public String findAll(Model model){
        List<Specialization> specializations = specializationService.findAll();
        model.addAttribute("specializations", specializations);
        return "specializations/specializations";
    }

    @GetMapping("/{id}")
    public String findById(@PathVariable Integer id, Model model){
        model.addAttribute("specialization", specializationService.findById(id));
        return "specializations/specialization";
    }

    @GetMapping("/{id}/update")
    public String updatePage(@PathVariable Integer id, Model model){
        model.addAttribute("specialization", specializationService.findById(id));
        model.addAttribute("institutes", instituteService.findALl());
        return "specializations/update";
    }

    @PutMapping("/{id}")
    public String update(@ModelAttribute @Valid Specialization specialization,
                         BindingResult bindingResult, @PathVariable Integer id){
        specializationValidator.validate(specialization, bindingResult);
        if (bindingResult.hasErrors()){
            return "specializations/update";
        }
        specializationService.update(specialization, id);

        return "redirect:/specializations";
    }

    @GetMapping("/new")
    public String newCourse(Model model){
        model.addAttribute("specialization", new Specialization());
        model.addAttribute("institutes", instituteService.findALl());
        return "specializations/new";
    }

    @PostMapping
    public String create(@ModelAttribute("specialization") @Valid Specialization specialization, BindingResult bindingResult){
        specializationValidator.validate(specialization, bindingResult);
        if (bindingResult.hasErrors()){
            return "specializations/new";
        }
        specializationService.save(specialization);
        return "redirect:/specializations";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Integer id){
        specializationService.delete(id);
        return "redirect:/specializations";
    }
}
