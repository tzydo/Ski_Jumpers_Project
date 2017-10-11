package com.pl.ski_jumping.model;

import com.pl.ski_jumping.utils.QuerryPattern;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Zyzio on 10.10.2017.
 */
public class QuerryPatternTest {

    @Test
    public void getQuerryTest() {
        assertEquals("correct", QuerryPattern.getQuerry(
                1, 2, 3, "name", "surname", "nationality"),
                "rank = 1 AND bib = 2 AND fis_code = 3 AND name = name AND surname = surname AND nationality = nationality");
    }

    @Test
    public void checkIntTest() {
        assertTrue("value is positive", QuerryPattern.checkInt(3));
        assertFalse("value isn't positive", QuerryPattern.checkInt(-3));
        assertFalse("value isn't positive", QuerryPattern.checkInt(null));
    }

    @Test
    public void checkStringTest() {
        assertTrue("value is correct", QuerryPattern.checkString("ala"));
        assertFalse("value isn't correct", QuerryPattern.checkString(""));
        assertFalse("value isn't correct", QuerryPattern.checkString(" "));
        assertFalse("value isn't correct", QuerryPattern.checkString(null));
    }


}