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

    @RequestMapping(value="/profile/{username}", method = RequestMethod.GET)
    public String showSpitterProfile(@PathVariable String username, Model model)
            throws IllegalStateException{

        Spitter spitter = spitterRepository.findByUserName(username);
        model.addAttribute("spitterName", username);
        model.addAttribute("spittles", spittleRepository.getSpittlesForSpitter(spitter));
        model.addAttribute("spittle", new Spittle());
        return "spitters/viewSpitterProfile";
    }

    @RequestMapping(value = "/profile/{username}/follow", method = RequestMethod.GET)
    public String follow(@PathVariable String username, Model model){

        Authentication user = SecurityContextHolder.getContext().getAuthentication();
        Spitter spitter = spitterRepository.findByUserName(user.getName());
        Spitter followee = spitterRepository.findByUserName(username);
        final int DEFAULT_SPITTLES_PER_PAGE = 25;
        model.addAttribute("spittleList", spittleRepository.getRecentSpittles(DEFAULT_SPITTLES_PER_PAGE));

        if(spitterRepository.isUserFollowing(spitter, followee)){
            model.addAttribute("followMessage", "You are already following user");
            return "redirect:/spitters/profile/" + username;
        }

        spitterRepository.addFollowee(spitter, followee);
        model.addAttribute("followMessage", "You are now following user");
        return "redirect:/spitters/profile/" + username;
    }

    @RequestMapping(value = "/profile/{username}/viewFollowers", method = RequestMethod.GET)
    public String getFollowers(@PathVariable String username, Model model){
        Spitter spitter = spitterRepository.findByUserName(username);
        model.addAttribute("followersMessage", username + " is following");
        model.addAttribute("spitterName", username);
        model.addAttribute("followerList", spitterRepository.getFollowers(spitter));
        return "/displayFollowers";
    }

    @RequestMapping(value = "/profile/{username}/viewFollowees", method = RequestMethod.GET)
    public String getFollowees(@PathVariable String username, Model model){
        Spitter spitter = spitterRepository.findByUserName(username);
        model.addAttribute("spitterName", username);
        model.addAttribute("followeeList", spitterRepository.getFollowers(spitter));
        return "/displayFollowees";
    }

    @RequestMapping(value = "/isUserFollowing", method = RequestMethod.POST)
    public @ResponseBody String checkIfUserIsFollowing(@RequestParam(value="username", required = true)String username){
        Authentication user = SecurityContextHolder.getContext().getAuthentication();
        Spitter spitter = spitterRepository.findByUserName(user.getName());
        Spitter followee = spitterRepository.findByUserName(username);

        if(spitterRepository.isUserFollowing(spitter, followee)){
            return "You are already following " + username;
        }
        else
            return "You are now following " + username;
    }

}
