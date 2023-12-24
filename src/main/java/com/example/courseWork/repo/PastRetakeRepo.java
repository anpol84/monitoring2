package com.example.courseWork.repo;

import com.example.courseWork.models.PastRetake;
import com.example.courseWork.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PastRetakeRepo extends JpaRepository<PastRetake, Integer> {

    List<PastRetake> findAllByUser(User user);
}
