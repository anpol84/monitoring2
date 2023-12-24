package com.example.courseWork.services;

import com.example.courseWork.models.*;
import com.example.courseWork.repo.EventRepo;
import com.example.courseWork.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RegistrationService {
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final UntreatedStudentService untreatedStudentService;
    private final EventRepo eventRepo;
    @Autowired
    public RegistrationService(UserRepo userRepo, PasswordEncoder passwordEncoder,
                               UntreatedStudentService untreatedStudentService, EventRepo eventRepo) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
        this.untreatedStudentService = untreatedStudentService;
        this.eventRepo = eventRepo;
    }

    public void register(User user){
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        userRepo.save(user);
        if (user.getRole().equals("ROLE_STUDENT")){
            UntreatedStudent untreatedStudent = new UntreatedStudent();
            untreatedStudent.setUser(user);
            untreatedStudentService.save(untreatedStudent);
        }
    }
    public void registerAdmin(User user){
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        user.setRole("ROLE_ADMIN");
        userRepo.save(user);
    }

    public User findById(Integer id){
        return userRepo.findById(id).orElse(null);
    }
    public User findByEmail(String email){
        return userRepo.findByEmail(email).orElse(null);
    }
    public User findByLogin(String login){
        return userRepo.findByLogin(login).orElse(null);
    }

    public List<User> findTeachers(){
        return userRepo.findAllByRole("ROLE_TEACHER");
    }

    public void update(User user, Integer id){


        User user1 = userRepo.findById(id).orElse(null);
        String encodedPassword = passwordEncoder.encode(user.getPassword());

        user1.setId(user.getId());
        user1.setLogin(user.getLogin());
        user1.setPassword(encodedPassword);
        user1.setEmail(user.getEmail());
        user1.setName(user.getName());
        user1.setSurname(user.getSurname());
        user1.setLastname(user.getLastname());
        user1.setDepartment(user.getDepartment());
        user1.setSpecialization(user.getSpecialization());
        userRepo.save(user1);
    }

    public void delete(Integer id){
        UntreatedStudent untreatedStudent = untreatedStudentService.findById(id);
        if (untreatedStudent != null){
            untreatedStudentService.delete(untreatedStudent.getUser().getId());
        }
        userRepo.deleteById(id);
    }

    public List<User> findAll(){
        return userRepo.findAll();
    }

    public List<Event> findTeacherEvents(Integer id) {
        return userRepo.findById(id).orElse(null).getEvents();
    }

    public void signEvent(SignEventDTO signEvent) {
        User user = userRepo.findById(signEvent.getUserId()).orElse(null);
        Event event = eventRepo.findById(signEvent.getEventId()).orElse(null);
        if (user.getEvents() == null){
            user.setEvents(new ArrayList<>());
        }
        user.getEvents().add(event);
        if (event.getStudents() == null){
            event.setStudents(new ArrayList<>());
        }
        event.getStudents().add(user);
        event.setCount(event.getCount()-1);
        if (event.getCount() < 0){
            event.setCount(0);
        }
        userRepo.save(user);
        eventRepo.save(event);
    }

    public List<Event> findEvents(Integer id) {
        return userRepo.findById(id).orElse(null).getStudentEvents();
    }

    public List<Integer> getCourses(Integer id) {
        User user = userRepo.findById(id).orElse(null);
        List<Event> events = user.getStudentEvents();
        List<Integer> courses = new ArrayList<>();
        for (Event event : events){
            courses.add(event.getCourse().getId());
        }
        return courses;
    }

    public void save(User user) {
        userRepo.save(user);
    }
}
