package com.pl.ski_jumping.service;

import com.pl.ski_jumping.dao.SkiJumperDao;
import com.pl.ski_jumping.model.SkiJumper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class SkiJumperServiceImp implements SkiJumperService{

    @Autowired
    SkiJumperDao skiJumperDao;

    @Override
    @Transactional
    public List<SkiJumper> findAll() {
        return skiJumperDao.findAll();
    }

    @Override
    @Transactional
    public SkiJumper findByRank(int rank) {
        return skiJumperDao.findByRank(rank);
    }

    @Override
    @Transactional
    public List<SkiJumper> findByName(String name) {
        return skiJumperDao.findByName(name);
    }

    @Override
    @Transactional
    public void save(SkiJumper skiJumper) {
        skiJumperDao.save(skiJumper);
    }

    @Override
    @Transactional
    public void update(SkiJumper skiJumper) {
        skiJumperDao.update(skiJumper);
    }

    @Override
    @Transactional
    public void deleteByRank(int rank) {
        skiJumperDao.deleteByRank(rank);
    }

    @Override
    @Transactional
    public void deleteAll() {
        skiJumperDao.deleteAll();
    }
}
