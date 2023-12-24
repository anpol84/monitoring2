package com.example.courseWork.controllers;

import com.example.courseWork.models.Cabinet;
import com.example.courseWork.models.Course;
import com.example.courseWork.services.CabinetService;
import com.example.courseWork.util.CabinetValidator;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/cabinets")
public class CabinetController {

    private final CabinetService cabinetService;
    private final CabinetValidator cabinetValidator;
    @Autowired
    public CabinetController(CabinetService cabinetService, CabinetValidator cabinetValidator) {
        this.cabinetService = cabinetService;
        this.cabinetValidator = cabinetValidator;
    }
    @GetMapping
    public String findAll(Model model){
        List<Cabinet> cabinets = cabinetService.findAll();
        model.addAttribute("cabinets", cabinets);
        return "cabinets/cabinets";
    }

    @GetMapping("/{id}")
    public String findById(@PathVariable Integer id, Model model){
        model.addAttribute("cabinet", cabinetService.findById(id));
        return "cabinets/cabinet";
    }

    @GetMapping("/{id}/update")
    public String updatePage(@PathVariable Integer id, Model model){
        model.addAttribute("cabinet", cabinetService.findById(id));
        return "cabinets/update";
    }

    @PutMapping("/{id}")
    public String update(@ModelAttribute @Valid Cabinet cabinet, BindingResult bindingResult, @PathVariable Integer id,
                         Model model){
        cabinetValidator.validate(cabinet, bindingResult);
        if (bindingResult.hasErrors()){
            return "cabinets/update";
        }
        cabinetService.update(cabinet, id);
        return "redirect:/cabinets";
    }

    @GetMapping("/new")
    public String newCabinet(Model model){
        model.addAttribute("cabinet", new Cabinet());

        return "cabinets/new";
    }

    @PostMapping
    public String create(@ModelAttribute("cabinet") @Valid Cabinet cabinet, BindingResult bindingResult, Model model){
        cabinetValidator.validate(cabinet, bindingResult);
        if (bindingResult.hasErrors()){
            return "cabinets/new";
        }
        cabinetService.save(cabinet);
        return "redirect:/cabinets";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Integer id){
        cabinetService.delete(id);
        return "redirect:/cabinets";
    }
}
