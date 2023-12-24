package com.example.courseWork.services;

import com.example.courseWork.models.*;
import com.example.courseWork.repo.CourseRepo;
import com.example.courseWork.repo.RetakeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RetakeService {
    private final RetakeRepo retakeRepo;
    private final CourseRepo courseRepo;
    @Autowired
    public RetakeService(RetakeRepo retakeRepo, CourseRepo courseRepo) {
        this.retakeRepo = retakeRepo;
        this.courseRepo = courseRepo;
    }

    public void set(RetakeDTO retake){
        for (int i = 0; i < retake.getCourses().size(); i++){
            Retake retake1 = new Retake();
            retake1.setAttempt(retake.getAttempts().get(i));
            retake1.setUser(retake.getUser());
            retake1.setCourse(courseRepo.findById(retake.getCourses().get(i)).get());
            retakeRepo.save(retake1);
        }
    }

    public List<Retake> findAll(){
        return retakeRepo.findAll();
    }
    public Retake findById(Integer id){
        return retakeRepo.findById(id).orElse(null);
    }

    public void create(Retake retake){
        retakeRepo.save(retake);
    }

    public void update(Retake retake, Integer id){
        Retake retake1 = retakeRepo.findById(id).orElse(null);

        retake1.setAttempt(retake.getAttempt());
        retakeRepo.save(retake1);
    }

    public void delete(Integer id){
        retakeRepo.deleteById(id);
    }


    public List<Event> findEvents(Integer id) {
        List<Event> temp = retakeRepo.findById(id).orElse(null).getCourse().getEvents();
        List<Event> ans = new ArrayList<>();
        for (Event event : temp){
            if (event.getCount() > 0){
                ans.add(event);
            }
        }
        return ans;
    }

    public Retake findByUserAndCourse(User user, Course course){
        return retakeRepo.findByUserAndCourse(user, course).orElse(null);
    }
}
