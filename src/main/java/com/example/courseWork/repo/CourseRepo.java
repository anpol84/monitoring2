package com.example.courseWork.repo;

import com.example.courseWork.models.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface CourseRepo extends JpaRepository<Course, Integer> {
    Optional<Course> findByNameAndNumberAndSemester(String name, Integer number, Integer Semester);
}
