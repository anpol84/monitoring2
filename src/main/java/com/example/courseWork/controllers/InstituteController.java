package com.example.courseWork.controllers;

import com.example.courseWork.models.Institute;
import com.example.courseWork.services.InstituteService;
import com.example.courseWork.util.InstituteValidator;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/institutes")
public class InstituteController {
    private final InstituteService instituteService;
    private final InstituteValidator instituteValidator;
    @Autowired
    public InstituteController(InstituteService instituteService, InstituteValidator instituteValidator) {
        this.instituteService = instituteService;
        this.instituteValidator = instituteValidator;
    }

    @GetMapping
    public String findAll(Model model){
        List<Institute> institutes = instituteService.findALl();
        model.addAttribute("institutes", institutes);
        return "institutes/institutes";
    }

    @GetMapping("/{id}")
    public String findById(@PathVariable Integer id, Model model){
        model.addAttribute("institute", instituteService.findById(id));
        return "institutes/institute";
    }

    @GetMapping("/{id}/update")
    public String updatePage(@PathVariable Integer id, Model model){
        model.addAttribute("institute", instituteService.findById(id));
        return "institutes/update";
    }

    @PutMapping("/{id}")
    public String update(@ModelAttribute @Valid Institute institute,
                         BindingResult bindingResult, @PathVariable Integer id){
        instituteValidator.validate(institute, bindingResult);
        if (bindingResult.hasErrors()){
            return "institutes/update";
        }
        instituteService.update(institute, id);
        return "redirect:/institutes";
    }

    @GetMapping("/new")
    public String newCourse(Model model){
        model.addAttribute("institute", new Institute());
        return "institutes/new";
    }

    @PostMapping
    public String create(@ModelAttribute("institute") @Valid Institute institute, BindingResult bindingResult){
        instituteValidator.validate(institute, bindingResult);
        if (bindingResult.hasErrors()){
            return "institutes/new";
        }
        instituteService.save(institute);
        return "redirect:/institutes";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Integer id){
        instituteService.delete(id);
        return "redirect:/institutes";
    }
}
