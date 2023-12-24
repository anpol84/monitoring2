package com.example.courseWork.controllers;

import com.example.courseWork.models.Event;
import com.example.courseWork.models.Institute;
import com.example.courseWork.security.PersonDetails;
import com.example.courseWork.services.CabinetService;
import com.example.courseWork.services.CourseService;
import com.example.courseWork.services.EventService;
import com.example.courseWork.services.RegistrationService;
import com.example.courseWork.util.EventValidator;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/events")
public class EventController {
    private final EventService eventService;
    private final EventValidator eventValidator;
    private final CourseService courseService;
    private final CabinetService cabinetService;
    private final RegistrationService registrationService;
    @Autowired
    public EventController(EventService eventService, EventValidator eventValidator, CourseService courseService,
                           CabinetService cabinetService, RegistrationService registrationService) {
        this.eventService = eventService;
        this.eventValidator = eventValidator;
        this.courseService = courseService;
        this.cabinetService = cabinetService;
        this.registrationService = registrationService;
    }

    @GetMapping
    public String findAll(Model model){
        List<Event> events = eventService.findAll();
        model.addAttribute("events", events);

        return "events/events";
    }

    @GetMapping("/{id}")
    public String findById(@PathVariable Integer id, Model model){
        model.addAttribute("event", eventService.findById(id));
        return "events/event";
    }

    @GetMapping("/{id}/update")
    public String updatePage(@PathVariable Integer id, Model model){
        model.addAttribute("event", eventService.findById(id));
        model.addAttribute("courses", courseService.findAll());
        model.addAttribute("cabinets", cabinetService.findAll());
        model.addAttribute("teachers", registrationService.findTeachers());
        return "events/update";
    }

    @PutMapping("/{id}")
    public String update(@ModelAttribute @Valid Event event,
                         BindingResult bindingResult, @PathVariable Integer id, Model model){
        eventValidator.validate(event, bindingResult);
        if (bindingResult.hasErrors()){
            model.addAttribute("courses", courseService.findAll());
            model.addAttribute("cabinets", cabinetService.findAll());
            model.addAttribute("teachers", registrationService.findTeachers());
            return "events/update";
        }
        eventService.update(event, id);
        return "redirect:/events";
    }

    @GetMapping("/new")
    public String newEvent(Model model){
        model.addAttribute("event", new Event());
        model.addAttribute("courses", courseService.findAll());
        model.addAttribute("cabinets", cabinetService.findAll());
        model.addAttribute("teachers", registrationService.findTeachers());
        return "events/new";
    }

    @PostMapping
    public String create(@ModelAttribute("event") @Valid Event event, BindingResult bindingResult, Model model){
        eventValidator.validate(event, bindingResult);

        if (bindingResult.hasErrors()){
            model.addAttribute("courses", courseService.findAll());
            model.addAttribute("cabinets", cabinetService.findAll());
            model.addAttribute("teachers", registrationService.findTeachers());
            return "events/new";
        }

        eventService.save(event);
        return "redirect:/events";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Integer id){
        eventService.delete(id);
        return "redirect:/events";
    }
}
