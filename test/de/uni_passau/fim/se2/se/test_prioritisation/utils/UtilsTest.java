package de.uni_passau.fim.se2.se.test_prioritisation.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UtilsTest {

    @Test
    public void sixTestcaseShouldReturnFiveDegreeOfFreedom(){
        var degreeOfFreedom = Utils.degreesOfFreedom(6);
        assertEquals(5, degreeOfFreedom);
    }
    @Test
    public void oneTestcaseShouldReturnZeroDegreeOfFreedom(){
        var degreeOfFreedom = Utils.degreesOfFreedom(1);
        assertEquals(0, degreeOfFreedom);
    }
    @Test
    public void zeroTestcaseShouldReturnZeroDegreeOfFreedom(){
        var degreeOfFreedom = Utils.degreesOfFreedom(0);
        assertEquals(0, degreeOfFreedom);
    }



}