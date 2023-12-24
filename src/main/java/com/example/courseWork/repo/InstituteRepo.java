package com.example.courseWork.repo;

import com.example.courseWork.models.Institute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InstituteRepo extends JpaRepository<Institute, Integer> {
    Optional<Institute> findByName(String name);
}
