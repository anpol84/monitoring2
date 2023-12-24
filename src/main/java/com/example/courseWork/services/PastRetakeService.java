package com.example.courseWork.services;

import com.example.courseWork.models.PastRetake;
import com.example.courseWork.models.User;
import com.example.courseWork.repo.PastRetakeRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PastRetakeService {
    private final PastRetakeRepo pastRetakeRepo;

    public PastRetakeService(PastRetakeRepo pastRetakeRepo) {
        this.pastRetakeRepo = pastRetakeRepo;
    }

    public List<PastRetake> findAllByUser(User user){
        return pastRetakeRepo.findAllByUser(user);
    }

    public void save(PastRetake pastRetake){
        pastRetakeRepo.save(pastRetake);
    }

    public void delete(Integer id){
        pastRetakeRepo.deleteById(id);
    }
}
