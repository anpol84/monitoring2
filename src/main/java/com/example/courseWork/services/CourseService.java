package com.example.courseWork.services;

import com.example.courseWork.models.Course;
import com.example.courseWork.repo.CourseRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService {
    private final CourseRepo courseRepo;

    @Autowired
    public CourseService(CourseRepo courseRepo) {
        this.courseRepo = courseRepo;
    }

    public List<Course> findAll(){
        return courseRepo.findAll();
    }

    public Course findById(Integer id){
        return courseRepo.findById(id).orElse(null);
    }

    public void save(Course course){
        courseRepo.save(course);
    }

    public void update(Course course, Integer id){
        Course course1 = courseRepo.findById(id).orElse(null);
        course1.setId(course.getId());
        course1.setName(course.getName());
        course1.setNumber(course.getNumber());
        course1.setSemester(course.getSemester());
        course1.setInstitutes(course.getInstitutes());
        course1.setSpecializations(course.getSpecializations());
        course1.setType(course.getType());
        courseRepo.save(course1);

    }

    public void deleteById(Integer id){
        courseRepo.deleteById(id);
    }

    public Course findByNameAndNumberAndSemester(String name, Integer number, Integer semester){
        return courseRepo.findByNameAndNumberAndSemester(name, number, semester).orElse(null);
    }
}
