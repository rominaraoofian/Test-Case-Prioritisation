package de.uni_passau.fim.se2.se.test_prioritisation.mutations;

import de.uni_passau.fim.se2.se.test_prioritisation.encodings.TestOrder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.mock;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;


public class ShiftToBeginningMutationTest {
    private Mutation<TestOrder> mockMutation;
    @BeforeEach
    void setUp() {
        // Mock the Mutation object as it's a dependency for TestOrder's constructor.
        // We don't care about its behavior for this test.
        mockMutation = (Mutation<TestOrder>) mock(Mutation.class);
    }

    @Test
    public void oneSizePositionMutationMustRemainUnchanged(){
        //what can i do with randomness? create shift, put it in test order, then shift.apply in testOrder?
        Random rand = new Random();
        var shiftBeginning = new ShiftToBeginningMutation(rand);
        TestOrder test = new TestOrder(shiftBeginning, new int[]{0});
        TestOrder mutated = shiftBeginning.apply(test);
        assertArrayEquals(new int[]{0},mutated.getPositions());

    }
    //gemini helping
    @Test
    public void ShiftToBeginningMustReturnCorrectMutation(){
        Random mockRandom = mock(Random.class);
        int [] positions = new int [] {0,1,2,3,4,5};
        Mockito.when(mockRandom.nextInt(positions.length)).thenReturn(2);
        var shiftToBeginning = new ShiftToBeginningMutation(mockRandom);
        TestOrder test = new TestOrder(mockMutation, positions);
        TestOrder mutatedPositions = shiftToBeginning.apply(test);
        int [] expectedPositions = new int[] {2,0,1,3,4,5};
        assertArrayEquals(expectedPositions, mutatedPositions.getPositions());

    }
}