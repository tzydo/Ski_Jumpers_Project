//package com.pl.skijumping.batch.datareaderjob.jobs.findracedata.writer;
//
//import com.pl.skijumping.batch.BatchApplicationTest;
//import com.pl.skijumping.domain.entity.CompetitionType;
//import com.pl.skijumping.domain.entity.DataRace;
//import com.pl.skijumping.domain.mapper.DataRaceMapper;
//import com.pl.skijumping.domain.repository.DataRaceRepository;
//import org.assertj.core.api.Assertions;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//
//import javax.transaction.Transactional;
//import java.time.LocalDate;
//
//@RunWith(SpringJUnit4ClassRunner.class)
//@ActiveProfiles("test")
//@SpringBootTest(classes = BatchApplicationTest.class)
//public class FindRaceDataWriterBatchTest {
//
//    @Autowired
//    private DataRaceRepository dataRaceRepository;
//    @Autowired
//    DataRaceMapper dataRaceMapper;
//
//    @Test
//    @Transactional
//    public void writeTest() {
//        DataRace dataRace = DataRace.builder()
//                .raceId(1).city("test").competitionName("competition").competitionType(new CompetitionType())
//                .date(LocalDate.of(2018,3,3)).build();
//
//        dataRaceRepository.save(dataRace);
//        Assertions.assertThat(true).isTrue();
//    }
//}