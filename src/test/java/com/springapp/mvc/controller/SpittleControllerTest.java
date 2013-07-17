package com.springapp.mvc.controller;

import com.springapp.mvc.model.Spitter;
import com.springapp.mvc.model.Spittle;
import com.springapp.mvc.repository.SpitterRepository;
import com.springapp.mvc.repository.SpittleRepository;
import org.junit.Test;
import org.springframework.ui.Model;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

public class SpittleControllerTest {

    @Test
    public void getRecentSpittles() throws Exception {
        SpitterRepository spitterRepository = mock(SpitterRepository.class);
        SpittleRepository spittleRepository = mock(SpittleRepository.class);

        SpittleController controller = new SpittleController(spitterRepository, spittleRepository);

        Model model = mock(Model.class);
        Spitter spitter = new Spitter();
        String result = controller.getRecentSpittles(model);

        assertThat(result, is("home"));
    }

    @Test
    public void postSpittle() throws Exception {
        SpitterRepository spitterRepository = mock(SpitterRepository.class);
        SpittleRepository spittleRepository = mock(SpittleRepository.class);

        SpittleController controller = new SpittleController(spitterRepository, spittleRepository);

        Spittle spittle = new Spittle();
        String result = controller.postSpittle(spittle);

        assertThat(result, is("redirect:home"));
    }
}
