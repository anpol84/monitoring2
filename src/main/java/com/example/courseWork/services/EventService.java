package com.example.courseWork.services;

import com.example.courseWork.models.*;
import com.example.courseWork.repo.EventRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class EventService {
    private final EventRepo eventRepo;
    private final RegistrationService registrationService;
    private final RetakeService retakeService;
    private final PastRetakeService pastRetakeService;
    @Autowired
    public EventService(EventRepo eventRepo, RegistrationService registrationService, RetakeService retakeService,
                        PastRetakeService pastRetakeService) {
        this.eventRepo = eventRepo;
        this.registrationService = registrationService;
        this.retakeService = retakeService;
        this.pastRetakeService = pastRetakeService;
    }

    public List<Event> findAll(){
        return eventRepo.findAll();
    }

    public Event findById(Integer id){
        return eventRepo.findById(id).orElse(null);
    }

    public Event findByDateAndNumberAndCabinet(Date date, Integer number, Cabinet cabinet){
        return eventRepo.findByDateAndNumberAndCabinet(date, number, cabinet).orElse(null);
    }

    public void save(Event event){
        eventRepo.save(event);
    }

    public void update(Event event, Integer id){
        Event event1 = eventRepo.findById(id).orElse(null);
        event1.setId(event.getId());
        event1.setDate(event.getDate());
        event1.setCabinet(event.getCabinet());
        event1.setNumber(event.getNumber());
        event1.setCourse(event.getCourse());
        event1.setCount(event.getCount());
        event1.setTeacher(event.getTeacher());
        eventRepo.save(event1);
    }

    public void delete(Integer id){
        eventRepo.deleteById(id);
    }

    public void submitRetakes(CheckRetakeDTO checkRetake) {
        List<Integer> userIds = checkRetake.getUserIds();
        List<String> isRetake = checkRetake.getIsRetake();
        Integer eventId = checkRetake.getEventId();
        Event event = eventRepo.findById(eventId).orElse(null);
        for (int i = 0; i < userIds.size(); i++){
            boolean flag = true;
            User user = registrationService.findById(userIds.get(i));
            PastRetake pastRetake = new PastRetake();
            pastRetake.setDate(event.getDate());
            pastRetake.setSubject(event.getCourse().getName());
            pastRetake.setUser(user);
            System.out.println(isRetake.get(i).equals("pass"));
            if (isRetake.get(i).equals("pass")){
                List<User> tempStudent = event.getStudents();
                tempStudent.remove(user);
                event.setStudents(tempStudent);
                List<Event> tempEvents = user.getStudentEvents();
                tempEvents.remove(event);
                user.setStudentEvents(tempEvents);
                eventRepo.save(event);
                registrationService.save(user);
                retakeService.delete(retakeService.findByUserAndCourse(user, event.getCourse()).getId());
                pastRetake.setResult("Пересдал");
            }else{
                Retake retake = retakeService.findByUserAndCourse(user, event.getCourse());
                retake.setAttempt(retake.getAttempt()-1);
                if (retake.getAttempt() == 0){
                    flag = false;
                    retake.setAttempt(1);
                    registrationService.delete(user.getId());
                }
                retakeService.create(retake);
                pastRetake.setResult("Не пересдал");
            }
            if (flag){
                pastRetakeService.save(pastRetake);
            }

        }
        eventRepo.deleteById(event.getId());
    }
}
