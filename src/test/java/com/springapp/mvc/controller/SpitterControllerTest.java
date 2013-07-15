package com.springapp.mvc.controller;


import com.springapp.mvc.model.Spitter;
import com.springapp.mvc.model.Spittle;
import com.springapp.mvc.repository.SpitterRepository;
import com.springapp.mvc.repository.SpittleRepository;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static java.util.Arrays.asList;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.matchers.JUnitMatchers.hasItem;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;

@ContextConfiguration({"classpath:test-context-jpa.xml"})
@TransactionConfiguration(defaultRollback = true)
public class SpitterControllerTest extends AbstractTransactionalJUnit4SpringContextTests {

    @Test
    public void listSpittlesForSpitter(){
        Spitter spitter = new Spitter();
        spitter.setFullName("Bill Gates");
        spitter.setPassword("microsoft");
        spitter.setUserName("BillGates");

        Spittle spittle1 = new Spittle(spitter, "Spittle1");
        Spittle spittle2 = new Spittle(spitter, "Spittle2");
        Spittle spittle3 = new Spittle(spitter, "Spittle3");

        SpitterRepository spitterRepository = mock(SpitterRepository.class);
        SpittleRepository spittleRepository = mock(SpittleRepository.class);

        SpittleController spittleController = new SpittleController(spitterRepository, spittleRepository);

        spittleRepository.add(spittle1);
        spittleRepository.add(spittle2);
        spittleRepository.add(spittle3);
        List<Spittle> spittleList = asList(spittle1, spittle2, spittle3);

        Model map = mock(Model.class);

        String result = spittleController.listSpittlesForSpitter(spitter.getUserName(), map);

        when(spittleRepository.getSpittlesForSpitter(spitter)).thenReturn(spittleList);
        assertThat(result, is("spittles/list"));

    }


    @Test
    public void createSpitterProfile() throws Exception {
        SpitterRepository spitterRepository = mock(SpitterRepository.class);
        SpittleRepository spittleRepository = mock(SpittleRepository.class);
        SpitterController controller = new SpitterController(spitterRepository, spittleRepository);

        Model map = mock(Model.class);
        String result = controller.createSpitterProfile(map);

        assertThat(result, is("spitters/register"));
    }

    @Test
    public void addSpitterFromForm() throws Exception {
        SpitterRepository spitterRepository = mock(SpitterRepository.class);
        SpittleRepository spittleRepository = mock(SpittleRepository.class);
        SpitterController controller = new SpitterController(spitterRepository, spittleRepository);

        BindingResult binding = mock(BindingResult.class);

        Spitter spitter = new Spitter();

        Model model = mock(Model.class);

        String result = controller.addSpitterFromForm(spitter, binding, model);

        assertThat(result, is("redirect:/home"));

    }

    @Test
    public void addSpitterFromForm_hasBindingErrors() throws Exception {
        SpitterRepository spitterRepository = mock(SpitterRepository.class);
        SpittleRepository spittleRepository = mock(SpittleRepository.class);
        SpitterController controller = new SpitterController(spitterRepository, spittleRepository);

        BindingResult binding = mock(BindingResult.class);

        when(binding.hasErrors()).thenReturn(true);

        Spitter spitter = new Spitter();
        Model model = mock(Model.class);

        String result = controller.addSpitterFromForm(spitter, binding, model);

        assertThat(result, is("spitters/register"));

    }

    @Test
    public void showSpitterProfile() throws Exception {
        SpitterRepository spitterRepository = mock(SpitterRepository.class);
        SpittleRepository spittleRepository = mock(SpittleRepository.class);
        SpitterController controller = new SpitterController(spitterRepository, spittleRepository);

        Spitter spitter = new Spitter();
        spitter.setFullName("Bill Gates");
        spitter.setPassword("microsoft");
        spitter.setUserName("BillGates");

        Model map = mock(Model.class);
        String result = controller.showSpitterProfile(spitter.getUserName(), new Spittle(), map);

        assertThat(result, is("spitters/viewSpitterProfile"));
    }

    @PersistenceContext
    EntityManager em;

    @Autowired
    SpitterRepository spitterRepository;

    @Test
    public void follow() throws Exception {

        Spitter spitter = new Spitter();
        spitter.setFullName("Bill Gates");
        spitter.setPassword("microsoft");
        spitter.setUserName("BillGates");

        Spitter spitter1 = new Spitter("SteveJobs", null, "apple1", "Steve Jobs");

        SpittleRepository spittleRepository = mock(SpittleRepository.class);
        SpitterController controller = new SpitterController(spitterRepository, spittleRepository);

        spitterRepository.addSpitter(spitter);
        spitterRepository.addSpitter(spitter1);

        Authentication auth = mock(Authentication.class);
        when(auth.getName()).thenReturn(spitter1.getUserName());
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(auth);
        SecurityContextHolder.setContext(securityContext);

        String result = controller.follow(spitter.getUserName());
        Spitter resultingSpitter = em.find(Spitter.class, spitter1.getId());

        assertThat(result, is("spitters/{username}"));
        assertThat(spitterRepository.getFollowees(resultingSpitter), hasItem(spitter));
    }
}
