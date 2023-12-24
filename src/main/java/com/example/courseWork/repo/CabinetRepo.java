package com.example.courseWork.repo;

import com.example.courseWork.models.Cabinet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CabinetRepo extends JpaRepository<Cabinet, Integer> {
    Optional<Cabinet> findByNumber(Integer number);
}
