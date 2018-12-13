//package com.pl.skijumping.batch.jumpresultsynchronize.writer.creator;
//
//import com.pl.skijumping.dto.SkiJumperDTO;
//import com.pl.skijumping.service.SkiJumperService;
//
//import java.util.List;
//
//public class SkiJumperCreator {
//    private final SkiJumperService skiJumperService;
//
//    public SkiJumperCreator(SkiJumperService skiJumperService) {
//        this.skiJumperService = skiJumperService;
//    }
//
//    public SkiJumperDTO createSkiJumper(List<String> jumpResultDataSecondStep, String skiJumperName) {
//        if (jumpResultDataSecondStep.isEmpty()) {
//            return null;
//        }
//
//        SkiJumperDTO skiJumperDTO = findSkiJumperDTOByName(skiJumperName);
//        if (skiJumperDTO != null) {
//            return skiJumperDTO;
//        }
//
//        skiJumperDTO = new SkiJumperDTO()
//                .bib(ValueMapper.getIntegerValue(jumpResultDataSecondStep, 1))
//                .fisCode(ValueMapper.getIntegerValue(jumpResultDataSecondStep, 2))
//                .name(skiJumperName);
//
//        return skiJumperService.save(skiJumperDTO);
//    }
//
//    private SkiJumperDTO findSkiJumperDTOByName(String skiJumperName) {
//        return skiJumperService.findOneByName(skiJumperName).orElse(null);
//    }
//}