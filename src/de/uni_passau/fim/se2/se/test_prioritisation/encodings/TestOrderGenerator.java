package de.uni_passau.fim.se2.se.test_prioritisation.encodings;

import de.uni_passau.fim.se2.se.test_prioritisation.mutations.Mutation;

import java.util.Random;

/**
 * A generator for random test case orderings of a regression test suite. In the literature, indices
 * would start at 1. However, we let them start at 0 as this simplifies the implementation. The
 * highest index is given by the number of test cases minus 1. The range of indices is contiguous.
 */
public class TestOrderGenerator implements EncodingGenerator<TestOrder> {

    /**
     * Creates a new test order generator with the given mutation and number of test cases.
     *
     * @param random     the source of randomness
     * @param mutation   the elementary transformation that the generated orderings will use
     * @param testCases  the number of test cases in the ordering
     */

    //Is it true to write it like that or not?
    private final Random random;
    private final Mutation<TestOrder> mutation;
    private final int testCases;

    public TestOrderGenerator(final Random random, final Mutation<TestOrder> mutation, final int testCases) {
        this.random = random;
        this.mutation = mutation;
        this.testCases = testCases;

        //throw new UnsupportedOperationException("Implement me");
    }

    /**
     * Creates and returns a random permutation of test cases.
     *
     * @return random test case ordering
     */
    @Override
    public TestOrder get() {
        int[] order = new int[testCases];
        for (int i=0; i< testCases; i++){
            order[i] = i;
        }
        for (int i = 0; i<testCases; i++){
            int rand_pos1 = random.nextInt(testCases);
            int rand_pos2 = random.nextInt(testCases);
            int temp_value = order[rand_pos1];
            order[rand_pos1] = order[rand_pos2];
            order[rand_pos2] = temp_value;

        }
        return new TestOrder(mutation, order);
        //throw new UnsupportedOperationException("Implement me");
    }
}
