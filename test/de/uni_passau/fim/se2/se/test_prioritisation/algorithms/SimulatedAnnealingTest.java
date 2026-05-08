package de.uni_passau.fim.se2.se.test_prioritisation.algorithms;

import de.uni_passau.fim.se2.se.test_prioritisation.encodings.EncodingGenerator;
import de.uni_passau.fim.se2.se.test_prioritisation.encodings.TestOrder;
import de.uni_passau.fim.se2.se.test_prioritisation.fitness_functions.APLC;
import de.uni_passau.fim.se2.se.test_prioritisation.mutations.Mutation;
import de.uni_passau.fim.se2.se.test_prioritisation.stopping_conditions.StoppingCondition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class SimulatedAnnealingTest {
    private Random mockRandom;
    private Mutation<TestOrder> mockMutation;
    private APLC mockEnergy;
    private EncodingGenerator mockEncodingGenerator;
    private StoppingCondition mockStoppingCondition;

    private TestOrder mockTestOrder(int[] positions) {
        // Use Mockito's mock creation to allow stubbing of non-final methods like mutate()
        TestOrder mockOrder = mock(TestOrder.class);
        when(mockOrder.getPositions()).thenReturn(positions);
        when(mockOrder.size()).thenReturn(positions.length);
        when(mockOrder.getMutation()).thenReturn(mockMutation);
        when(mockOrder.self()).thenReturn(mockOrder);
        return mockOrder;
    }

    @BeforeEach
    void setUp() {
        // Mock the Mutation object as it's a dependency for TestOrder's constructor.
        // We don't care about its behavior for this test.
        mockMutation = (Mutation<TestOrder>) mock(Mutation.class);
        mockRandom = mock(Random.class);
        mockEnergy = mock(APLC.class);
        mockEncodingGenerator = mock(EncodingGenerator.class);
        mockStoppingCondition = mock(StoppingCondition.class);
    }

    //helping Gemini
    @Test
    public void randomWalkShouldRunCorrectly() {
        int degreeOfFreedom = 1;
        TestOrder testOrder1 = mockTestOrder(new int[]{0, 1});
        TestOrder testOrder2 = mockTestOrder(new int[]{1, 0});
        when(mockEncodingGenerator.get()).thenReturn(testOrder1);
        when(mockEnergy.minimise(testOrder1)).thenReturn(10.0);
        when(mockEnergy.minimise(testOrder2)).thenReturn(5.0);
        when(testOrder1.mutate()).thenReturn(testOrder2);
        when(testOrder2.mutate()).thenReturn(testOrder1);
        when(mockStoppingCondition.searchMustStop()).thenReturn(true);
        SimulatedAnnealing<TestOrder> simulatedAnnealing = new SimulatedAnnealing<>(mockStoppingCondition, mockEncodingGenerator, mockEnergy, degreeOfFreedom, mockRandom);
        simulatedAnnealing.findSolution();
        //Mockito.verify(...)
        //"tells the Mockito framework, "Check the history of method calls that happened on the
        // following mock object".
        Mockito.verify(testOrder1, Mockito.times(20 / 2)).mutate();

    }

    @Test
    public void simulatedAnnealingShouldRejectWorseCandidateWithRespectToProbability() {
        int degreeOfFreedom = 1;
        TestOrder testOrder1 = mockTestOrder(new int[]{0, 1});
        TestOrder testOrder2 = mockTestOrder(new int[]{1, 0});
        when(mockEncodingGenerator.get()).thenReturn(testOrder1);
        when(mockEnergy.minimise(testOrder1)).thenReturn(5.0);
        when(mockEnergy.minimise(testOrder2)).thenReturn(10.0);
        when(testOrder1.mutate()).thenReturn(testOrder2);
        when(testOrder2.mutate()).thenReturn(testOrder1);
        when(mockRandom.nextDouble()).thenReturn(0.9);
        when(mockStoppingCondition.searchMustStop()).thenReturn(false, true);
        SimulatedAnnealing<TestOrder> simulatedAnnealing = new SimulatedAnnealing<>(mockStoppingCondition, mockEncodingGenerator, mockEnergy, degreeOfFreedom, mockRandom);
        assertSame(testOrder1, simulatedAnnealing.findSolution());
    }

    @Test
    public void simulatedAnnealingShouldAcceptWorseCandidateWithRespectToProbability() {
        int degreeOfFreedom = 1;
        TestOrder testOrder1 = mockTestOrder(new int[]{0, 1});
        TestOrder testOrder2 = mockTestOrder(new int[]{1, 0});
        when(mockEncodingGenerator.get()).thenReturn(testOrder1);
        when(mockEnergy.minimise(testOrder1)).thenReturn(5.0);
        when(mockEnergy.minimise(testOrder2)).thenReturn(10.0);
        when(testOrder1.mutate()).thenReturn(testOrder2);
        when(testOrder2.mutate()).thenReturn(testOrder1);
        when(mockRandom.nextDouble()).thenReturn(0.1);
        when(mockStoppingCondition.searchMustStop()).thenReturn(false, false, true);
        SimulatedAnnealing<TestOrder> simulatedAnnealing = new SimulatedAnnealing<>(mockStoppingCondition, mockEncodingGenerator, mockEnergy, degreeOfFreedom, mockRandom);
        simulatedAnnealing.findSolution();
        Mockito.verify(testOrder2, Mockito.times(11)).mutate();
    }

    @Test
    public void simulatedAnnealingShouldReturnStoppingCondition() {
        SimulatedAnnealing<TestOrder> simulatedAnnealing = new SimulatedAnnealing<>(mockStoppingCondition, mockEncodingGenerator, mockEnergy, 3, mockRandom);
        StoppingCondition stoppingCondition = simulatedAnnealing.getStoppingCondition();
        assertSame(mockStoppingCondition, stoppingCondition);
    }

    //helping Gemini
    @Test
    public void shouldCoolWhenMaxTriedIsReached() {
        final int N_DOF = 1;
        TestOrder testOrder1 = mockTestOrder(new int[]{0});
        TestOrder testOrder2 = mockTestOrder(new int[]{1});
        when(mockEncodingGenerator.get()).thenReturn(testOrder1);
        when(mockEnergy.minimise(testOrder1)).thenReturn(10.0);
        when(mockEnergy.minimise(testOrder2)).thenReturn(15.0);
        when(testOrder1.mutate()).thenReturn(testOrder2);
        when(testOrder2.mutate()).thenReturn(testOrder1);
        when(mockRandom.nextDouble()).thenReturn(0.9);

        Boolean[] middleFalse = new Boolean[102];
        java.util.Arrays.fill(middleFalse, false);
        when(mockStoppingCondition.searchMustStop()).thenReturn(false, middleFalse).thenReturn(true);

        SimulatedAnnealing<TestOrder> sa = new SimulatedAnnealing<>(mockStoppingCondition, mockEncodingGenerator, mockEnergy, N_DOF, mockRandom);

        sa.findSolution();
        Mockito.verify(mockStoppingCondition, Mockito.times(104)).notifyFitnessEvaluation();
    }

    @Test
    public void simulatedAnnealingShouldReturnCorrectCandidateWithZeroDegree(){
        TestOrder testOrder1 = mockTestOrder(new int []{0});
        when(mockEncodingGenerator.get()).thenReturn(testOrder1);
        when(testOrder1.mutate()).thenReturn(testOrder1);
        when(mockEnergy.minimise(testOrder1)).thenReturn(1.0);
        when(mockStoppingCondition.searchMustStop()).thenReturn(false, true);
        SimulatedAnnealing<TestOrder> simulatedAnnealing = new SimulatedAnnealing<>(mockStoppingCondition, mockEncodingGenerator, mockEnergy, 0, mockRandom);
        assertSame(testOrder1,simulatedAnnealing.findSolution());
    }
}