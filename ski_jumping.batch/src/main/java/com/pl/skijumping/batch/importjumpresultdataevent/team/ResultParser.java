package com.pl.skijumping.batch.importjumpresultdataevent.team;

import com.pl.skijumping.batch.importjumpresultdataevent.NameConverter;
import com.pl.skijumping.batch.matchingword.MatchingWords;
import com.pl.skijumping.batch.util.BasicDataParser;
import com.pl.skijumping.batch.util.ValueChecker;
import com.pl.skijumping.dto.JumpResultDTO;

import java.util.Iterator;
import java.util.Set;

class ResultParser {
    private ResultParser() {
//
    }

    static JumpResultDTO parseJumpResult(MatchingWords matchingWords, String template) {
        Set<String> jumpResultPoints = matchingWords.getJumpResultPoints(template);
        Iterator<String> iterator = jumpResultPoints.iterator();

        return new JumpResultDTO()
                .rank(ValueChecker.isNull(BasicDataParser.toInt(matchingWords.getTeamRankId(template)), "Rank cannot be null!"))
                .firstJump(ValueChecker.isNull(getValueFromList(iterator), "Not found first jump size"))
                .pointsForFirstJump(ValueChecker.isNull(getValueFromList(iterator), "Not found first jump points"))
                .secondJump(ValueChecker.isNull(getValueFromList(iterator), "Not found second jump size"))
                .pointsForSecondJump(ValueChecker.isNull(getValueFromList(iterator), "Not found second jump points"))
                .fisCodeId(ValueChecker.isNull(BasicDataParser.toInt(matchingWords.getTeamFisCodeId(template)), "Not found fis code id"))
                .competitorId(ValueChecker.isNull(matchingWords.getJumpResultCompetitorId(template), "Not found competitor id"))
                .jumperName(ValueChecker.isNull(NameConverter.convert(matchingWords.getJumpResultJumperName(template)), "Cannot find jumper name"));
    }

    private static Double getValueFromList(Iterator<String> iterator) {
        if (iterator == null) {
            return null;
        }

        if (iterator.hasNext()) {
            return BasicDataParser.toDouble(iterator.next());
        } else {
            return null;
        }
    }
}
