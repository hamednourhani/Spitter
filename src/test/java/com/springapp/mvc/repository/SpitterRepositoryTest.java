package com.springapp.mvc.repository;

import com.springapp.mvc.model.Followee;
import com.springapp.mvc.model.Spitter;
import com.springapp.mvc.model.Spittle;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.transaction.TransactionConfiguration;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertThat;
import static org.junit.matchers.JUnitMatchers.hasItem;


@ContextConfiguration({"classpath:test-context-jpa.xml"})
@TransactionConfiguration(defaultRollback = true)
public class SpitterRepositoryTest extends AbstractTransactionalJUnit4SpringContextTests {

    @Autowired
    SpitterRepository spitterRepository;

    @Autowired
    SpittleRepository spittleRepository;

    @PersistenceContext
    private EntityManager em;

    public SpitterRepositoryTest() {
    }

    @Test
    public void add(){
        Spitter spitter = new Spitter();
        spitter.setFullName("Bill Gates");
        spitter.setPassword("microsoft");
        spitter.setUserName("BillyGates");
        Spitter result = spitterRepository.addSpitter(spitter);
        assertThat(result, is(spitter));
    }

    @Test
    public void find_shouldReturnASpitter(){
        //creating first spitter
        Spitter spitter1 = new Spitter();
        spitter1.setFullName("Bill Gates");
        spitter1.setPassword("microsoft");
        spitter1.setUserName("BillyGates");

        spitterRepository.addSpitter(spitter1);
        Spitter result = spitterRepository.find(spitter1.getId());
        assertThat(result, is(spitter1));
    }

    @Test
    public void findByUserName_ShouldReturnASpitter(){
        //creating first spitter
        Spitter spitter1 = new Spitter();
        spitter1.setFullName("Bill Gates");
        spitter1.setPassword("microsoft");
        spitter1.setUserName("BillyGates");

        //creating second spitter
        Spitter spitter2 = new Spitter();
        spitter2.setFullName("Steve Jobs");
        spitter2.setUserName("SteveJobs");
        spitter2.setPassword("apple1");

        spitterRepository.addSpitter(spitter1);
        spitterRepository.addSpitter(spitter2);
        Spitter result = spitterRepository.findByUserName("BillyGates");
        assertThat(result, is(spitter1));
    }

    @Test
    public void getFollowers()throws Exception{
        Spitter spitter1 = new Spitter();
        spitter1.setFullName("Bill Gates");
        spitter1.setPassword("microsoft");
        spitter1.setUserName("BillyGates");
        spitter1.setEmail("bill@microsoft.com");

        Spitter spitter2 = new Spitter();
        spitter2.setFullName("Steve Jobs");
        spitter2.setUserName("SteveJobs");
        spitter2.setPassword("apple1");
        //spitter2.setEmail("steve@apple.com");

        Spitter spitter3 = new Spitter();
        spitter3.setFullName("Mark Twain");
        spitter3.setUserName("MarkTwain");
        spitter3.setPassword("tomsawyer");
        spitter3.setEmail("mark@yahoo.com");

        persist(spitter1);
        persist(spitter2);
        persist(spitter3);

        spitterRepository.addFollowee(spitter2, spitter1);
        spitterRepository.addFollowee(spitter3, spitter1);
        List<Spitter> spitterList = spitterRepository.getFollowers(spitter1);

        assertThat(spitterList, hasItem(spitter2));
    }

    @Test
    public void getFollowees()throws Exception{
        Spitter spitter1 = new Spitter();
        spitter1.setFullName("Bill Gates");
        spitter1.setPassword("microsoft");
        spitter1.setUserName("BillyGates");
        spitter1.setEmail("bill@microsoft.com");

        Spitter spitter2 = new Spitter();
        spitter2.setFullName("Steve Jobs");
        spitter2.setUserName("SteveJobs");
        spitter2.setPassword("apple1");
        spitter2.setEmail("steve@apple.com");

        persist(spitter1);
        persist(spitter2);

        spitterRepository.addFollowee(spitter2, spitter1);
        List<Spitter> spitterList = spitterRepository.getFollowees(spitter2);
        assertThat(spitterList, hasItem(spitter1));
    }

    @Test
    public void isUserFollowing_shouldReturnTrue(){
        Spitter spitter1 = new Spitter();
        spitter1.setFullName("Bill Gates");
        spitter1.setPassword("microsoft");
        spitter1.setUserName("BillyGates");

        Spitter spitter2 = new Spitter();
        spitter2.setFullName("Steve Jobs");
        spitter2.setUserName("SteveJobs");
        spitter2.setPassword("apple1");

        persist(spitter1);
        persist(spitter2);

        Followee follow = new Followee(spitter1, spitter2);
        em.merge(follow);
        em.flush();
        em.clear();
        spitter1.addFollowee(follow);

        boolean result = spitterRepository.isUserFollowing(spitter1, spitter2);
        assertThat(result, is(true));
    }

