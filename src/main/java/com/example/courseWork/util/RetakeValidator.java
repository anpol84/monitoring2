package com.example.courseWork.util;

import com.example.courseWork.models.Institute;
import com.example.courseWork.models.Retake;
import com.example.courseWork.services.RetakeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.List;

@Component
public class RetakeValidator implements Validator {
    private final RetakeService retakeService;
    @Autowired
    public RetakeValidator(RetakeService retakeService) {
        this.retakeService = retakeService;
    }
    @Override
    public boolean supports(Class<?> clazz) {
        return Retake.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        Retake retake = (Retake) target;

        List<Retake> retakes = retakeService.findAll();
        for (Retake retake1 : retakes){
            System.out.println(retake1);
            if (retake.getUser().getId() == retake1.getUser().getId() && retake.getCourse().getId() == retake1.getCourse().getId()){
                errors.rejectValue("course", "", "Пересдача такого предмета уже существует");
            }
        }


    }
}
