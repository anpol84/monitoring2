package com.example.courseWork.controllers;

import com.example.courseWork.models.Department;
import com.example.courseWork.models.Institute;
import com.example.courseWork.services.DepartmentService;
import com.example.courseWork.services.InstituteService;
import com.example.courseWork.util.DepartmentValidator;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/departments")
public class DepartmentController {
    private final DepartmentService departmentService;
    private final DepartmentValidator departmentValidator;
    private final InstituteService instituteService;
    @Autowired
    public DepartmentController(DepartmentService departmentService, DepartmentValidator departmentValidator,
                                InstituteService instituteService) {
        this.departmentService = departmentService;
        this.departmentValidator = departmentValidator;
        this.instituteService = instituteService;
    }

    @GetMapping
    public String findAll(Model model){
        List<Department> departments = departmentService.findAll();
        model.addAttribute("departments", departments);
        return "departments/departments";
    }

    @GetMapping("/{id}")
    public String findById(@PathVariable Integer id, Model model){
        model.addAttribute("department", departmentService.findById(id));
        return "departments/department";
    }

    @GetMapping("/{id}/update")
    public String updatePage(@PathVariable Integer id, Model model){
        model.addAttribute("department", departmentService.findById(id));
        model.addAttribute("institutes", instituteService.findALl());
        return "departments/update";
    }

    @PutMapping("/{id}")
    public String update(@ModelAttribute @Valid Department department,
                         BindingResult bindingResult, @PathVariable Integer id){
        departmentValidator.validate(department, bindingResult);
        if (bindingResult.hasErrors()){
            return "departments/update";
        }
        departmentService.update(department, id);
        return "redirect:/departments";
    }

    @GetMapping("/new")
    public String newDepartment(Model model){
        model.addAttribute("department", new Department());
        model.addAttribute("institutes", instituteService.findALl());
        return "departments/new";
    }

    @PostMapping
    public String create(@ModelAttribute("department") @Valid Department department, BindingResult bindingResult){
        departmentValidator.validate(department, bindingResult);
        if (bindingResult.hasErrors()){
            return "departments/new";
        }
        departmentService.save(department);
        return "redirect:/departments";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Integer id){
        departmentService.delete(id);
        return "redirect:/departments";
    }
}
