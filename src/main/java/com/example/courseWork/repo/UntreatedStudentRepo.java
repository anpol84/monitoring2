package com.example.courseWork.repo;

import com.example.courseWork.models.UntreatedStudent;
import com.example.courseWork.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UntreatedStudentRepo extends JpaRepository<UntreatedStudent, Integer> {
    Optional<UntreatedStudent> findByUser(User user);
}
