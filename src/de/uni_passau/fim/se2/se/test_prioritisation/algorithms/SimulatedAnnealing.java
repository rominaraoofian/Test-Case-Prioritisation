package de.uni_passau.fim.se2.se.test_prioritisation.algorithms;

import de.uni_passau.fim.se2.se.test_prioritisation.encodings.Encoding;
import de.uni_passau.fim.se2.se.test_prioritisation.encodings.EncodingGenerator;
import de.uni_passau.fim.se2.se.test_prioritisation.fitness_functions.FitnessFunction;
import de.uni_passau.fim.se2.se.test_prioritisation.stopping_conditions.StoppingCondition;

import java.util.Random;



/**
 * Implements the Simulated Annealing algorithm for test order prioritisation based on
 * -----------------------------------------------------------------------------------------
 * Flow chart of the algorithm:
 * Bastien Chopard, Marco Tomassini, "An Introduction to Metaheuristics for Optimization",
 * (Springer), Ch. 4.3, Page 63
 * -----------------------------------------------------------------------------------------
 * Note we've applied a few modifications to add elitism.
 *
 * @param <E> the type of encoding
 */
public final class SimulatedAnnealing<E extends Encoding<E>> implements SearchAlgorithm<E> {

    private final StoppingCondition stoppingCondition;
    private final EncodingGenerator<E> encodingGenerator;
    private final FitnessFunction<E> energy;
    private final int degreeOfFreedom;
    private final Random random;

    /**
     * Constructs a new simulated annealing algorithm.
     *
     * @param stoppingCondition the stopping condition to use
     * @param encodingGenerator the encoding generator to use
     * @param energy            the energy fitness function to use
     * @param degreesOfFreedom  the number of degrees of freedom of the problem, i.e. the number of variables that define a solution
     * @param random            the random number generator to use
     */
    public SimulatedAnnealing(
            final StoppingCondition stoppingCondition,
            final EncodingGenerator<E> encodingGenerator,
            final FitnessFunction<E> energy,
            final int degreesOfFreedom,
            final Random random) {
        this.stoppingCondition = stoppingCondition;
        this.encodingGenerator = encodingGenerator;
        this.energy = energy;
        this.degreeOfFreedom = degreesOfFreedom;
        this.random = random;
        //throw new UnsupportedOperationException("Implement me");
    }

    /**
     * Performs the Simulated Annealing algorithm to search for an optimal solution of the encoded problem.
     * Since Simulated Annealing is designed as a minimisation algorithm, optimal solutions are characterized by a minimal energy value.
     */
    @Override
    public E findSolution() {
        final int numberOfIterationsRandomWalk = 20;
        final double pZero  = 0.5;
        final int maxAccepted = 12 * this.degreeOfFreedom;
        final int maxTried= 100 * this.degreeOfFreedom;
        int accepted = 0;
        int tried = 0;
        double deltaSum = 0;
        E startingCandidate = this.encodingGenerator.get();
        double CurrentEnergy = this.energy.minimise(startingCandidate);
        E currentCandidate = startingCandidate;
        for (int i = 0; i < numberOfIterationsRandomWalk; i ++){
            E neighbor = currentCandidate.mutate();
            double neighborEnergy = this.energy.minimise(neighbor);
            deltaSum += Math.abs(CurrentEnergy - neighborEnergy);
            currentCandidate = neighbor;
            CurrentEnergy = neighborEnergy;
        }
        double averageDeltaSum  = deltaSum / numberOfIterationsRandomWalk;
        double temp = averageDeltaSum / (-Math.log(pZero));

        this.stoppingCondition.notifySearchStarted();
        E candidate = startingCandidate;
        double energy = this.energy.minimise(candidate);
        this.stoppingCondition.notifyFitnessEvaluation();
        double bestEnergy = energy;
        E bestCandidate = candidate;
        while (!this.stoppingCondition.searchMustStop()) {
            E neighbour = candidate.mutate();
            double energyNeighbour =  this.energy.minimise(neighbour);
            this.stoppingCondition.notifyFitnessEvaluation();
            tried += 1;
            double energyDelta = energyNeighbour - energy;
            if (energyDelta < 0){
                candidate = neighbour;
                energy = energyNeighbour;
                accepted += 1;

            }
            else{
                double p = Math.exp(-energyDelta / temp);
                if (p > this.random.nextDouble()){
                    candidate = neighbour;
                    energy = energyNeighbour;
                    accepted += 1;
                }
            }

            if (energy < bestEnergy){
                bestEnergy = energy;
                bestCandidate = candidate;
            }
            if (accepted >=  maxAccepted || tried >= maxTried) {
                temp = 0.9 * temp;
                accepted = 0;
                tried = 0;
            }

        }
        return bestCandidate;
        //throw new UnsupportedOperationException("Implement me");
    }

    @Override
    public StoppingCondition getStoppingCondition() {
        return this.stoppingCondition;
        //throw new UnsupportedOperationException("Implement me");
    }
}
