package com.springapp.mvc.controller;

import com.springapp.mvc.model.Follower;
import com.springapp.mvc.model.Spitter;
import com.springapp.mvc.model.Spittle;
import com.springapp.mvc.repository.SpitterRepository;
import com.springapp.mvc.repository.SpittleRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;

@Transactional
@Controller
@RequestMapping(value = "/spitters")
public class SpitterController {

    private SpitterRepository spitterRepository;
    private SpittleRepository spittleRepository;

    public SpitterController() {
    }

    @Inject
    public SpitterController(SpitterRepository spitterRepository, SpittleRepository spittleRepository){
        this.spitterRepository = spitterRepository;
        this.spittleRepository = spittleRepository;
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String createSpitterProfile(Model model){
        model.addAttribute("spitter", new Spitter());
        return "spitters/register";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String addSpitterFromForm(@Valid Spitter spitter, BindingResult bindingResult, Model model){
        if (bindingResult.hasErrors())
            return "spitters/register";

        spitterRepository.addSpitter(spitter);
        return "redirect:/home";
    }

    @RequestMapping(value="/{username}", method = RequestMethod.GET)
    public String showSpitterProfile(@PathVariable String username, Spittle spittle, Model model)
            throws IllegalStateException{

        Spitter spitter = spitterRepository.findByUserName(username);

        model.addAttribute("spitterName", username);
        model.addAttribute("spittles", spittleRepository.getSpittlesForSpitter(spitter));
        model.addAttribute("spittle", new Spittle());
        return "spitters/viewSpitterProfile";
    }

    @RequestMapping(value = "/{username}/follow", method = RequestMethod.POST)
    public String follow(@PathVariable String username){

        User user = (User) SecurityContextHolder.getContext().getAuthentication();
        Spitter spitter = spitterRepository.findByUserName(user.getUsername());
        Spitter followee = spitterRepository.findByUserName(username);

        if(spitterRepository.isUserFollowing(spitter, followee)){
            return "spitters/{username}";
        }

        spitter.addFollowee(followee);
        //spitterRepository.addFollower(spitter, follower);
        return "spitters/{username}";
    }

}
