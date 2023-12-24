package com.example.courseWork.services;

import com.example.courseWork.models.Cabinet;
import com.example.courseWork.repo.CabinetRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CabinetService {
    private final CabinetRepo cabinetRepo;
    @Autowired
    public CabinetService(CabinetRepo cabinetRepo) {
        this.cabinetRepo = cabinetRepo;
    }

   public Cabinet findByNumber(Integer number){
        return cabinetRepo.findByNumber(number).orElse(null);
   }

   public List<Cabinet> findAll(){
        return cabinetRepo.findAll();
   }

   public Cabinet findById(Integer id){
        return cabinetRepo.findById(id).orElse(null);
   }

   public void save(Cabinet cabinet){
        cabinetRepo.save(cabinet);
   }

   public void update(Cabinet cabinet, Integer id){
        Cabinet cabinet1 = cabinetRepo.findById(id).orElse(null);
        cabinet1.setId(cabinet.getId());
        cabinet1.setNumber(cabinet.getNumber());
        cabinetRepo.save(cabinet1);
   }

   public void delete(Integer id){
        cabinetRepo.deleteById(id);
   }
}
