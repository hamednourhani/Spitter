package com.springapp.mvc.controller;


import com.springapp.mvc.model.Spitter;
import com.springapp.mvc.model.Spittle;
import com.springapp.mvc.repository.SpitterRepository;
import com.springapp.mvc.repository.SpittleRepository;
import org.junit.Test;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Arrays.asList;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertSame;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

public class HomeControllerTest {

    @Test
    public void shouldDisplayRecentSpittles(){
        List<Spittle> expectedSpittles = asList(new Spittle(), new Spittle(), new Spittle());
        Spittle spittle = new Spittle();
        SpittleRepository spittleRepository = mock(SpittleRepository.class);
        SpitterRepository spitterRepository = mock(SpitterRepository.class);
        when(spittleRepository.getRecentSpittles(25)).thenReturn(expectedSpittles);

        HomeController controller = new HomeController(spitterRepository, spittleRepository);
        BindingResult bindingResult = mock(BindingResult.class);
        HashMap<String, Object> model = new HashMap<String, Object>();
        String viewName = controller.showHomePage(model);

        assertEquals("home", viewName);

        assertSame(expectedSpittles, model.get("spittleList"));
        verify(spittleRepository).getRecentSpittles(25);
    }

    @Test
    public void createSpitterProfile() throws Exception {
        SpitterRepository spitterRepository = mock(SpitterRepository.class);
        SpittleRepository spittleRepository = mock(SpittleRepository.class);
        HomeController controller = new HomeController(spitterRepository, spittleRepository);

        Model map = mock(Model.class);
        String result = controller.createSpitterProfile(map);

        assertThat(result, is("spitters/register"));
    }

    @Test
    public void addSpitterFromForm() throws Exception {
        SpitterRepository spitterRepository = mock(SpitterRepository.class);
        SpittleRepository spittleRepository = mock(SpittleRepository.class);
        HomeController controller = new HomeController(spitterRepository, spittleRepository);

        BindingResult binding = mock(BindingResult.class);

        Spitter spitter = new Spitter();

        Map<String, Object> model = mock(Map.class);
        String result = controller.addSpitterFromForm(spitter, binding, model);

        assertThat(result, is("home"));

    }

    @Test
    public void addSpitterFromForm_hasBindingErrors() throws Exception {
        SpitterRepository spitterRepository = mock(SpitterRepository.class);
        SpittleRepository spittleRepository = mock(SpittleRepository.class);
        HomeController controller = new HomeController(spitterRepository, spittleRepository);

        BindingResult binding = mock(BindingResult.class);

        when(binding.hasErrors()).thenReturn(true);

        Spitter spitter = new Spitter();

        Map<String, Object> model = mock(Map.class);
        String result = controller.addSpitterFromForm(spitter, binding, model);

        assertThat(result, is("spitters/register"));

    }

}
