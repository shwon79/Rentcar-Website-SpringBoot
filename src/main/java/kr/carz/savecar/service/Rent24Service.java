package kr.carz.savecar.service;

import kr.carz.savecar.domain.Rent24;
import kr.carz.savecar.repository.Rent24Repository;

import java.util.List;
import java.util.Optional;

public class Rent24Service {

    private final Rent24Repository rent24Repository;

    public Rent24Service(Rent24Repository rent24Repository){
        this.rent24Repository = rent24Repository;
    }

    public Optional<Rent24> findByRent24Id(Long id){ return rent24Repository.findByRent24Id(id); }

    public List<Rent24> findAll() {return rent24Repository.findAll();}

    public Long save(Rent24 rent24){ return rent24Repository.save(rent24).getRent24Id(); }

    public void deleteAllInBatch(){
        rent24Repository.deleteAllInBatch();
    }
}