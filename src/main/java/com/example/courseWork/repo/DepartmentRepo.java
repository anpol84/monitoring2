package com.example.courseWork.repo;

import com.example.courseWork.models.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DepartmentRepo extends JpaRepository<Department, Integer> {
    Optional<Department> findByName(String name);
}
