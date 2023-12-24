package com.example.courseWork.services;

import com.example.courseWork.models.Course;
import com.example.courseWork.models.Specialization;
import com.example.courseWork.repo.SpecializationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SpecializationService {
    private final SpecializationRepo specializationRepo;
    @Autowired
    public SpecializationService(SpecializationRepo specializationRepo) {
        this.specializationRepo = specializationRepo;
    }

    public Specialization findById(Integer id){
        return specializationRepo.findById(id).orElse(null);
    }
    public Specialization findByName(String name){
        return specializationRepo.findByName(name).orElse(null);
    }

    public Specialization findByCode(String code){
        return specializationRepo.findByCode(code).orElse(null);
    }

    public List<Specialization> findAll(){
        return specializationRepo.findAll();
    }
    public void save(Specialization specialization){
        specializationRepo.save(specialization);
    }
    public void update(Specialization specialization, Integer id){
        Specialization specialization1 = specializationRepo.findById(id).orElse(null);
        specialization1.setId(specialization.getId());
        specialization1.setName(specialization.getName());
        specialization1.setCode(specialization.getCode());
        specialization1.setInstitute(specialization.getInstitute());
        specialization1.setCourses(specialization.getCourses());
        specialization1.setInstitute(specialization.getInstitute());
        specializationRepo.save(specialization1);
    }

    public void delete(Integer id){
        specializationRepo.deleteById(id);
    }
    public List<Course> findCoursesBySpecializationId(Integer id){
        return specializationRepo.findById(id).get().getCourses();
    }
}
