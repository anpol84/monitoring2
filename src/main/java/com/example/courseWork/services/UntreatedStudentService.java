package com.example.courseWork.services;

import com.example.courseWork.models.UntreatedStudent;
import com.example.courseWork.models.User;
import com.example.courseWork.repo.UntreatedStudentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UntreatedStudentService {
    private final UntreatedStudentRepo untreatedStudentRepo;
    @Autowired
    public UntreatedStudentService(UntreatedStudentRepo untreatedStudentRepo) {
        this.untreatedStudentRepo = untreatedStudentRepo;
    }

    public List<UntreatedStudent> findAll(){
        return untreatedStudentRepo.findAll();
    }

    public UntreatedStudent findById(Integer id){
        return untreatedStudentRepo.findById(id).orElse(null);
    }

    public void save(UntreatedStudent untreatedStudent){
        untreatedStudentRepo.save(untreatedStudent);
    }

    public void delete(Integer id){
        untreatedStudentRepo.deleteById(id);
    }

    public Object findByUser(User user) {
        return untreatedStudentRepo.findByUser(user).orElse(null);
    }
}
