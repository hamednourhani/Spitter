package com.springapp.mvc.service;

import com.springapp.mvc.model.Spitter;
import com.springapp.mvc.model.Spittle;

import java.util.List;

public interface SpitterService {
    public void saveSpittle(Spittle spittle);
    public void saveSpitter(Spitter spitter);
    public List<Spittle> getSpittleList(int numberOfSpittles);
    public Spitter getSpitter(String username);
}
