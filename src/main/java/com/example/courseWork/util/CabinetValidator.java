package com.example.courseWork.util;

import com.example.courseWork.models.Cabinet;
import com.example.courseWork.models.Course;
import com.example.courseWork.services.CabinetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class CabinetValidator implements Validator {
    private final CabinetService cabinetService;
    @Autowired
    public CabinetValidator(CabinetService cabinetService) {
        this.cabinetService = cabinetService;
    }
    @Override
    public boolean supports(Class<?> clazz) {
        return Cabinet.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Cabinet cabinet = (Cabinet) target;
        Cabinet cabinet1 = cabinetService.findByNumber(cabinet.getNumber());
        if (cabinet.getId() == null) {
            if (cabinet1 != null) {

                errors.rejectValue("number", "", "Такой кабинет уже существует");

            }
        }else{
            if (cabinet1 != null && cabinet1.getId() != cabinet.getId()) {

                errors.rejectValue("number", "", "Такой кабинет уже существует");

            }
        }


    }
}
