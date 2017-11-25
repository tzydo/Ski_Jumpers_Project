package com.pl.ski_jumping.dao;

import com.pl.ski_jumping.model.Country;
import com.pl.ski_jumping.model.SkiJumper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.math.BigInteger;
import java.util.List;


@Repository
@Transactional
public class SkiJumpDaoImp implements SkiJumperDao {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public List<SkiJumper> findAll() {
        return (List<SkiJumper>) entityManager.createQuery("from SkiJumper").getResultList();
    }

    @Override
    public SkiJumper findByRank(int rank) {
        return (SkiJumper) entityManager.createQuery("from SkiJumper where id = :rank")
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
    public List<SkiJumper> getJumperByPattern(String pattern) {
        String querry = "from SkiJumper where "+pattern;
        System.out.println(querry);
        return (List<SkiJumper>) entityManager.createQuery(querry).getResultList();
    }

    @Override
    public void save(SkiJumper skiJumper) {
        entityManager.persist(skiJumper);
    }

    public void update(SkiJumper skiJumper) {
        entityManager.merge(skiJumper);
    }

    @Transactional
    public void deleteByRank(int rank) {
        try {
            SkiJumper skiJumper = entityManager.find(SkiJumper.class, rank);
            System.out.println(skiJumper.toString());
            entityManager.remove(skiJumper);
            System.out.println("delete");
        }catch (NullPointerException ex) {
            System.out.println("error");
        }
    }

    @Transactional
    public void deleteAll() {
        entityManager.createNativeQuery("truncate table jumper").executeUpdate();
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

    @Override
    public List<Country> getCountries() {
        return (List<Country>) entityManager.createQuery("from Country").getResultList();
    }

    @Override
    public List<Country> getCountriesByPattern(String pattern) {
        String querry = "from Country where name LIKE :pattern";
        return (List<Country>) entityManager.createQuery(querry)
                .setParameter("pattern", "%" + pattern + "%")
                .getResultList();
    }
}
