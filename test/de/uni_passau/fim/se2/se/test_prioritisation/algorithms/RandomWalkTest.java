package de.uni_passau.fim.se2.se.test_prioritisation.algorithms;

import de.uni_passau.fim.se2.se.test_prioritisation.encodings.EncodingGenerator;
import de.uni_passau.fim.se2.se.test_prioritisation.encodings.TestOrder;
import de.uni_passau.fim.se2.se.test_prioritisation.fitness_functions.APLC;
import de.uni_passau.fim.se2.se.test_prioritisation.mutations.Mutation;
import de.uni_passau.fim.se2.se.test_prioritisation.stopping_conditions.StoppingCondition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class RandomWalkTest {
    private Random mockRandom;
    private Mutation<TestOrder> mockMutation;
    private APLC mockFitnessfunction;
    private EncodingGenerator mockEncodingGenerator;
    private StoppingCondition mockStoppingCondition;
    @BeforeEach
    void setUp() {
        // Mock the Mutation object as it's a dependency for TestOrder's constructor.
        // We don't care about its behavior for this test.
        mockMutation = (Mutation<TestOrder>) mock(Mutation.class);
        mockRandom = mock(Random.class);
        mockFitnessfunction = mock(APLC.class);
        mockEncodingGenerator = mock(EncodingGenerator.class);
        mockStoppingCondition = mock(StoppingCondition.class);
    }
    //helping Gemini
    private TestOrder mockTestOrder(int[] positions) {
        // Use Mockito's mock creation to allow stubbing of non-final methods like mutate()
        TestOrder mockOrder = mock(TestOrder.class);
        when(mockOrder.getPositions()).thenReturn(positions);
        when(mockOrder.size()).thenReturn(positions.length);
        when(mockOrder.getMutation()).thenReturn(mockMutation);
        when(mockOrder.self()).thenReturn(mockOrder);

        return mockOrder;
    }
    @Test
    public void randomWalkShouldReturnCorrectCandidate(){
        TestOrder testOrder1 = mockTestOrder(new int []{0,1,4,3});
        TestOrder testOrder2 = mockTestOrder(new int []{1,0,4,3});
        TestOrder testOrder3 = mockTestOrder(new int []{4,1,0,3});
        TestOrder testOrder4 = mockTestOrder(new int []{3,4,1,0});
        when(mockStoppingCondition.searchMustStop()).thenReturn(false,false,false,true);
        when(mockEncodingGenerator.get()).thenReturn(testOrder1);
        when(testOrder1.mutate()).thenReturn(testOrder2);
        when(testOrder2.mutate()).thenReturn(testOrder3);
        when(testOrder3.mutate()).thenReturn(testOrder4);
        when(mockFitnessfunction.maximise(testOrder1)).thenReturn(0.6);
        when(mockFitnessfunction.maximise(testOrder2)).thenReturn(0.4);
        when(mockFitnessfunction.maximise(testOrder3)).thenReturn(0.8);
        RandomWalk<TestOrder> randomWalk = new RandomWalk<>(mockStoppingCondition, mockEncodingGenerator, mockFitnessfunction);
        assertSame(testOrder3, randomWalk.findSolution());
    }
    @Test
    public void randomWalkShouldReturnStoppingCondition(){
        RandomWalk<TestOrder> randomWalk = new RandomWalk<>(mockStoppingCondition, mockEncodingGenerator, mockFitnessfunction);
        StoppingCondition stoppingCondition = randomWalk.getStoppingCondition();
        assertSame(mockStoppingCondition, stoppingCondition);
    }
}