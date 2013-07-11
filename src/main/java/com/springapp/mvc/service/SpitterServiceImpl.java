package com.springapp.mvc.service;

import com.springapp.mvc.model.Spitter;
import com.springapp.mvc.model.Spittle;
import com.springapp.mvc.repository.SpitterRepository;
import com.springapp.mvc.repository.SpittleRepository;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

@Service
public class SpitterServiceImpl implements SpitterService{

    private SpitterRepository spitterRepository;
    private SpittleRepository spittleRepository;

    @Inject
    public SpitterServiceImpl(SpitterRepository spitterRepository,
                              SpittleRepository spittleRepository){
        this.spitterRepository = spitterRepository;
        this.spittleRepository = spittleRepository;
    }

    @Override
    @Secured("ROLE_SPITTER")
    public void saveSpittle(Spittle spittle) {
        spittleRepository.add(spittle);
    }

    @Override
    public void saveSpitter(Spitter spitter) {
        spitterRepository.addSpitter(spitter);
    }

    @Override
    public List<Spittle> getSpittleList(int numberOfSpittles) {
        return spittleRepository.getRecentSpittles(numberOfSpittles);
    }

    @Override
    public Spitter getSpitter(String username) {
        return spitterRepository.findByUserName(username);
    }

}
