package com.example.courseWork.util;

import com.example.courseWork.models.Course;
import com.example.courseWork.models.Institute;
import com.example.courseWork.services.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class CourseValidator implements Validator {
    private final CourseService courseService;
    @Autowired
    public CourseValidator(CourseService courseService) {
        this.courseService = courseService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Course.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Course course = (Course) target;
        Course course1 = courseService.findByNameAndNumberAndSemester(course.getName(), course.getNumber(), course.getSemester());

        if (course.getId() == null) {
            if (course1 != null) {

                errors.rejectValue("name", "", "Такой курс уже существует");

            }
        }else{
            if (course1 != null && course1.getId() != course.getId()) {

                errors.rejectValue("name", "", "Такой курс уже существует");

            }
        }


    }
}
