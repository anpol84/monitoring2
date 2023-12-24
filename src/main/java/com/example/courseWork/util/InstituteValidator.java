package com.example.courseWork.util;

import com.example.courseWork.models.Institute;
import com.example.courseWork.models.User;
import com.example.courseWork.services.InstituteService;
import com.example.courseWork.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Optional;

@Component
public class InstituteValidator implements Validator {
    private final InstituteService instituteService;
    @Autowired
    public InstituteValidator(InstituteService instituteService) {
        this.instituteService = instituteService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Institute.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Institute institute = (Institute) target;
        Institute institute1 = instituteService.findByName(institute.getName());
        if (institute.getId() == null) {
            if (institute1 != null) {
                errors.rejectValue("name", "", "Институт с таким названием уже существует");
            }
        }else{
            if (institute1 != null && institute1.getId() != institute.getId()) {
                errors.rejectValue("name", "", "Институт с таким названием уже существует");
            }
        }


    }
}
