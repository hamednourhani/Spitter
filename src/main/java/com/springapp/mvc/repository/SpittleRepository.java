package com.springapp.mvc.repository;

import com.springapp.mvc.model.Spitter;
import com.springapp.mvc.model.Spittle;

import java.util.List;

public interface SpittleRepository {
    public Spittle add(Spittle spittle);
    public Spittle find(int id);
    public List<Spittle> getRecentSpittles(int numOfSpittles);
    public List<Spittle> getSpittlesForSpitter(Spitter spitter);
}
