package com.springapp.mvc.repository;

import com.springapp.mvc.model.Followee;
import com.springapp.mvc.model.Follower;
import com.springapp.mvc.model.Spitter;
import com.springapp.mvc.model.Spittle;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;


@Repository
public class SpitterRepositoryImpl implements SpitterRepository {

    @PersistenceContext
    private EntityManager em;

    public SpitterRepositoryImpl() {
    }

    public Spitter addSpitter(Spitter spitter){
        em.persist(spitter);
        em.flush();
        em.clear();
        return spitter;
    }

    public void update(Spitter spitter){
        em.merge(spitter);
        em.flush();
        em.clear();
    }

    public void merge(Followee followee){
        em.merge(followee);
        em.flush();
        em.clear();
    }

    public void addFollowee(Spitter spitter, Spitter followee){
        Spitter s = findByUserName(spitter.getUserName());
        s.addFollowee(followee);
        update(s);
    }

    public Spitter find(int id){
        return em.find(Spitter.class, id);
    }

    public Spitter findByUserName(String userName){
        String hql = "SELECT s FROM Spitter s where s.userName = :username";
        TypedQuery <Spitter> query = em.createQuery(hql, Spitter.class).setParameter("username", userName);
        return query.getSingleResult();
    }

    @Override
    public List<Spitter> getFollowers(Spitter followee) {
        String hql = "SELECT f.spitter FROM Followee f WHERE f.followee.userName = :followeeUserName";
        TypedQuery<Spitter> query = em.createQuery(hql, Spitter.class)
                .setParameter("followeeUserName", followee.getUserName());
        return query.getResultList();
    }

    @Override
    public List<Spitter> getFollowees(Spitter spitter) {
        String hql = "SELECT f.followee FROM Followee f WHERE f.spitter.userName = :spitterUserName";
        TypedQuery<Spitter> query = em.createQuery(hql, Spitter.class)
                .setParameter("spitterUserName", spitter.getUserName());
        return query.getResultList();
    }

    @Override
    public boolean isUserFollowing(Spitter spitter, Spitter followee){
        String hql = "SELECT f from Followee f WHERE f.spitter.userName = :spitterUserName" +
                " AND f.followee.userName = :followeeUserName ";
        TypedQuery<Followee> query = em.createQuery(hql, Followee.class)
                .setParameter("followeeUserName", followee.getUserName())
                .setParameter("spitterUserName", spitter.getUserName());
        try{
        query.getSingleResult();
        }catch(NoResultException e){
            return false;
        }
        return true;
    }

    @Override
    public List<Spittle> getFolloweeSpittlesForSpitter(Spitter spitter){
        String hql = "SELECT s FROM Spittle s, Followee f " +
                "WHERE f.spitter.userName = :spitterUserName " +
                "AND s.spitter = f.followee";

        TypedQuery<Spittle> query = em.createQuery(hql, Spittle.class)
                .setParameter("spitterUserName", spitter.getUserName());
        return query.getResultList();
    }

}
