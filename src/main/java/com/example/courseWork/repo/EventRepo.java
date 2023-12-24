package com.example.courseWork.repo;

import com.example.courseWork.models.Cabinet;
import com.example.courseWork.models.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Optional;

@Repository
public interface EventRepo extends JpaRepository<Event, Integer> {
    Optional<Event> findByDateAndNumberAndCabinet(Date date, Integer number, Cabinet cabinet);
}