    @Test
    public void isUserFollowing_shouldReturnFalse(){
        Spitter spitter1 = new Spitter();
        spitter1.setFullName("Bill Gates");
        spitter1.setPassword("microsoft");
        spitter1.setUserName("BillyGates");

        Spitter spitter2 = new Spitter();
        spitter2.setFullName("Steve Jobs");
        spitter2.setUserName("SteveJobs");
        spitter2.setPassword("apple1");

        persist(spitter1);
        persist(spitter2);

        Followee follow = new Followee(spitter1, spitter2);
        em.merge(follow);
        em.flush();
        em.clear();
        spitter1.addFollowee(follow);

        boolean result = spitterRepository.isUserFollowing(spitter2, spitter1);

        assertThat(result, is(false));

    }

    @Test
    public void getSpittlesForFollowees(){

        Spitter spitter1 = new Spitter();
        spitter1.setFullName("Bill Gates");
        spitter1.setPassword("microsoft");
        spitter1.setUserName("BillyGates");

        Spitter spitter2 = new Spitter();
        spitter2.setFullName("Steve Jobs");
        spitter2.setUserName("SteveJobs");
        spitter2.setPassword("apple1");

        Spitter spitter3 = new Spitter();
        spitter3.setFullName("Mark Twain");
        spitter3.setUserName("MarkTwain");
        spitter3.setPassword("tomsawyer");

        Spitter spitter4 = new Spitter();
        spitter4.setFullName("Warren Buffet");
        spitter4.setUserName("MasterInvestor");
        spitter4.setPassword("PlusInsideTrader");

        Spittle spittle = new Spittle(spitter2, "Spitter 2-1");
        Spittle spittle1 = new Spittle(spitter2, "Spitter 2-2");
        Spittle spittle2 = new Spittle(spitter1, "Spitter 1");
        Spittle spittle3 = new Spittle(spitter3, "Spitter 3-1");
        Spittle spittle4 = new Spittle(spitter4, "Spitter 4-1");

        spitter2.addSpittle(spittle);
        spitter2.addSpittle(spittle1);
        spitter1.addSpittle(spittle2);
        spitter3.addSpittle(spittle3);
        spitter4.addSpittle(spittle4);

        persist(spitter1);
        persist(spitter2);
        persist(spitter3);
        persist(spitter4);

        spitterRepository.addFollowee(spitter1, spitter2);
        spitterRepository.addFollowee(spitter1, spitter3);
        Spitter s = em.find(Spitter.class, spitter2.getId());
        List<Spittle> spittleList = spitterRepository.getFolloweeSpittlesForSpitter(spitter1);

        assertThat(spittleList, hasItem(spittle));
        assertThat(spittleList, hasItem(spittle1));
        assertThat(spittleList, hasItem(spittle3));
        assertThat(spittleList, not(hasItem(spittle2)));
        assertThat(spittleList, not(hasItem(spittle4)));
    }

    @Test
    public void retrieveFolloweeRepository() throws Exception {
        Spitter spitter1 = new Spitter();
        spitter1.setFullName("Bill Gates");
        spitter1.setPassword("microsoft");
        spitter1.setUserName("BillyGates");
        spitter1.setEmail("bill@microsoft.com");

        Spitter spitter2 = new Spitter();
        spitter2.setFullName("Steve Jobs");
        spitter2.setUserName("SteveJobs");
        spitter2.setPassword("apple1");
        spitter2.setEmail("steve@apple.com");

        Spitter spitter3 = new Spitter();
        spitter3.setFullName("Mark Twain");
        spitter3.setUserName("MarkTwain");
        spitter3.setPassword("tomsawyer");
        spitter3.setEmail("mark@yahoo.com");

        persist(spitter1);
        persist(spitter2);
        persist(spitter3);

        Followee followee1 = new Followee(spitter1, spitter2);
        Followee followee2 = new Followee(spitter1, spitter3);

        em.merge(followee1);
        em.merge(followee2);
        em.flush();
        em.clear();

        String hql = "SELECT f FROM Followee f";
        TypedQuery<Followee> query = em.createQuery(hql, Followee.class);
        List<Followee> followeeList = query.getResultList();

        assertThat(followeeList, hasItem(followee1));
        assertThat(followeeList, hasItem(followee2));

    }

    private void persist(Spitter spitter){
        em.persist(spitter);
        em.flush();
        em.clear();
    }

}
