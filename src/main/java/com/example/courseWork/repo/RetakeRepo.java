package com.example.courseWork.repo;

import com.example.courseWork.models.Course;
import com.example.courseWork.models.Retake;
import com.example.courseWork.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RetakeRepo extends JpaRepository<Retake, Integer> {
    Optional<Retake> findByUserAndCourse(User user, Course course);
}
