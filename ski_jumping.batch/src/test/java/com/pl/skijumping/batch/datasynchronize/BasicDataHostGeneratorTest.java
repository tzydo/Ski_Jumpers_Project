//package com.pl.skijumping.batch.datasynchronize;
//
//import com.pl.skijumping.dto.TournamentYearDTO;
//import com.pl.skijumping.service.TournamentYearService;
//import org.assertj.core.api.Assertions;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.Mock;
//import org.mockito.runners.MockitoJUnitRunner;
//
//import java.util.*;
//
//import static org.mockito.Mockito.when;
//
//@RunWith(MockitoJUnitRunner.class)
//public class BasicDataHostGeneratorTest {
//
//    @Mock
//    private TournamentYearService tournamentYearService;
//
//    @Test
//    public void generateTest() {
//        List<TournamentYearDTO> tournamentYearDTOS = Arrays.asList(new TournamentYearDTO(null, "1"),
//                new TournamentYearDTO(null, "2"),
//                new TournamentYearDTO(null, "3"));
//
//        when(tournamentYearService.findAll()).thenReturn(tournamentYearDTOS);
//        BasicDataHostGenerator basicDataHostGenerator = new BasicDataHostGenerator(tournamentYearService, true, null);
//        Map<String, String> actualGeneratedValue = basicDataHostGenerator.generate("test");
//
//        Map<String, String> expectedGeneratedValue = new HashMap<>();
//        expectedGeneratedValue.put("1", "test1");
//        expectedGeneratedValue.put("2", "test2");
//        expectedGeneratedValue.put("3", "test3");
//        Assertions.assertThat(actualGeneratedValue).containsAllEntriesOf(expectedGeneratedValue);
//    }
//
//    @Test
//    public void generateWithoutDuplicateTest() {
//        List<TournamentYearDTO> tournamentYearDTOS = Arrays.asList(
//                new TournamentYearDTO(null, "1"),
//                new TournamentYearDTO(null, "1"),
//                new TournamentYearDTO(null, "2"),
//                new TournamentYearDTO(null, "2"),
//                new TournamentYearDTO(null, "3"),
//                new TournamentYearDTO(null, "3"));
//
//        when(tournamentYearService.findAll()).thenReturn(tournamentYearDTOS);
//        BasicDataHostGenerator basicDataHostGenerator = new BasicDataHostGenerator(tournamentYearService, true, null);
//        Map<String, String> actualGeneratedValue = basicDataHostGenerator.generate("test");
//
//        Assertions.assertThat(actualGeneratedValue).hasSize(3);
//
//        Map<String, String> expectedGeneratedValue = new HashMap<>();
//        expectedGeneratedValue.put("1", "test1");
//        expectedGeneratedValue.put("2", "test2");
//        expectedGeneratedValue.put("3", "test3");
//        Assertions.assertThat(actualGeneratedValue).containsAllEntriesOf(expectedGeneratedValue);
//    }
//
//
//    @Test
//    public void generateWhenNullTest() {
//        BasicDataHostGenerator basicDataHostGenerator = new BasicDataHostGenerator(tournamentYearService, true, null);
//        Map<String, String> actualGeneratedValue = basicDataHostGenerator.generate(null);
//        Assertions.assertThat(actualGeneratedValue).isEmpty();
//    }
//}