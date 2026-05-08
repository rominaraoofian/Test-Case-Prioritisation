package de.uni_passau.fim.se2.se.test_prioritisation.fitness_functions;

import de.uni_passau.fim.se2.se.test_prioritisation.encodings.TestOrder;

import java.util.HashMap;


/**
 * The Average Percentage of Lines Covered (APLC) fitness function.
 */
public final class APLC implements FitnessFunction<TestOrder> {

    /**
     * The coverage matrix to be used when computing the APLC metric.
     */
    private final boolean[][] coverageMatrix;

    /**
     * Creates a new APLC fitness function with the given coverage matrix.
     *
     * @param coverageMatrix the coverage matrix to be used when computing the APLC metric
     */
    public APLC(final boolean[][] coverageMatrix) {

        if ((coverageMatrix == null) || (coverageMatrix.length == 0)){
            throw new IllegalArgumentException("coverage matrix is not complete");
        }
        //deep copy of matrix coverage
        this.coverageMatrix = new boolean[coverageMatrix.length][];
        for (int i = 0; i < coverageMatrix.length; i++) {
            this.coverageMatrix[i] = coverageMatrix[i].clone();
        }

        //throw new UnsupportedOperationException("Implement me");
    }


    /**
     * Computes and returns the APLC for the given order of test cases.
     * Orderings that achieve a higher rate of coverage are rewarded with higher values.
     * The APLC ranges between 0.0 and 1.0.
     *
     * @param testOrder the proposed test order for which the fitness value will be computed
     * @return the APLC value of the given test order
     * @throws NullPointerException if {@code null} is given
     */
    @Override
    public double applyAsDouble(final TestOrder testOrder) throws NullPointerException {
        if ((testOrder == null) || (this.coverageMatrix == null)){
            throw new NullPointerException("test order or coverage matrix is null");
        }
        int [] positions = testOrder.getPositions();
        int sumOfTLs = 0;
        HashMap <Integer, Boolean> visitedLines = new HashMap<Integer, Boolean>();
        //looping over tests
        for (int i=0; i<positions.length; i++){
            int testNumber = positions[i];
            if (this.coverageMatrix[testNumber] == null){
                throw new NullPointerException("lines covered by a test is null");
            }
            //looping over lines for a single test
            for (int j=0; j<this.coverageMatrix[i].length; j++){
                if (this.coverageMatrix[testNumber][j]){
                    if (!visitedLines.containsKey(j)){
                        sumOfTLs += (i+1);
                        visitedLines.put(j, true);
                    }
                }
            }
        }
        //refining the calculation  with gemini
        double n = this.coverageMatrix.length;
        double m = visitedLines.size();

        // Handle the edge case where no lines are covered (m=0)
        if (m == 0) {
            return 0.0;
        }

        // APLC(T') = 1 - (1 / (n * m)) * (Sum of TLi) + (1 / (2 * n))
        double result = 1.0
                - (1.0 / (n * m) * sumOfTLs)
                + (1.0 / (2.0 * n));

        return result;
        //throw new UnsupportedOperationException("Implement me");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double maximise(TestOrder encoding) throws NullPointerException {
        return applyAsDouble(encoding);
        //throw new UnsupportedOperationException("Implement me");

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double minimise(TestOrder encoding) throws NullPointerException {
        return 1.0 -applyAsDouble(encoding);
        //throw new UnsupportedOperationException("Implement me");

    }
}
