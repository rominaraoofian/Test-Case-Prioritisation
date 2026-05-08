package de.uni_passau.fim.se2.se.test_prioritisation.parent_selection;


import de.uni_passau.fim.se2.se.test_prioritisation.encodings.TestOrder;
import de.uni_passau.fim.se2.se.test_prioritisation.fitness_functions.APLC;
import de.uni_passau.fim.se2.se.test_prioritisation.mutations.Mutation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class TournamentSelectionTest {
    private Random mockRandom;
    private Mutation<TestOrder> mockMutation;
    private APLC mockFitnessfunction;
    @BeforeEach
    void setUp() {
        // Mock the Mutation object as it's a dependency for TestOrder's constructor.
        // We don't care about its behavior for this test.
        mockMutation = (Mutation<TestOrder>) mock(Mutation.class);
        mockRandom = mock(Random.class);
        mockFitnessfunction = mock(APLC.class);
    }
    @Test
    public void zeroTournamentSizeShouldReturnException(){

        assertThrows(IllegalArgumentException.class, () -> {
            new TournamentSelection(0,mockFitnessfunction, mockRandom);
        });

    }
    @Test
    public void nullFitnessFunctionShouldReturnException() {

        assertThrows(NullPointerException.class, () -> {
            new TournamentSelection(2, null, mockRandom);
        });
    }
    @Test
    public void nullRandomShouldReturnException() {

        assertThrows(NullPointerException.class, () -> {
            new TournamentSelection(2, mockFitnessfunction, null);
        });
    }
    @Test
    public void largeTournamentSizeShouldReturnException(){

        assertThrows(IllegalArgumentException.class, ()-> {
            TestOrder testOrder1 = new TestOrder(mockMutation, new int []{0,1,2,3,4,5});
            TestOrder testOrder2 = new TestOrder(mockMutation, new int []{1,4,5,0,2,3});
            List<TestOrder> population = new ArrayList<TestOrder>();
            population.add(testOrder1);
            population.add(testOrder2);
            new TournamentSelection(10, mockFitnessfunction, mockRandom).selectParent(population);
        });
    }
    @Test
    public void selectParentShouldReturnTrueParent(){
        TestOrder testOrder1 = new TestOrder(mockMutation, new int []{0,1,2,3,4,5});
        TestOrder testOrder2 = new TestOrder(mockMutation, new int []{1,4,5,0,2,3});
        List<TestOrder> population = new ArrayList<TestOrder>();
        population.add(testOrder1);
        population.add(testOrder2);
        when(mockRandom.nextInt(2)).thenReturn(0,1);
        when(mockFitnessfunction.maximise(testOrder1)).thenReturn(5.0);
        when(mockFitnessfunction.maximise(testOrder2)).thenReturn(4.0);
        TestOrder expectedParent = new TournamentSelection(2, mockFitnessfunction, mockRandom).selectParent(population);
        assertSame(testOrder1, expectedParent);
    }
    @Test
    public void withDuplicateShouldReturnTrueParent(){
        TestOrder testOrder1 = new TestOrder(mockMutation, new int []{0,1,2,3,4,5});
        TestOrder testOrder2 = new TestOrder(mockMutation, new int []{1,4,5,0,2,3});
        TestOrder testOrder3 = new TestOrder(mockMutation, new int []{1,5,4,3,2,0});
        TestOrder testOrder4 = new TestOrder(mockMutation, new int []{0,5,1,4,2,3});
        List<TestOrder> population = new ArrayList<TestOrder>();
        population.add(testOrder1);
        population.add(testOrder2);
        population.add(testOrder3);
        population.add(testOrder4);
        when(mockRandom.nextInt(4)).thenReturn(0,1, 0,1,2);
        when(mockFitnessfunction.maximise(testOrder1)).thenReturn(4.0);
        when(mockFitnessfunction.maximise(testOrder2)).thenReturn(6.0);
        when(mockFitnessfunction.maximise(testOrder3)).thenReturn(8.0);
        when(mockFitnessfunction.maximise(testOrder4)).thenReturn(10.0);
        TestOrder expectedParent = new TournamentSelection(3, mockFitnessfunction, mockRandom).selectParent(population);
        assertSame(testOrder3, expectedParent);
    }



}