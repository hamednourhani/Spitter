package com.springapp.mvc.controller;

import com.springapp.mvc.model.Spittle;
import com.springapp.mvc.repository.SpitterRepository;
import com.springapp.mvc.repository.SpittleRepository;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

public class SpittleControllerTest {

    @Test
    public void postSpittle() throws Exception {
        SpitterRepository spitterRepository = mock(SpitterRepository.class);
        SpittleRepository spittleRepository = mock(SpittleRepository.class);

        SpittleController controller = new SpittleController(spitterRepository, spittleRepository);

        Spittle spittle = new Spittle();
        String result = controller.postSpittle(spittle);

        assertThat(result, is("/home"));
    }
}
