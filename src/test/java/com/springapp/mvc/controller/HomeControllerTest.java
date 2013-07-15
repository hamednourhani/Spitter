package com.springapp.mvc.controller;


import com.springapp.mvc.model.Spittle;
import com.springapp.mvc.repository.SpitterRepository;
import com.springapp.mvc.repository.SpittleRepository;
import org.junit.Test;
import org.springframework.validation.BindingResult;

import java.util.HashMap;
import java.util.List;

import static java.util.Arrays.asList;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class HomeControllerTest {

    @Test
    public void shouldDisplayRecentSpittles(){
        List<Spittle> expectedSpittles = asList(new Spittle(), new Spittle(), new Spittle());
        Spittle spittle = new Spittle();
        SpittleRepository spittleRepository = mock(SpittleRepository.class);
        SpitterRepository spitterRepository = mock(SpitterRepository.class);
        when(spittleRepository.getRecentSpittles(25)).thenReturn(expectedSpittles);

        HomeController controller = new HomeController(spittleRepository, spitterRepository);
        BindingResult bindingResult = mock(BindingResult.class);
        HashMap<String, Object> model = new HashMap<String, Object>();
        String viewName = controller.showHomePage(spittle, model, bindingResult);

        assertEquals("home", viewName);

        assertSame(expectedSpittles, model.get("spittleList"));
        verify(spittleRepository).getRecentSpittles(25);
    }


}
