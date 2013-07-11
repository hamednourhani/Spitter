package com.springapp.mvc.repository;

import com.springapp.mvc.model.Spitter;
import com.springapp.mvc.model.Spittle;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;


@Repository
public class SpittleRepositoryImpl implements SpittleRepository {

    @PersistenceContext
    private EntityManager em;

    public SpittleRepositoryImpl() {
    }

    public Spittle add(Spittle spittle){
        em.persist(spittle);
        em.flush();
        em.clear();
       return spittle;
    }

    public Spittle find(int id){
        return em.find(Spittle.class, id);
    }

    public List<Spittle> getRecentSpittles(int retrieveNumOfSpittles){
        String hql = "SELECT s FROM Spittle s ORDER BY s.id DESC";
        TypedQuery<Spittle> query = em.createQuery(hql, Spittle.class);
        query.setMaxResults(retrieveNumOfSpittles);
        return query.getResultList();
    }

    @Override
    public List<Spittle> getSpittlesForSpitter(Spitter spitter) {
        String hql = "SELECT s FROM Spittle s WHERE s.spitter.id = :id ORDER BY s.id DESC";
        TypedQuery<Spittle> query = em.createQuery(hql, Spittle.class).setParameter("id", spitter.getId());
        return query.getResultList();
    }

}
