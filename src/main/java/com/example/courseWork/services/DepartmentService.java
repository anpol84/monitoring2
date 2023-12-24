package com.example.courseWork.services;

import com.example.courseWork.models.Department;
import com.example.courseWork.repo.DepartmentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentService {
    private final DepartmentRepo departmentRepo;
    @Autowired
    public DepartmentService(DepartmentRepo departmentRepo) {
        this.departmentRepo = departmentRepo;
    }
    public List<Department> findAll(){
        return departmentRepo.findAll();
    }

    public Department findById(Integer id){
        return departmentRepo.findById(id).orElse(null);
    }

    public Department findByName(String name){
        return departmentRepo.findByName(name).orElse(null);
    }

    public void save(Department department){
        departmentRepo.save(department);
    }

    public void update(Department department, Integer id){
        Department department1 = departmentRepo.findById(id).orElse(null);
        department1.setId(department.getId());
        department1.setName(department.getName());
        department1.setInstitute(department.getInstitute());
        department1.setTeachers(department.getTeachers());
        departmentRepo.save(department1);
    }

    public void delete(Integer id){
        departmentRepo.deleteById(id);
    }
}
