package de.uni_passau.fim.se2.se.test_prioritisation.mutations;

import de.uni_passau.fim.se2.se.test_prioritisation.encodings.TestOrder;

import java.util.Random;
import java.util.Arrays;

/**
 * A mutation that shifts a test to the beginning of the sequence.
 */
public class ShiftToBeginningMutation implements Mutation<TestOrder> {

    /**
     * The internal source of randomness.
     */
    private final Random random;

    public ShiftToBeginningMutation(final Random random) {
        this.random = random;
    }

    /**
     * Shifts a test to the beginning of the sequence.
     *
     * @param encoding the test order to be mutated
     * @return the mutated test order
     */
    @Override
    public TestOrder apply(TestOrder encoding) {

        int random_pos = this.random.nextInt(encoding.size());
        int[] positions = Arrays.copyOf(encoding.getPositions(), encoding.getPositions().length);
        if (random_pos == 0) {
            return new TestOrder(encoding.getMutation(), positions);
        }
        int testToMove = positions[random_pos];

        for (int i = random_pos; i > 0; i--) {
            positions[i] = positions[i - 1];
        }
        positions[0] = testToMove;

        return new TestOrder(encoding.getMutation(), positions);
    }
}
