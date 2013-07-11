package com.springapp.mvc.controller;

import com.springapp.mvc.model.Spitter;
import com.springapp.mvc.model.Spittle;
import com.springapp.mvc.repository.SpitterRepository;
import com.springapp.mvc.repository.SpittleRepository;
import com.springapp.mvc.service.SpitterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;

@Transactional
@Controller
public class SpittleController {

    SpitterRepository spitterRepository;
    SpittleRepository spittleRepository;

    public SpittleController() {
    }

    @Inject
    public SpittleController(SpitterRepository spitterRepository, SpittleRepository spittleRepository){
        this.spitterRepository = spitterRepository;
        this.spittleRepository = spittleRepository;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String getRecentSpittles(Model model){
        model.addAttribute("message", "Log in to continue");
        return "home";
    }

    @RequestMapping(value = "/spittles", method = RequestMethod.GET)
    public String listSpittlesForSpitter(@RequestParam("spitter") String username, Model model){
        Spitter spitter = spitterRepository.findByUserName(username);
        model.addAttribute(spitter);
        model.addAttribute(spittleRepository.getSpittlesForSpitter(spitter));
        return "spittles/list";
    }

    @RequestMapping(value="${spitter.userName}", method = RequestMethod.POST)
    public String postSpittle(@ModelAttribute("spittle") Spittle spittle){
        Spitter spitter = new Spitter();
        spittle.setId(spitter.getId());
        spittleRepository.add(spittle);
        return "redirect:home";
    }

}
