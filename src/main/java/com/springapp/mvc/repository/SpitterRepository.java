package com.springapp.mvc.repository;

import com.springapp.mvc.model.Followee;
import com.springapp.mvc.model.Spitter;
import com.springapp.mvc.model.Spittle;

import java.util.List;

public interface SpitterRepository {

    public Spitter addSpitter(Spitter spitter);
    public void update(Spitter spitter);
    public void merge(Followee followee);
    public void addFollowee(Spitter spitter, Spitter followee);
    public Spitter find(int id);
    public Spitter findByUserName(String username);
    public List<Spitter> getFollowers(Spitter spitter);
    public List<Spitter> getFollowees(Spitter spitter);
    public boolean isUserFollowing(Spitter spitter, Spitter followee);
    public List<Spittle> getFolloweeSpittlesForSpitter(Spitter spitter);
}
