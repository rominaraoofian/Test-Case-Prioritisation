package de.uni_passau.fim.se2.se.test_prioritisation.encodings;

import de.uni_passau.fim.se2.se.test_prioritisation.mutations.Mutation;
import java.util.HashMap; //import it myself!

public class TestOrder extends Encoding<TestOrder> {

    /**
     * Internal backing array that stores the actual ordering of the tests. By convention, we assign
     * a unique number to every test case. The number range starts at 0, ends at n-1 and is
     * contiguous. The position of a test case in the regression test suite corresponds to the
     * position of its unique number in this array. A valid test case prioritization must not
     * contain numbers outside this range. Also, the same number must not occur twice. These
     * requirements can be checked with {@code isValid(final int[] tests)}.
     */
    private final int[] positions;
    private final Mutation<TestOrder> mutation; //I add this myself, is it okay?

    /**
     * Creates a new test order with the given mutation and test case ordering.
     *
     * @param mutation  the mutation to be used with this encoding
     * @param positions the test case ordering
     */

    public TestOrder(Mutation<TestOrder> mutation, int[] positions) {
        super(mutation);
        this.mutation = mutation;
        if (isValid(positions))
            this.positions = positions;
        else{
            throw new IllegalArgumentException("Invalid positions");
        }
        //throw new UnsupportedOperationException("Implement me");
    }


    /**
     * Tells whether the given array represents a valid regression test case prioritization encoding.
     * By convention, we require that every test must have a unique identifier starting at 0.
     * Since ranges are contiguous, this implies that numbers must only occur once and be located in the range from 0 to n -1.
     *
     * @param tests the test suite prioritization array to check
     * @return {@code true} if the given prioritization is valid, {@code false} otherwise
     */
    public static boolean isValid(final int[] tests) {
         /* implementing hashmap with the length of array and the values of false.
            if the array is valid, it should transfer all values to true.
          */
         HashMap <Integer, Boolean> check = new HashMap<Integer, Boolean>();
         for(int i = 0; i < tests.length; i ++){
             check.put(i, false);
         }
         for (Integer item : tests){
                check.replace(item, true);
         }
        return !check.containsValue(false);

        //throw new UnsupportedOperationException("Implement me");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TestOrder deepCopy() {
        int [] copiedPositions = new int[this.positions.length];
        for (int i = 0; i<this.positions.length; i++){
            copiedPositions[i] = this.positions[i];
        }
        return new TestOrder(mutation, copiedPositions);
        //throw new UnsupportedOperationException("Implement me");
    }

    /**
     * Returns the number of test cases in this test case ordering.
     *
     * @return the number of test cases
     */
    public int size() {
        return positions.length;
    }

    /**
     * Returns a reference to the underlying internal backing array.
     *
     * @return the orderings array
     */
    public int[] getPositions() {
        return positions;
    }


    @Override
    public TestOrder self() {
        return this;
    }

}
