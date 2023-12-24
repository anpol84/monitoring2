package com.example.courseWork.controllers;

import com.example.courseWork.models.Course;
import com.example.courseWork.services.CourseService;
import com.example.courseWork.services.InstituteService;
import com.example.courseWork.services.SpecializationService;
import com.example.courseWork.util.CourseValidator;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/courses")
public class CourseController {

    private final CourseService courseService;
    private final InstituteService instituteService;
    private final SpecializationService specializationService;
    private final CourseValidator courseValidator;
    @Autowired
    public CourseController(CourseService courseService, InstituteService instituteService,
                            SpecializationService specializationService, CourseValidator courseValidator) {
        this.courseService = courseService;
        this.instituteService = instituteService;
        this.specializationService = specializationService;
        this.courseValidator = courseValidator;
    }

    @GetMapping
    public String findAll(Model model){
        List<Course> courses = courseService.findAll();
        model.addAttribute("courses", courses);
        return "courses/courses";
    }

    @GetMapping("/{id}")
    public String findById(@PathVariable Integer id, Model model){
        model.addAttribute("course", courseService.findById(id));
        return "courses/course";
    }

    @GetMapping("/{id}/update")
    public String updatePage(@PathVariable Integer id, Model model){
        model.addAttribute("course", courseService.findById(id));
        model.addAttribute("institutes", instituteService.findALl());
        model.addAttribute("specializations", specializationService.findAll());
        return "courses/update";
    }

    @PutMapping("/{id}")
    public String update(@ModelAttribute @Valid Course course, BindingResult bindingResult, @PathVariable Integer id,
    Model model){
        courseValidator.validate(course, bindingResult);
        if (bindingResult.hasErrors()){
            model.addAttribute("institutes", instituteService.findALl());
            model.addAttribute("specializations", specializationService.findAll());
            return "courses/update";
        }
        courseService.update(course, id);
        return "redirect:/courses";
    }

    @GetMapping("/new")
    public String newCourse(Model model){
        model.addAttribute("course", new Course());
        model.addAttribute("institutes", instituteService.findALl());
        model.addAttribute("specializations", specializationService.findAll());

        return "courses/new";
    }

    @PostMapping
    public String create(@ModelAttribute("course") @Valid Course course, BindingResult bindingResult, Model model){
        courseValidator.validate(course, bindingResult);
        if (bindingResult.hasErrors()){
            model.addAttribute("institutes", instituteService.findALl());
            model.addAttribute("specializations", specializationService.findAll());
            return "courses/new";
        }
        courseService.save(course);
        return "redirect:/courses";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Integer id){
        courseService.deleteById(id);
        return "redirect:/courses";
    }
}
