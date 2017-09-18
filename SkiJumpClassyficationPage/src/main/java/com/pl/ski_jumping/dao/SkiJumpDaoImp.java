package com.pl.ski_jumping.dao;

import com.pl.ski_jumping.model.SkiJumper;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.math.BigInteger;
import java.util.List;


@Repository
public class SkiJumpDaoImp implements SkiJumperDao {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public List<SkiJumper> findAll() {
        return (List<SkiJumper>) entityManager.createQuery("from SkiJumper").getResultList();
    }

    @Override
    public SkiJumper findByRank(int rank) {
        return (SkiJumper) entityManager.createQuery("from SkiJumper where rank=:rank")
                .setParameter("rank", rank)
                .getSingleResult();
    }

    @Override
    public List<SkiJumper> findByName(String name) {
        return (List<SkiJumper>) entityManager.createQuery("from SkiJumper where name =:name ")
                .setParameter("name", name)
                .getResultList();
    }

    @Override
    public void save(SkiJumper skiJumper) {
        entityManager.persist(skiJumper);
    }

    public void update(SkiJumper skiJumper) {
        entityManager.merge(skiJumper);
    }

    @Override
    public void deleteByRank(int rank) {
        entityManager.remove(findByRank(rank));
    }

    @Override
    public void deleteAll() {
        Query query = entityManager.createNativeQuery("TRUNCATE TABLE SkiJumper");
        query.executeUpdate();
    }

    @Override
    public int getJumpersCount() {
        BigInteger count = (BigInteger) entityManager.createNativeQuery("select COUNT(id) from jumper").getSingleResult();

        int jumpersCount = count.intValue();
        return jumpersCount;
    }

    @Override
    public List<Integer> getFisCodeList() {
        String querry = "select fis_code from SkiJumper";
        return entityManager.createQuery(querry).getResultList();
    }
}
