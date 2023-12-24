package com.example.courseWork.util;

import com.example.courseWork.models.Event;
import com.example.courseWork.models.Institute;
import com.example.courseWork.services.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class EventValidator implements Validator {
    private final EventService eventService;
    @Autowired
    public EventValidator(EventService eventService) {
        this.eventService = eventService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Event.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Event event = (Event) target;
        Event event1 = eventService.findByDateAndNumberAndCabinet(event.getDate(), event.getNumber(), event.getCabinet());

        if (event.getId() == null) {
            if (event1 != null) {
                errors.rejectValue("cabinet", "", "В этом кабинете в это время уже назначена пересдача");
            }
        }else{
            if (event1 != null && event1.getId() != event.getId()) {
                errors.rejectValue("cabinet", "", "В этом кабинете в это время уже назначена пересдача");
            }
        }


    }
}
