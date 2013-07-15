package com.springapp.mvc.model;

import com.springapp.mvc.repository.SpitterRepository;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.transaction.TransactionConfiguration;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.matchers.JUnitMatchers.hasItem;

@ContextConfiguration({"classpath:test-context-jpa.xml"})
@TransactionConfiguration(defaultRollback = true)
public class SpitterIntegrationTest extends AbstractTransactionalJUnit4SpringContextTests{

    @PersistenceContext
    private EntityManager em;

    @Test
    public void addSpittle(){
        Spitter spitter = new Spitter("username", "email@email.com", "password", "full name");
        Spittle spittle = new Spittle(spitter, "spittle 1");
        spitter.addSpittle(spittle);

        persist(spitter);

        Spitter s = em.find(Spitter.class, spitter.getId());
        assertThat(s.getSpittles(), hasItem(spittle));
    }

    @Test
    public void saveSpitter() throws Exception {
        Spitter spitter = new Spitter();
        spitter.setUserName("BillGates");
        spitter.setPassword("microsoft");
        spitter.setFullName("Bill Gates");
        spitter.setEmail("email@email.com");

        persist(spitter);

        Spitter persistedSpitter = em.find(Spitter.class, spitter.getId());
        assertThat(persistedSpitter.getUserName(), is(spitter.getUserName()));
        assertThat(persistedSpitter.getPassword(), is(spitter.getPassword()));
        assertThat(persistedSpitter.getEmail(), is(spitter.getEmail()));
        assertThat(persistedSpitter.getFullName(), is(spitter.getFullName()));

    }

    @Autowired
    SpitterRepository spitterRepository;


    @Test
    public void addFollowee() throws Exception {
        Spitter spitter1 = new Spitter("username", "email@email.com", "password", "full name");
        Spitter spitter2 = new Spitter();
        spitter2.setUserName("BillGates");
        spitter2.setPassword("microsoft");
        spitter2.setFullName("Bill Gates");

        persist(spitter1);
        persist(spitter2);

        Followee follow = new Followee(spitter1, spitter2);
        spitter1.addFollowee(follow);
        em.merge(spitter1);
        em.flush();
        em.clear();

        Spitter spitter = em.find(Spitter.class, spitter1.getId());

        assertThat(spitter.getFollowees(), hasItem(follow));
    }

    private void persist(Spitter spitter){
        em.persist(spitter);
        em.flush();
        em.clear();
    }


}
