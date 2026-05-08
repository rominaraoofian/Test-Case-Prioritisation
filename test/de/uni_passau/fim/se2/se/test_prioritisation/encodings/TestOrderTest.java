package de.uni_passau.fim.se2.se.test_prioritisation.encodings;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;
import static org.mockito.Mockito.mock;
import static org.junit.jupiter.api.Assertions.*;

import de.uni_passau.fim.se2.se.test_prioritisation.mutations.Mutation; // import it myself!

import java.util.Random;


public class TestOrderTest {
    private Mutation<TestOrder> mockMutation;

    //gemini helping
    @BeforeEach
    void setUp() {
        // Mock the Mutation object as it's a dependency for TestOrder's constructor.
        // We don't care about its behavior for this test.
        mockMutation = (Mutation<TestOrder>) mock(Mutation.class);
    }

    @Test
    public void trueOrderShouldReturnTrue(){
        int [] tests = {0,1,2,3,4,5};
        boolean check =  TestOrder.isValid(tests);
        assertTrue(check);
    }

    @Test
    public void missing3ShouldReturnFalse(){
        int [] tests = {0,1,2,4};
        boolean check =  TestOrder.isValid(tests);
        assertFalse(check);
    }
    @Test
    public void notContagiousShouldReturnFalse(){
        int [] tests = {3,5,6,1};
        boolean check =  TestOrder.isValid(tests);
        assertFalse(check);
    }
    @Test
    public void redundantShouldReturnFalse(){
        int [] tests = {0,1,1,2};
        boolean check =  TestOrder.isValid(tests);
        assertFalse(check);
    }
    @Test
    public void incorrectRangeShouldReturnFalse(){
        int [] tests = {1,2,3,4,5};
        boolean check =  TestOrder.isValid(tests);
        assertFalse(check);
    }
    @Test
    public void emptyTestShouldReturnFalse(){
        int [] tests = {};
        assertTrue(TestOrder.isValid(tests));
    }

    @Test
    public void deepCopyShouldReturnTheSameTest(){
        int [] positions = new int [] {0,3,1,2,4};
        TestOrder testorder = new TestOrder(mockMutation, positions);
        TestOrder testOrderCopy = testorder.deepCopy();
        assertNotSame(testorder.getPositions(),  testOrderCopy.getPositions());
    }
    @Test
    public void sizeShouldReturnCorrectSize(){
        int [] positions = new int [] {0,3,1,2,4};
        TestOrder testOrder = new TestOrder(mockMutation, positions);
        assertEquals(5, testOrder.size());
    }

    @Test
    public void getPositionsShouldReturnCorrectPositions(){
        int [] positions = new int [] {0,3,1,2,4};
        TestOrder testOrder = new TestOrder(mockMutation, positions);
        assertSame(positions, testOrder.getPositions());
    }
    //gemini helping
    @Test
    public void constructorShouldThrowOnInvalidPositions() {
        int[] invalidPositions = new int[]{1, 2, 3};
        assertThrows(IllegalArgumentException.class, () -> {
            new TestOrder(mockMutation, invalidPositions);
        });
    }
    @Test
    public void getShouldReturnValidTestOrder(){
        Random random = new Random();
        int positionSize = 5;
        TestOrderGenerator generator = new TestOrderGenerator(random, mockMutation, 5);
        assertTrue(TestOrder.isValid(generator.get().getPositions()));
    }




}