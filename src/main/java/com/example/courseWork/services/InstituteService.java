package com.example.courseWork.services;

import com.example.courseWork.models.Institute;
import com.example.courseWork.repo.InstituteRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InstituteService {

    private final InstituteRepo instituteRepo;
    @Autowired
    public InstituteService(InstituteRepo instituteRepo) {
        this.instituteRepo = instituteRepo;
    }

    public List<Institute> findALl(){
        return instituteRepo.findAll();
    }

    public Institute findById(Integer id){
        return instituteRepo.findById(id).orElse(null);
    }

    public void save(Institute institute){
        instituteRepo.save(institute);
    }

    public void update(Institute institute, Integer id){
        Institute institute1 = instituteRepo.findById(id).orElse(null);
        institute1.setSpecializations(institute.getSpecializations());
        institute1.setCourses(institute.getCourses());
        institute1.setDepartments(institute.getDepartments());
        institute1.setId(institute.getId());
        institute1.setName(institute.getName());
        instituteRepo.save(institute1);
    }

    public void delete(Integer id){
        instituteRepo.deleteById(id);
    }

    public Institute findByName(String name){
        return instituteRepo.findByName(name).orElse(null);
    }
}
