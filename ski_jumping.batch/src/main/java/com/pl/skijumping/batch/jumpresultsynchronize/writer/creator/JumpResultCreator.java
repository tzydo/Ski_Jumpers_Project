//package com.pl.skijumping.batch.jumpresultsynchronize.writer.creator;
//
//import com.pl.skijumping.dto.JumpResultDTO;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import java.util.List;
//
//public class JumpResultCreator {
//    private static final Logger LOGGER = LoggerFactory.getLogger(JumpResultCreator.class);
//
//    private JumpResultCreator() {
//        //
//    }
//
//    public static JumpResultDTO createJumpResult(List<String> resultDataSecondStepWords) {
//        if (resultDataSecondStepWords == null || resultDataSecondStepWords.isEmpty()) {
//            LOGGER.warn("Cannot create jump result from null values!");
//            return null;
//        }
//
//        JumpResultDTO jumpResultDTO = new JumpResultDTO();
//        jumpResultDTO.setRank(ValueMapper.getIntegerValue(resultDataSecondStepWords, 0));
//        jumpResultDTO.setFirstJump(ValueMapper.getDoubleValue(resultDataSecondStepWords, 5));
//        jumpResultDTO.setPointsForFirstJump(ValueMapper.getDoubleValue(resultDataSecondStepWords, 6));
//        jumpResultDTO.setSecondJump(ValueMapper.getDoubleValue(resultDataSecondStepWords, 7));
//        jumpResultDTO.setPointsForSecondJump(ValueMapper.getDoubleValue(resultDataSecondStepWords, 8));
//        jumpResultDTO.setTotalPoints(ValueMapper.getDoubleValue(resultDataSecondStepWords, 9));
//
//        return jumpResultDTO;
//    }
//}