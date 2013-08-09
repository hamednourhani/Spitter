package com.springapp.mvc.controller;

import com.springapp.mvc.model.Spitter;
import com.springapp.mvc.model.Spittle;
import com.springapp.mvc.repository.SpitterRepository;
import com.springapp.mvc.repository.SpittleRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.inject.Inject;
import javax.validation.Valid;
import java.util.Map;

@Transactional
@Controller
public class HomeController {

    private SpitterRepository spitterRepository;
    private SpittleRepository spittleRepository;

    public HomeController() {
    }

    @Inject
    public HomeController(SpitterRepository spitterRepository, SpittleRepository spittleRepository){
        this.spittleRepository = spittleRepository;
        this.spitterRepository = spitterRepository;
    }

    @RequestMapping(value =  {"/", "/home"}, method = RequestMethod.GET)
    public String showHomePage(Map<String, Object> model) throws IllegalStateException{
        getSpittleList(model);
        model.put("spittle", new Spittle());
        return "home";
    }

    @RequestMapping(value = "/home", method = RequestMethod.POST)
    public String spit(@ModelAttribute("spittle") @Valid Spittle spittle, BindingResult binding, Map<String, Object> model)
            throws IllegalStateException{

        if (binding.hasErrors()){
            getSpittleList(model);
            return "home";
        }

        spittle = getSpittleDetails(spittle);
        model.put("spittle", spittle);
        spittleRepository.add(spittle);
        getSpittleList(model);
        return "home";
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String createSpitterProfile(Model model){
        model.addAttribute("spitter", new Spitter());
        return "/register";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String addSpitterFromForm(@Valid Spitter spitter, BindingResult bindingResult, Map<String, Object> model){
        if (bindingResult.hasErrors())
            return "/register";

        spitterRepository.addSpitter(spitter);
        getSpittleList(model);
        return "home";
    }

    private void getSpittleList(Map<String, Object> model){
        final int DEFAULT_SPITTLES_PER_PAGE = 25;
        model.put("spittleList", spittleRepository.getRecentSpittles(DEFAULT_SPITTLES_PER_PAGE));
    }

    private Spittle getSpittleDetails(Spittle spittle){
        String username;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(principal instanceof UserDetails){
            username = ((UserDetails)principal).getUsername();
        }else{
            username = principal.toString();
        }

        Spitter spitter = spitterRepository.findByUserName(username);
        spittle.setDate();
        spittle.setSpitter(spitter);

        return spittle;
    }
}
