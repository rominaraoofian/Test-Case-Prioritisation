package de.uni_passau.fim.se2.se.test_prioritisation.algorithms;

import de.uni_passau.fim.se2.se.test_prioritisation.crossover.Crossover;
import de.uni_passau.fim.se2.se.test_prioritisation.encodings.EncodingGenerator;
import de.uni_passau.fim.se2.se.test_prioritisation.encodings.TestOrder;
import de.uni_passau.fim.se2.se.test_prioritisation.fitness_functions.APLC;
import de.uni_passau.fim.se2.se.test_prioritisation.mutations.Mutation;
import de.uni_passau.fim.se2.se.test_prioritisation.parent_selection.ParentSelection;
import de.uni_passau.fim.se2.se.test_prioritisation.stopping_conditions.StoppingCondition;
import static org.mockito.ArgumentMatchers.any;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class SimpleGeneticAlgorithmTest {
    private Random mockRandom;
    private Mutation<TestOrder> mockMutation;
    private APLC mockFitnessScore;
    private EncodingGenerator mockEncodingGenerator;
    private StoppingCondition mockStoppingCondition;
    private Crossover mockCrossOver;
    private ParentSelection mockParentSelection;


    private TestOrder mockTestOrder(int[] positions) {
        TestOrder mockOrder = mock(TestOrder.class);
        when(mockOrder.getPositions()).thenReturn(positions);
        when(mockOrder.size()).thenReturn(positions.length);
        when(mockOrder.getMutation()).thenReturn(mockMutation);
        when(mockOrder.self()).thenReturn(mockOrder);
        return mockOrder;
    }

    @BeforeEach
    void setUp() {
        mockMutation = (Mutation<TestOrder>) mock(Mutation.class);
        mockRandom = mock(Random.class);
        mockFitnessScore = mock(APLC.class);
        mockEncodingGenerator = mock(EncodingGenerator.class);
        mockStoppingCondition = mock(StoppingCondition.class);
        mockCrossOver = mock(Crossover.class);
        mockParentSelection = mock(ParentSelection.class);
    }
    //helping Gemini
    @Test
    public void geneticShouldReturnTheCorrectCandidate() {
        final int POPULATION_SIZE = 4;
        TestOrder testOrder1 = mockTestOrder(new int[]{0, 1});
        TestOrder eliteCopy = mockTestOrder(new int[]{0, 1});
        when(testOrder1.deepCopy()).thenReturn(eliteCopy);
        when(eliteCopy.mutate()).thenReturn(eliteCopy);
        when(eliteCopy.deepCopy()).thenReturn(eliteCopy);
        TestOrder testOrder2 = mockTestOrder(new int[]{1, 0});
        TestOrder[] initialPopulation = new TestOrder[POPULATION_SIZE];
        for (int i = 0; i < POPULATION_SIZE - 1; i++) {
            initialPopulation[i] = testOrder2;
        }
        initialPopulation[POPULATION_SIZE - 1] = testOrder1;
        when(mockEncodingGenerator.get()).thenReturn(
                initialPopulation[0],
                initialPopulation[1],
                initialPopulation[2],
                initialPopulation[3]
        );

        when(mockFitnessScore.maximise(testOrder2)).thenReturn(5.0);
        when(mockFitnessScore.maximise(testOrder1)).thenReturn(10.0);
        when(mockStoppingCondition.searchMustStop()).thenReturn(false, true);
        when(mockCrossOver.apply(any(), any())).thenReturn(testOrder1);
        SimpleGeneticAlgorithm<TestOrder> genetic = new SimpleGeneticAlgorithm<>(
                mockStoppingCondition,
                mockEncodingGenerator,
                mockFitnessScore,
                mockCrossOver,
                mockParentSelection,
                mockRandom
        );
        assertSame(eliteCopy, genetic.findSolution());
    }

    @Test
    public void geneticShouldReturnStoppingCondition() {
        SimpleGeneticAlgorithm<TestOrder> genetic = new SimpleGeneticAlgorithm<>(mockStoppingCondition, mockEncodingGenerator, mockFitnessScore, mockCrossOver, mockParentSelection, mockRandom);
        StoppingCondition stoppingCondition = genetic.getStoppingCondition();
        assertSame(mockStoppingCondition, stoppingCondition);
    }


}