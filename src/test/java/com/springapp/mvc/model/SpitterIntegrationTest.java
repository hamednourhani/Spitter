package com.springapp.mvc.model;

import org.junit.Test;
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

    private void persist(Spitter spitter){
        em.persist(spitter);
        em.flush();
        em.clear();
    }


}
