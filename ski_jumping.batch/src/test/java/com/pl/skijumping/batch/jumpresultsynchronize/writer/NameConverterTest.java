package com.pl.skijumping.batch.jumpresultsynchronize.writer;

import org.assertj.core.api.Assertions;
import org.junit.Test;

public class NameConverterTest {

    @Test
    public void convertTest() {
        String words = "FORFANG Johann Andre";
        String actualName = NameConverter.convert(words);

        String expectedName = "Johann Andre Forfang";
        Assertions.assertThat(actualName).isNotEmpty();
        Assertions.assertThat(actualName).isEqualTo(expectedName);
    }

    @Test
    public void convertWhenFewPartsTest() {
        String words = "fifth first second third fourth";
        String actualName = NameConverter.convert(words);

        String expectedName = "First Second Third Fourth Fifth";
        Assertions.assertThat(actualName).isNotEmpty();
        Assertions.assertThat(actualName).isEqualTo(expectedName);
    }

    @Test
    public void convertWhenNullTest() {
        Assertions.assertThat(NameConverter.convert(null)).isNull();
    }
}