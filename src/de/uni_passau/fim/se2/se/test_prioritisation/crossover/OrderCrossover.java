package de.uni_passau.fim.se2.se.test_prioritisation.crossover;

import de.uni_passau.fim.se2.se.test_prioritisation.encodings.TestOrder;

import java.util.*;

import static java.lang.Math.max;
import static java.lang.Math.min;


public class OrderCrossover implements Crossover<TestOrder> {

     /**
     * The internal source of randomness.
     */
    private final Random random;

    /**
     * Creates a new order crossover operator.
     *
     * @param random the internal source of randomness
     */
    public OrderCrossover(final Random random) {
        this.random = random;
    }

    /**
     * Combines two parent encodings to create a new offspring encoding using the order crossover operation.
     * The order crossover corresponds to a two-point crossover where the section between two random indices is copied
     * from the first parent and the remaining alleles are added in the order they appear in the second parent.
     * The resulting children must correspond to a valid test order encoding of size n that represents a permutation of tests
     * where each test value in the range [0, n-1] appears exactly once.
     *
     * @param parent1 the first parent encoding
     * @param parent2 the second parent encoding
     * @return the offspring encoding
     */
    @Override
    public TestOrder apply(TestOrder parent1, TestOrder parent2) {

        /*
            first store the values chosen from pos1 to hash map , to check them whether there are exist or not for
            adding them from pos2.
            with first while, adding from pos2 until reach the indices 1.
            then continue adding from pos2 to our selected array from indices2 till the end.
        */

        int rand_1 = this.random.nextInt(parent1.size());
        int rand_2 = this.random.nextInt(parent1.size());
        int indices_1 = min(rand_1, rand_2);
        int indices_2 = max(rand_1, rand_2);
        int[] positionParent1 = parent1.getPositions();
        int[] positionParent2 = parent2.getPositions();
        int[] selected = new int[parent1.size()];
        HashMap<Integer, Boolean> values = new HashMap<Integer, Boolean>();
        for (int i = indices_1; i <= indices_2; i++) {
            values.put(positionParent1[i], true);
            selected[i] = positionParent1[i];
        }
        int selectedcounter = 0;
        int parent2counter = 0;
        while (selectedcounter < indices_1){
            if (values.containsKey(positionParent2[parent2counter])){
                parent2counter += 1;
            }
            else{
                selected[selectedcounter] = positionParent2[parent2counter];
                parent2counter += 1;
                selectedcounter += 1;
            }

        }

        selectedcounter = indices_2 + 1;
        while (selectedcounter < selected.length){
            if (values.containsKey(positionParent2[parent2counter])){
                parent2counter += 1;
            }
            else{
                selected[selectedcounter] = positionParent2[parent2counter];
                parent2counter += 1;
                selectedcounter += 1;
            }
        }
        return new TestOrder(parent1.getMutation(), selected);// is it true the get the parent mutation?
        //throw new UnsupportedOperationException("Implement me");
    }





}
