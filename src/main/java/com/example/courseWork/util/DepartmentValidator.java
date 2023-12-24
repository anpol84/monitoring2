package com.example.courseWork.util;

import com.example.courseWork.models.Department;
import com.example.courseWork.models.Institute;
import com.example.courseWork.services.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class DepartmentValidator implements Validator {
    private final DepartmentService departmentService;
    @Autowired
    public DepartmentValidator(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }
    @Override
    public boolean supports(Class<?> clazz) {
        return Department.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Department department = (Department)target;
        Department department1 = departmentService.findByName(department.getName());
        if (department.getId() == null) {
            if (department1 != null) {
                errors.rejectValue("name", "", "Кафедра с таким названием уже существует");
            }
        }else{
            if (department1 != null && department1.getId() != department.getId()) {
                errors.rejectValue("name", "", "Кафедра с таким названием уже существует");
            }
        }


    }
}
