package com.example.courseWork.repo;

import com.example.courseWork.models.Specialization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SpecializationRepo extends JpaRepository<Specialization, Integer> {
    Optional<Specialization> findByName(String name);
    Optional<Specialization> findByCode(String code);
}
