package de.uni_passau.fim.se2.se.test_prioritisation.algorithms;
import de.uni_passau.fim.se2.se.test_prioritisation.encodings.EncodingGenerator;
import de.uni_passau.fim.se2.se.test_prioritisation.encodings.TestOrder;
import de.uni_passau.fim.se2.se.test_prioritisation.fitness_functions.APLC;
import de.uni_passau.fim.se2.se.test_prioritisation.mutations.Mutation;
import de.uni_passau.fim.se2.se.test_prioritisation.stopping_conditions.MaxFitnessEvaluations;
import de.uni_passau.fim.se2.se.test_prioritisation.stopping_conditions.StoppingCondition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


class RandomSearchTest {
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
    @Test
    public void randomSearchShouldReturnCorrectCandidate(){
        TestOrder testOrder1 = new TestOrder(mockMutation, new int []{0,1});
        TestOrder testOrder2 = new TestOrder(mockMutation, new int []{1,0});
        when(mockEncodingGenerator.get()).thenReturn(testOrder1, testOrder2);
        when(mockFitnessfunction.maximise(testOrder1)).thenReturn(0.5);
        when(mockFitnessfunction.maximise(testOrder2)).thenReturn(0.9);
        when(mockStoppingCondition.searchMustStop()).thenReturn(false, false, true);
        RandomSearch<TestOrder> randomSearch = new RandomSearch<TestOrder>(mockStoppingCondition, mockEncodingGenerator, mockFitnessfunction);
        assertSame(testOrder2, randomSearch.findSolution());
    }
    @Test
    public void randomSearchShouldReturnCorrectCandidate02(){
        TestOrder testOrder1 = new TestOrder(mockMutation, new int []{0,1,2});
        TestOrder testOrder2 = new TestOrder(mockMutation, new int []{1,0,2});
        TestOrder testOrder3 = new TestOrder(mockMutation, new int []{1,0,2});
        TestOrder testOrder4 = new TestOrder(mockMutation, new int []{2,0,1});
        when(mockEncodingGenerator.get()).thenReturn(testOrder1, testOrder2, testOrder3, testOrder4);
        when(mockFitnessfunction.maximise(testOrder1)).thenReturn(0.5);
        when(mockFitnessfunction.maximise(testOrder2)).thenReturn(0.9);
        when(mockFitnessfunction.maximise(testOrder3)).thenReturn(0.4);
        when(mockFitnessfunction.maximise(testOrder4)).thenReturn(0.8);
        when(mockStoppingCondition.searchMustStop()).thenReturn(false, false,false,false, true);
        RandomSearch<TestOrder> randomSearch = new RandomSearch<TestOrder>(mockStoppingCondition, mockEncodingGenerator, mockFitnessfunction);
        assertSame(testOrder2, randomSearch.findSolution());
    }
    @Test
    public void zeroBudgetShouldReturnNull(){
        when(mockStoppingCondition.searchMustStop()).thenReturn(true);
        RandomSearch<TestOrder> randomSearch = new RandomSearch<TestOrder>(mockStoppingCondition, mockEncodingGenerator, mockFitnessfunction);
        assertNull(randomSearch.findSolution());
    }
    @Test
    public void randomSearchShouldReturnStoppingCondition(){
        RandomSearch<TestOrder> randomSearch = new RandomSearch<TestOrder>(mockStoppingCondition, mockEncodingGenerator, mockFitnessfunction);
        StoppingCondition stoppingCondition = randomSearch.getStoppingCondition();
        assertSame(mockStoppingCondition, stoppingCondition);
    }


}