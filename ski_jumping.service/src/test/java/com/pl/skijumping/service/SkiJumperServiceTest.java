//package com.pl.skijumping.domain.service;
//
//import com.pl.skijumping.domain.mapper.SkiJumperMapper;
//import com.pl.skijumping.domain.repository.SkiJumperRepository;
//import com.pl.skijumping.domain.dto.SkiJumperDTO;
//import com.pl.skijumping.domain.entity.SkiJumper;
//import org.assertj.core.api.Assertions;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//
//import javax.transaction.Transactional;
//import java.util.Arrays;
//import java.util.List;
//import java.util.Optional;
//
//import static org.assertj.core.api.Java6Assertions.assertThat;
//
//
//@RunWith(SpringJUnit4ClassRunner.class)
//@ActiveProfiles("test")
//@DataJpaTest
//public class SkiJumperServiceTest {
//
//    private static final int BIB = 1;
//    private static final String NATIONALITY = "nationality";
//    private static final String SURNAME = "surname";
//    private static final String NAME = "name";
//
//    @Autowired
//    private SkiJumperRepository skiJumperRepository;
//
//    @Autowired
//    private SkiJumperMapper skiJumperMapper;
//
//    @Test
//    @Transactional
//    public void saveTest() {
//        SkiJumperDTO skiJumperDTO = getSkiJumperDTO(BIB, NAME, SURNAME, NATIONALITY);
//        SkiJumperService skiJumperService = new SkiJumperService(skiJumperRepository, skiJumperMapper);
//        SkiJumperDTO savedSkiJumperDTO = skiJumperService.save(skiJumperDTO);
//
//        skiJumperDTO.setId(savedSkiJumperDTO.getId());
//        assertThat(savedSkiJumperDTO).isEqualToComparingFieldByField(skiJumperDTO);
//        SkiJumper skiJumperFromDB = skiJumperRepository.getOne(savedSkiJumperDTO.getId());
//        assertThat(savedSkiJumperDTO).isEqualToComparingFieldByField(skiJumperMapper.toDTO(skiJumperFromDB));
//    }
//
//    @Test
//    @Transactional
//    public void findAllTest() {
//        SkiJumperService skiJumperService = new SkiJumperService(skiJumperRepository, skiJumperMapper);
//        List<SkiJumperDTO> skiJumperDTOList = setupSkiJumperDTOList(skiJumperService);
//
//        Optional<List<SkiJumperDTO>> skiJumperListFromDB = skiJumperService.findAll();
//        Assertions.assertThat(skiJumperListFromDB.isPresent()).isTrue();
//        Assertions.assertThat(skiJumperListFromDB.get()).hasSize(skiJumperDTOList.size());
//        Assertions.assertThat(skiJumperListFromDB.get()).isEqualTo(skiJumperDTOList);
//    }
//
//    @Test
//    @Transactional
//    public void findByRankTest() {
//        SkiJumperService skiJumperService = new SkiJumperService(skiJumperRepository, skiJumperMapper);
//        List<SkiJumperDTO> skiJumperDTOList = setupSkiJumperDTOList(skiJumperService);
//
//        Optional<SkiJumperDTO> actualSkiJumperDTO = skiJumperService.findByRank(2);
//        Assertions.assertThat(actualSkiJumperDTO.isPresent()).isTrue();
//        Assertions.assertThat(skiJumperDTOList.contains(actualSkiJumperDTO.get())).isTrue();
//    }
//
//    @Test
//    @Transactional
//    public void findByNameTest() {
//        SkiJumperService skiJumperService = new SkiJumperService(skiJumperRepository, skiJumperMapper);
//        setupSkiJumperDTOList(skiJumperService);
//
//        Optional<List<SkiJumperDTO>> actualSkiJumperDTO = skiJumperService.findByName("name2");
//        Assertions.assertThat(actualSkiJumperDTO.isPresent()).isTrue();
//        Assertions.assertThat(actualSkiJumperDTO.get()).hasSize(1);
//    }
//
//    @Test
//    @Transactional
//    public void deleteByRankTest() {
//        SkiJumperService skiJumperService = new SkiJumperService(skiJumperRepository, skiJumperMapper);
//        setupSkiJumperDTOList(skiJumperService);
//
//        Boolean successfullyDeleted = skiJumperService.deleteByRank(1);
//        Assertions.assertThat(successfullyDeleted).isTrue();
//
//        Optional<List<SkiJumperDTO>> skiJumpersFromDB = skiJumperService.findAll();
//        Assertions.assertThat(skiJumpersFromDB.get()).hasSize(2);
//        Assertions.assertThat(skiJumpersFromDB.get().get(0).getRank()).isNotEqualTo(1);
//        Assertions.assertThat(skiJumpersFromDB.get().get(1).getRank()).isNotEqualTo(1);
//    }
//
//    @Test
//    public void deleteAllTest() {
//        SkiJumperService skiJumperService = new SkiJumperService(skiJumperRepository, skiJumperMapper);
//        setupSkiJumperDTOList(skiJumperService);
//
//        Boolean successfullyDeleted = skiJumperService.deleteAll();
//        Assertions.assertThat(successfullyDeleted).isTrue();
//        Optional<List<SkiJumperDTO>> skiJumpersFromDB = skiJumperService.findAll();
//        Assertions.assertThat(skiJumpersFromDB.isPresent()).isFalse();
//        Assertions.assertThat(skiJumpersFromDB).isEqualTo(Optional.empty());
//    }
//
//    @Test
//    @Transactional
//    public void getJumpersCountTest() {
//        SkiJumperService skiJumperService = new SkiJumperService(skiJumperRepository, skiJumperMapper);
//        setupSkiJumperDTOList(skiJumperService);
//
//        long jumpersCount = skiJumperService.getJumpersCount();
//        Assertions.assertThat(jumpersCount).isEqualTo(3);
//        skiJumperService.deleteByRank(1);
//        jumpersCount = skiJumperService.getJumpersCount();
//        Assertions.assertThat(jumpersCount).isEqualTo(2);
//    }
//
//    @Test
//    @Transactional
//    public void updateTest() {
//        SkiJumperService skiJumperService = new SkiJumperService(skiJumperRepository, skiJumperMapper);
//        List<SkiJumperDTO> skiJumperDTOList = setupSkiJumperDTOList(skiJumperService);
//
//        SkiJumperDTO skiJumperDTO = skiJumperDTOList.get(0);
//        skiJumperDTO.setRank(-1);
//        skiJumperDTO.setName("newName");
//        skiJumperDTO.setNationality("newNationality");
//
//        skiJumperService.update(skiJumperDTO);
//
//        Assertions.assertThat(skiJumperService.findAll().get()).hasSize(3);
//        Assertions.assertThat(skiJumperService.findAll().get()).contains(skiJumperDTO);
//    }
//
//    private List<SkiJumperDTO> setupSkiJumperDTOList(SkiJumperService skiJumperService) {
//        return Arrays.asList(
//                setupSkiJumperDTO(skiJumperService, 1, "name", "surname", "nationality"),
//                setupSkiJumperDTO(skiJumperService, 2, "name2", "surname2", "nationality2"),
//                setupSkiJumperDTO(skiJumperService, 3, "name3", "surname3", "nationality3"));
//    }
//
//    private SkiJumperDTO setupSkiJumperDTO(SkiJumperService skiJumperService,
//                                           int rank, String name, String surname, String nationality) {
//        return skiJumperService.save(getSkiJumperDTO(rank, name, surname, nationality));
//    }
//
//    private SkiJumperDTO getSkiJumperDTO(int rank, String name, String surname, String nationality) {
//        return new SkiJumperDTO()
//                .rank(rank)
//                .name(name)
//                .surname(surname)
//                .nationality(nationality);
//    }
//}