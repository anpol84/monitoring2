package com.example.courseWork.util;

import com.example.courseWork.models.Institute;
import com.example.courseWork.models.Specialization;
import com.example.courseWork.models.User;
import com.example.courseWork.services.InstituteService;
import com.example.courseWork.services.SpecializationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
@Component
public class SpecializationValidator implements Validator {
    private final SpecializationService specializationService;
    @Autowired
    public SpecializationValidator(SpecializationService specializationService) {
        this.specializationService = specializationService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Specialization.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Specialization specialization = (Specialization) target;
        Specialization specialization1 = specializationService.findByName(specialization.getName());
        if (specialization.getId() == null){
            if (specialization1 != null){
                errors.rejectValue("name", "", "Специальность с таким названием уже существует");
            }
        }else{
            if (specialization1 != null && specialization1.getId() != specialization.getId()){
                errors.rejectValue("name", "", "Специальность с таким названием уже существует");
            }
        }

        Specialization specialization2 = specializationService.findByCode(specialization.getCode());
        if (specialization.getId() == null){
            if (specialization2 != null){
                errors.rejectValue("code", "", "Специальность с таким кодом уже существует");
            }
        }else{
            if (specialization2 != null && specialization2.getId() != specialization.getId()){
                errors.rejectValue("code", "", "Специальность с таким кодом уже существует");
            }
        }


    }
}
