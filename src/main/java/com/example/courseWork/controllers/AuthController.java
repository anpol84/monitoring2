package com.example.courseWork.controllers;

import com.example.courseWork.models.*;
import com.example.courseWork.security.PersonDetails;
import com.example.courseWork.services.*;
import com.example.courseWork.util.CourseValidator;
import com.example.courseWork.util.RetakeValidator;
import com.example.courseWork.util.UserUpdateValidator;
import com.example.courseWork.util.UserValidator;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.access.method.P;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/auth")
public class AuthController {
    private final UserValidator userValidator;
    private final RegistrationService registrationService;
    private final UserUpdateValidator userUpdateValidator;
    private final DepartmentService departmentService;
    private final SpecializationService specializationService;
    private final RetakeService retakeService;
    private final RetakeValidator retakeValidator;
    private final EventService eventService;
    private final UntreatedStudentService untreatedStudentService;

    @Autowired
    public AuthController(UserValidator userValidator, RegistrationService registrationService,
                          UserUpdateValidator userUpdateValidator, DepartmentService departmentService,
                          SpecializationService specializationService, RetakeService retakeService,
                          RetakeValidator retakeValidator, EventService eventService,
                          UntreatedStudentService untreatedStudentService) {
        this.userValidator = userValidator;
        this.registrationService = registrationService;
        this.userUpdateValidator = userUpdateValidator;
        this.departmentService = departmentService;
        this.specializationService = specializationService;
        this.retakeService = retakeService;
        this.retakeValidator = retakeValidator;
        this.eventService = eventService;
        this.untreatedStudentService = untreatedStudentService;

    }

    @GetMapping("/login")
    public String loginPage(){

        return "auth/login";
    }
    @GetMapping("/register")
    public String registrationPage(@ModelAttribute("user") User user, Model model){
        model.addAttribute("departments", departmentService.findAll());
        model.addAttribute("specializations", specializationService.findAll());
        return "auth/register";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute("user") @Valid User user, BindingResult bindingResult, Model model){
        userValidator.validate(user, bindingResult);
        if (bindingResult.hasErrors()){
            model.addAttribute("departments", departmentService.findAll());
            model.addAttribute("specializations", specializationService.findAll());
            return "auth/register";
        }
        registrationService.register(user);
        return "redirect:/auth/login";
    }



    @GetMapping("/profile")
    public String profilePage(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
        model.addAttribute("user", personDetails);
        return "auth/profile";
    }
    @GetMapping("/profileInfoT/{id}")
    public String profileInfoPage(@PathVariable Integer id, Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
        if (!personDetails.getUser().getRole().equals("ROLE_ADMIN")
                && personDetails.getUser().getId() != id) {
            return "auth/login";
        }
        model.addAttribute("user", registrationService.findById(id));
        model.addAttribute("user2", personDetails.getUser());
        return "auth/profile_info";
    }

    @GetMapping("/retakesInfo/{id}")
    public String retakesInfo(@PathVariable Integer id, Model model){
        model.addAttribute("user", registrationService.findById(id));
        model.addAttribute("untreatedUser", untreatedStudentService.findByUser(registrationService.findById(id)));
        model.addAttribute("excitingRetakes", registrationService.getCourses(id));
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
        if (!personDetails.getUser().getRole().equals("ROLE_ADMIN")
                && personDetails.getUser().getId() != id) {
            return "auth/login";
        }
        return "auth/retakesInfo";
    }

    @GetMapping("/{id}/profileChange")
    public String profileChange(@PathVariable Integer id, Model model){
        model.addAttribute("departments", departmentService.findAll());
        model.addAttribute("specializations", specializationService.findAll());
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
        if (!personDetails.getUser().getRole().equals("ROLE_ADMIN")
                && personDetails.getUser().getId() != id) {
            return "auth/login";
        }

        model.addAttribute("user", registrationService.findById(id));
        return "auth/profileChange";
    }

    @PutMapping("/profile/{id}")
    public String update(@ModelAttribute @Valid User user, BindingResult bindingResult, @PathVariable Integer id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
        if (!personDetails.getUser().getRole().equals("ROLE_ADMIN")
                && personDetails.getUser().getId() != id) {
            return "auth/login";
        }

        userUpdateValidator.validate(user, bindingResult);
        if (bindingResult.hasErrors()){
            return "auth/profileChange";
        }
        registrationService.update(user, id);
        if (id != personDetails.getUser().getId()){
            return "redirect:/auth/profiles";
        }
        return "redirect:/auth/login";
    }

    @DeleteMapping("/profile/{id}")
    public String delete(@PathVariable Integer id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
        if (!personDetails.getUser().getRole().equals("ROLE_ADMIN")
                && personDetails.getUser().getId() != id) {
            return "auth/login";
        }
        registrationService.delete(id);
        if (id != personDetails.getUser().getId()){
            return "redirect:/auth/profiles";
        }
        return "redirect:/auth/register";
    }


