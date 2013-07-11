package com.springapp.mvc.repository;


import com.springapp.mvc.model.Spitter;
import com.springapp.mvc.model.Spittle;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.matchers.JUnitMatchers.hasItem;
import static org.junit.matchers.JUnitMatchers.hasItems;

@ContextConfiguration({"classpath:test-context-jpa.xml"})
@TransactionConfiguration (defaultRollback = true)
public class SpittleRepositoryTest extends AbstractTransactionalJUnit4SpringContextTests{

    @Autowired
    SpittleRepository spittleRepository;

    @PersistenceContext
    EntityManager em;

    @Test
    public void add(){
        Spitter spitter = new Spitter();
        spitter.setFullName("Bill Gates");
        spitter.setPassword("microsoft");
        spitter.setUserName("BillyGates");
        spitter.setId(1);  //Using user set ID

        Spittle spittle = new Spittle(spitter, "Hello");
        Spittle result = spittleRepository.add(spittle);

        assertThat(result, is(spittle));
    }


    @Test
    public void find_shouldReturnSpittle1(){
        //creating first spitter/spittle
        Spitter spitter1 = new Spitter();
        spitter1.setFullName("Bill Gates");
        spitter1.setPassword("microsoft");
        spitter1.setUserName("BillyGates");

        em.persist(spitter1);
        em.flush();
        em.clear();

        Spittle spittle1 = new Spittle(spitter1, "Hello I'm Spitter1");

        spittleRepository.add(spittle1);
        spitter1.addSpittle(spittle1);
        Spittle result = spittleRepository.find(spittle1.getId());

        assertThat(result, is(spittle1));
    }

    @Test
    public void getRecentSpittles_shouldReturnInDescOrder(){
        //creating first spitter/spittle
        Spitter spitter1 = new Spitter();
        spitter1.setFullName("Bill Gates");
        spitter1.setPassword("microsoft");
        spitter1.setUserName("BillyGates");
        Spittle spittle1 = new Spittle(spitter1, "Hello I'm Spitter1");

        //creating second spitter/spittle
        Spitter spitter2 = new Spitter();
        spitter2.setFullName("Steve Jobs");
        spitter2.setUserName("SteveJobs");
        spitter2.setPassword("apple1");
        Spittle spittle2 = new Spittle(spitter2, "Hello I'm Spitter2");

        //creating second spitter/spittle
        Spitter spitter3 = new Spitter();
        spitter3.setFullName("Steve Jobs");
        spitter3.setUserName("SteveJobs");
        spitter3.setPassword("apple1");
        Spittle spittle3 = new Spittle(spitter3, "Hello I'm Spitter3");

        //Adding all spitters
        em.persist(spitter1);
        em.persist(spitter2);
        em.persist(spitter3);
        em.flush();
        em.clear();

        //adding all spittles
        spittleRepository.add(spittle1);
        spittleRepository.add(spittle2);
        spittleRepository.add(spittle3);

        spitter1.addSpittle(spittle1);
        spitter2.addSpittle(spittle2);
        spitter1.addSpittle(spittle3);

        List<Spittle> spittleList = spittleRepository.getRecentSpittles(25);

        assertThat(spittleList, hasItem(spittle1));

    }

    @Test
    public void getSpittlesForSpitter_shouldReturnAllSpittlesFromSpitter(){
        //creating first spitter/spittle
        Spitter spitter1 = new Spitter();
        spitter1.setFullName("Bill Gates");
        spitter1.setPassword("microsoft");
        spitter1.setUserName("BillyGates");
        Spittle spittle1 = new Spittle(spitter1, "Hello I'm Spitter1 with spittle1");

        //creating second spitter/spittle
        Spitter spitter2 = new Spitter();
        spitter2.setFullName("Steve Jobs");
        spitter2.setUserName("SteveJobs");
        spitter2.setPassword("apple1");
        Spittle spittle2 = new Spittle(spitter2, "Hello I'm Spitter2 with spittle2");
        Spittle spittle3 = new Spittle(spitter2, "Hello I'm Spitter2 with spittle3");
        Spittle spittle4 = new Spittle(spitter2, "Hello I'm Spitter2 with spittle4");

        //Adding all spitters
        em.persist(spitter1);
        em.persist(spitter2);
        em.flush();
        em.clear();

        //adding all spittles
        spittleRepository.add(spittle1);
        spittleRepository.add(spittle2);
        spittleRepository.add(spittle3);
        spittleRepository.add(spittle4);

        List<Spittle> result = spittleRepository.getSpittlesForSpitter(spitter2);

        assertThat(result, hasItems(spittle2, spittle3, spittle4));
    }

}
