package de.uni_passau.fim.se2.se.test_prioritisation.crossover;

import de.uni_passau.fim.se2.se.test_prioritisation.encodings.TestOrder;
import de.uni_passau.fim.se2.se.test_prioritisation.mutations.Mutation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class OrderCrossoverTest {
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
    public void orderCrossOverShouldReturnCorrectCrossOver(){
        int [] position1 = new int [] {0,1,2,3,4};
        int [] position2 = new int [] {1,4,2,0,3};
        TestOrder testOrder1 = new TestOrder(mockMutation, position1);
        TestOrder testOrder2 = new TestOrder(mockMutation, position2);
        Mockito.when(mockRandom.nextInt(position1.length)).thenReturn(2, 3);
        int [] expectedCrossOrderPosition = new int [] {1,4,2,3,0};
        var orderCrossover = new OrderCrossover(mockRandom);
        TestOrder crossOrderPosition = orderCrossover.apply(testOrder1, testOrder2);
        assertArrayEquals(expectedCrossOrderPosition, crossOrderPosition.getPositions());
    }

}