package com.pl.skijumping.domain.service;

import com.pl.skijumping.domain.dto.SkiJumperDTO;
import com.pl.skijumping.domain.entity.SkiJumper;
import com.pl.skijumping.domain.mapper.SkiJumperMapper;
import com.pl.skijumping.domain.repository.SkiJumperRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SkiJumperService {
    private static final String CANT_SAVE_NULL_OBJECT = "Cannot save null object!";
    private final SkiJumperRepository skiJumperRepository;
    private final SkiJumperMapper skiJumperMapper;

    public SkiJumperService(SkiJumperRepository skiJumperRepository, SkiJumperMapper skiJumperMapper) {
        this.skiJumperRepository = skiJumperRepository;
        this.skiJumperMapper = skiJumperMapper;
    }

    public SkiJumperDTO save(SkiJumperDTO skiJumperDTO) {
        if (skiJumperDTO == null) {
            throw new IllegalStateException(CANT_SAVE_NULL_OBJECT);
        }

        SkiJumper skiJumper = skiJumperRepository.save(skiJumperMapper.fromDTO(skiJumperDTO));
        return skiJumperMapper.toDTO(skiJumper);
    }

    public Optional<List<SkiJumperDTO>> findAll() {
        List<SkiJumperDTO> skiJumperDTOS = skiJumperMapper.toDTO(skiJumperRepository.findAll());
        if (skiJumperDTOS.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(skiJumperDTOS);
    }

    public Optional<SkiJumperDTO> findByRank(int rank) {
        SkiJumperDTO skiJumperDTO = skiJumperMapper.toDTO(skiJumperRepository.findByRank(rank));
        if (skiJumperDTO == null) {
            return Optional.empty();
        }
        return Optional.of(skiJumperDTO);
    }

    public Optional<List<SkiJumperDTO>> findByName(String name) {
        List<SkiJumperDTO> skiJumperDTOList = skiJumperMapper.toDTO(skiJumperRepository.findAllByName(name));
        if (skiJumperDTOList.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(skiJumperDTOList);
    }


    public Boolean deleteByRank(int rank) {
        try {
            skiJumperRepository.deleteAllByRank(rank);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Boolean deleteAll() {
        try {
            skiJumperRepository.deleteAll();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public long getJumpersCount() {
        return skiJumperRepository.count();
    }

    public void update(SkiJumperDTO skiJumperDTO) {
        if(skiJumperDTO != null) {
            skiJumperRepository.save(skiJumperMapper.fromDTO(skiJumperDTO));
        }
    }


//    public int getFisCode() {
////        List<Integer> fisCodeList = skiJumperRepository.getFisCodeList();
//        Random randomValue = new Random();
//        boolean value = true;
//        int fis_code;
//
//        do {
//            fis_code = randomValue.nextInt(9900) + 999;
//            if (!fisCodeList.contains(fis_code)) value = false;
//        } while (value);
//
//        return fis_code;
//    }


//    public Optional<List<SkiJumperDTO>> getJumpersByPattenr(String pattern) {
//        List<SkiJumperDTO> skiJumperDTOList = skiJumperMapper.toDTO(skiJumperRepository.getJumpersByPattenr(pattern));
//        if(skiJumperDTOList.isEmpty()) {
//            return Optional.empty();
//        }
//        return Optional.of(skiJumperDTOList);
//    }


}
