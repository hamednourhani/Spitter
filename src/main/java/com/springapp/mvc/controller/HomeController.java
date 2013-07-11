package com.springapp.mvc.controller;

import com.springapp.mvc.model.Spitter;
import com.springapp.mvc.model.Spittle;
import com.springapp.mvc.service.SpitterService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.RequestAttributes;

import javax.inject.Inject;
import java.util.Map;

@Transactional
@Controller
public class HomeController {

    private SpitterService spitterService;

    public HomeController() {
    }

    @Inject
    public HomeController(SpitterService spitterService){
        this.spitterService = spitterService;
    }

    @RequestMapping(value =  {"/", "/home"}, method = RequestMethod.GET)
    public String showHomePage(Spittle spittle, Map<String, Object> model, BindingResult binding){
        getSpittleList(model);
        model.put("spittle", new Spittle());
        return "home";
    }

    @RequestMapping(value = "/home", method = RequestMethod.POST)
    public String spit(Spittle spittle, Map<String, Object> model, BindingResult binding)
            throws IllegalStateException{
        if (binding.hasErrors())
            return "home";

        spittle = getSpittleDetails(spittle);
        spitterService.saveSpittle(spittle);
        getSpittleList(model);
        return "home";
    }

    private void getSpittleList(Map<String, Object> model){
        final int DEFAULT_SPITTLES_PER_PAGE = 25;
        model.put("spittleList", spitterService.getSpittleList(DEFAULT_SPITTLES_PER_PAGE));
    }

    private Spittle getSpittleDetails(Spittle spittle){
        String username;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(principal instanceof UserDetails){
            username = ((UserDetails)principal).getUsername();
        }else{
            username = principal.toString();
        }

        Spitter spitter = spitterService.getSpitter(username);
        spittle.setDate();
        spittle.setSpitter(spitter);

        return spittle;
    }
}
