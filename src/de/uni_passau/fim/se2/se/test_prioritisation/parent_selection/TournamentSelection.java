package de.uni_passau.fim.se2.se.test_prioritisation.parent_selection;

import de.uni_passau.fim.se2.se.test_prioritisation.encodings.TestOrder;
import de.uni_passau.fim.se2.se.test_prioritisation.fitness_functions.APLC;
import org.junit.jupiter.api.Test;

import java.util.*;

public class TournamentSelection implements ParentSelection<TestOrder> {

    /**
     * A common default value for the size of the tournament.
     */
    private final static int DEFAULT_TOURNAMENT_SIZE = 5;
    private final int tournamentSize;
    private final Random random;
    private final APLC fitnessFunction;

    /**
     * Creates a new tournament selection operator.
     *
     * @param tournamentSize  the size of the tournament
     * @param fitnessFunction the fitness function used to rank the test orders
     * @throws NullPointerException if any of the arguments is {@code null}
     */
    public TournamentSelection(int tournamentSize, APLC fitnessFunction, Random random) {
        //chatgpt for throwing exceptions
        if (tournamentSize <= 0) {
            throw new IllegalArgumentException("tournamentSize must be > 0");
        }
        this.tournamentSize = tournamentSize;
        this.fitnessFunction = Objects.requireNonNull(fitnessFunction, "fitnessFunction must not be null");
        this.random = Objects.requireNonNull(random, "random must not be null");
        //throw new UnsupportedOperationException("Implement me");
    }

    /**
     * Creates a new tournament selection operator with a default tournament size.
     *
     * @param fitnessFunction the fitness function used to rank the test orders
     * @throws NullPointerException if any of the arguments is {@code null}
     */
    public TournamentSelection(APLC fitnessFunction, Random random) {
        this(DEFAULT_TOURNAMENT_SIZE, fitnessFunction, random);
    }

    /**
     * Selects a single parent from a population to be evolved in the current generation of an evolutionary algorithm
     * using the tournament selection strategy.
     *
     * @param population the population from which to select parents
     * @return the selected parent
     */
    @Override
    public TestOrder selectParent(List<TestOrder> population) {

        if (this.tournamentSize > population.size()){
            throw new IllegalArgumentException("population is smaller than tournament size");
        }
        //helping chatgpt for choosing without replacement
        boolean[] seen = new boolean[population.size()];
        List<TestOrder> parents = new ArrayList<>(this.tournamentSize);
        while (parents.size() < this.tournamentSize) {
            int idx = random.nextInt(population.size());
            if (!seen[idx]) {
                seen[idx] = true;
                parents.add(population.get(idx));
            }
        }
        TestOrder bestParent = null;
        double bestFitnessScore = -1;
        for (TestOrder parent : parents){
            double fitnessScore = this.fitnessFunction.maximise(parent);
            if (fitnessScore > bestFitnessScore){
                bestFitnessScore = fitnessScore;
                bestParent = parent;
            }
        }
        return bestParent;
        //throw new UnsupportedOperationException("Implement me");
    }


}
