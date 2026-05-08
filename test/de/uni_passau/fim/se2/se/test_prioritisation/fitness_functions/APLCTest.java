package de.uni_passau.fim.se2.se.test_prioritisation.fitness_functions;

import de.uni_passau.fim.se2.se.test_prioritisation.encodings.TestOrder;
import de.uni_passau.fim.se2.se.test_prioritisation.mutations.Mutation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class APLCTest {
    private Mutation<TestOrder> mockMutation;
    private Random mockRandom;
    @BeforeEach
    void setUp() {
        // Mock the Mutation object as it's a dependency for TestOrder's constructor.
        // We don't care about its behavior for this test.
        mockMutation = (Mutation<TestOrder>) mock(Mutation.class);
        mockRandom = mock(Random.class);
    }
    @Test
    public void nullCoverageMatrixShouldReturnException(){
         boolean [][] coverageMatrix = null;
        assertThrows(IllegalArgumentException.class, () -> {
            new APLC(coverageMatrix);
        });

    }
    @Test
    public void emptyCoverageMatrixShouldReturnException() {
        boolean[][] coverageMatrix = new boolean[0][];
        assertThrows(IllegalArgumentException.class, () -> {
            new APLC(coverageMatrix);
        });
    }
    //helping gemini
    @Test
    public void applyShouldReturnCorrectFitnessScore(){
        boolean[][] coverageMatrix = new boolean[][]{{false, true,true},{true, false, false}};
        int [] positions = new int [] {0,1};
        TestOrder testOrder = new TestOrder(mockMutation, positions);
        double actualValue = 7.0 / 12.0;
        APLC fitnessFunction = new APLC(coverageMatrix);
        double fitnessScore = fitnessFunction.applyAsDouble(testOrder);
        assertEquals(actualValue, fitnessScore, 0.00001);
    }
    @Test
    public void maximizeShouldReturnCorrectFitnessScore(){
        boolean[][] coverageMatrix = new boolean[][]{{false,false, true,false},{true, true, false, false},
                {true,true,true,false}, {true,false,true,false}};
        int [] positions = new int [] {0,1,2,3};
        TestOrder testOrder = new TestOrder(mockMutation, positions);
        double actualValue = 17.0 / 24.0;
        APLC fitnessFunction = new APLC(coverageMatrix);
        double fitnessScore = fitnessFunction.maximise(testOrder);
        assertEquals(actualValue, fitnessScore, 0.00001);
    }
    @Test
    public void minimizeShouldReturnCorrectFitnessScore(){
        boolean[][] coverageMatrix = new boolean[][]{{false, true,false},{true, true, false}};
        int [] positions = new int [] {1,0};
        TestOrder testOrder = new TestOrder(mockMutation, positions);
        double actualValue = 1 - (3.0 / 4.0);
        APLC fitnessFunction = new APLC(coverageMatrix);
        double fitnessScore = fitnessFunction.minimise(testOrder);
        assertEquals(actualValue, fitnessScore, 0.00001);
    }
    @Test
    public void nullTestOrderShouldReturnException() {
        boolean[][] coverageMatrix = new boolean[][]{{false, true,false},{true, true, false}};
        assertThrows(NullPointerException.class, () -> {
            new APLC(coverageMatrix).applyAsDouble(null);
        });
    }

    @Test
    public void zeroCoveredLineShouldReturnZero(){
        boolean[][] coverageMatrix = new boolean[][]{{false, false},{false, false}};
        int [] positions = new int [] {1,0};
        TestOrder testOrder = new TestOrder(mockMutation, positions);
        double actualValue = 0;
        APLC fitnessFunction = new APLC(coverageMatrix);
        double fitnessScore = fitnessFunction.maximise(testOrder);
        assertEquals(actualValue, fitnessScore, 0.00001);
    }
    @Test
    public void nullCoverageLineShouldReturnException(){
        boolean[][] coverageMatrix = new boolean[][]{{false, false},null,{false, false}};
        int [] positions = new int [] {1,0,2};
        TestOrder testOrder = new TestOrder(mockMutation, positions);
        assertThrows(NullPointerException.class, () -> {
            new APLC(coverageMatrix).applyAsDouble(null);
        });
    }






}