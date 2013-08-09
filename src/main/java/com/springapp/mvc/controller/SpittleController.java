package com.springapp.mvc.controller;

import com.springapp.mvc.model.Spitter;
import com.springapp.mvc.model.Spittle;
import com.springapp.mvc.repository.SpitterRepository;
import com.springapp.mvc.repository.SpittleRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;

@Transactional
@Controller
public class SpittleController {

    private SpitterRepository spitterRepository;
    private SpittleRepository spittleRepository;

    public SpittleController() {
    }

    @Inject
    public SpittleController(SpitterRepository spitterRepository, SpittleRepository spittleRepository){
        this.spitterRepository = spitterRepository;
        this.spittleRepository = spittleRepository;
    }

    @RequestMapping(value="${spitter.userName}", method = RequestMethod.POST)
    public String postSpittle(@ModelAttribute("spittle") Spittle spittle){
        Spitter spitter = new Spitter();
        spittle.setSpitter(spitter);
        spittleRepository.add(spittle);
        return "/home";
    }

    @RequestMapping(value = "/mySpitterCircle", method = RequestMethod.GET)
    public String getSpittles(Model model){
        Authentication user = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("followeeSpittleList", spitterRepository.getFolloweeSpittlesForSpitter(user.getName()));
        model.addAttribute("spittle", new Spittle());
        return "/mySpitterCircle";
    }

}