package com.pl.skijumping.batch.importjumpresultdataevent.team;

import org.assertj.core.api.Assertions;
import org.junit.Test;

public class JumpResultTeamFinderUtilTest {

    @Test
    public void replaceByPatternTest() {
        String word = "testA testB testC testD testE testA testB testC testD testE testA testB testC testD testE";
        String expectedWord = "testA TEST_00 testE testA TEST_01 testE testA TEST_02 testE";
        String regexpPattern = "testB\\s(.*?)\\stestD";

        String actualWord = JumpResultTeamFinderUtil.replaceByPattern(word, regexpPattern, "TEST_");
        Assertions.assertThat(actualWord).isEqualTo(expectedWord);
    }

    @Test
    public void replaceByPatternWhenNotFoundTest() {
        String word = "testA testZ testC testX testE testA testB testZ testX testE testA testB testZ testX testE";
        String regexpPattern = "testB\\s(.*?)\\stestD";

        String actualWord = JumpResultTeamFinderUtil.replaceByPattern(word, regexpPattern, "TEST_");
        Assertions.assertThat(actualWord).isEqualTo(word);
    }

}