    @GetMapping("/registerAdmin")
    public String registrationAdminPage(@ModelAttribute("user") User user){
        return "auth/registerAdmin";
    }


    @PostMapping("/registerAdmin")
    public String registerAdmin(@ModelAttribute("user") @Valid User user, BindingResult bindingResult){
        userValidator.validate(user, bindingResult);
        if (bindingResult.hasErrors()){
            return "auth/registerAdmin";
        }
        registrationService.registerAdmin(user);
        return "redirect:/auth/profile";
    }

    @GetMapping("/profiles")
    public String findAll(Model model){
        List<User> users = registrationService.findAll();
        model.addAttribute("users", users);
        return "auth/profiles";
    }


    @GetMapping("/profileInfo/{idR}/{idU}/update")
    public String updatePage(@PathVariable("idU") Integer idU, @PathVariable("idR") Integer idR, Model model){
        model.addAttribute("retake", retakeService.findById(idR));
        model.addAttribute("idU", idU);
        model.addAttribute("idR", idR);
        return "retakes/update";
    }

    @PutMapping("/profileInfo/{idR}/{idU}")
    public String update(@ModelAttribute @Valid Retake retake, BindingResult bindingResult,
                         @PathVariable("idU") Integer idU, @PathVariable("idR") Integer idR){
        if (bindingResult.hasErrors()){
            return "retakes/update";
        }
        retakeService.update(retake, idR);
        return "redirect:/auth/profileInfo/" + idU;
    }

    @GetMapping("/profileInfo/{id}/new")
    public String newRetake(Model model, @PathVariable Integer id){

        model.addAttribute("retake", new Retake());
        model.addAttribute("id", id);
        model.addAttribute("courses", registrationService.findById(id).getSpecialization().getCourses());
        return "retakes/new";
    }

    @PostMapping("/profileInfo/{id}")
    public String create(@ModelAttribute("retake") @Valid Retake retake, BindingResult bindingResult, @PathVariable Integer id
    , Model model){
        retake.setUser(registrationService.findById(id));
        retakeValidator.validate(retake, bindingResult);
        if (bindingResult.hasErrors()){
            model.addAttribute("id", id);
            model.addAttribute("courses", registrationService.findById(id).getSpecialization().getCourses());
            return "retakes/new";
        }

        retakeService.create(retake);
        return "redirect:/auth/profileInfo/" + id;
    }

    @DeleteMapping("/profileInfo/{idR}/{idU}")
    public String deleteRetake(@PathVariable("idR") Integer idR, @PathVariable("idU") Integer idU){
        retakeService.delete(idR);
        return "redirect:/auth/profileInfo/" + idU;
    }



    @GetMapping("/teacherRetakes/{id}")
    public String teacherRetakes(@PathVariable Integer id, Model model){
        if (!registrationService.findById(id).getRole().equals("ROLE_TEACHER")){
            return "auth/login";
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
        if (!personDetails.getUser().getRole().equals("ROLE_ADMIN")
                && personDetails.getUser().getId() != id) {
            return "auth/login";
        }
        model.addAttribute("events", registrationService.findTeacherEvents(id));
        return "auth/teacherRetake";
    }

    @GetMapping("/signRetake/{id}/{idU}")
    public String signRetake(@PathVariable("id") Integer id, @PathVariable("idU") Integer userId, Model model){
        if (!registrationService.findById(userId).getRole().equals("ROLE_STUDENT")){
            return "auth/login";
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
        if (!personDetails.getUser().getRole().equals("ROLE_ADMIN")
                && personDetails.getUser().getId() != userId) {
            return "auth/login";
        }
        model.addAttribute("events", retakeService.findEvents(id));
        model.addAttribute("id", userId);
        model.addAttribute("dto", new SignEventDTO());
        return "auth/signRetake";
    }

    @PostMapping("/signEvent")
    public String signEvent(@ModelAttribute("dto")SignEventDTO signEvent, Model model){

        registrationService.signEvent(signEvent);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
        model.addAttribute("user", personDetails);
        return "auth/profile";

    }

    @GetMapping("/userEvents/{id}")
    public String userEvents(@PathVariable("id") Integer id, Model model){
        if (!registrationService.findById(id).getRole().equals("ROLE_STUDENT")){
            return "auth/login";
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
        if (!personDetails.getUser().getRole().equals("ROLE_ADMIN")
                && personDetails.getUser().getId() != id) {
            return "auth/login";
        }
        model.addAttribute("events", registrationService.findEvents(id));
        return "auth/userEvents";
    }

    @GetMapping("/checkRetake/{id}")
    public String checkRetake(@PathVariable("id") Integer id, Model model){

        model.addAttribute("event", eventService.findById(id));
        model.addAttribute("dto", new CheckRetakeDTO());
        return "auth/checkRetake";
    }

    @PostMapping("/checkRetakes")
    public String submitRetakes(@ModelAttribute CheckRetakeDTO checkRetake, Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
        model.addAttribute("user", personDetails);
        eventService.submitRetakes(checkRetake);
        return "auth/profile";
    }

}
