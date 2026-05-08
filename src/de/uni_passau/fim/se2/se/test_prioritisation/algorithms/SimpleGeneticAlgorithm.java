package de.uni_passau.fim.se2.se.test_prioritisation.algorithms;

import de.uni_passau.fim.se2.se.test_prioritisation.crossover.Crossover;
import de.uni_passau.fim.se2.se.test_prioritisation.encodings.Encoding;
import de.uni_passau.fim.se2.se.test_prioritisation.encodings.EncodingGenerator;
import de.uni_passau.fim.se2.se.test_prioritisation.fitness_functions.FitnessFunction;
import de.uni_passau.fim.se2.se.test_prioritisation.parent_selection.ParentSelection;
import de.uni_passau.fim.se2.se.test_prioritisation.stopping_conditions.StoppingCondition;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public final class SimpleGeneticAlgorithm<E extends Encoding<E>> implements SearchAlgorithm<E> {

    private final StoppingCondition stoppingCondition;
    private final EncodingGenerator<E> encodingGenerator;
    private final  FitnessFunction<E> fitnessFunction;
    private final Crossover<E> crossOver;
    private final ParentSelection<E> parentSelection;
    private final Random random;

    /**
     * Creates a new simple genetic algorithm with the given components.
     *
     * @param stoppingCondition the stopping condition to be used by the genetic algorithm
     * @param encodingGenerator the encoding generator used to create the initial population
     * @param fitnessFunction   the fitness function used to evaluate the quality of the individuals in the population
     * @param crossover         the crossover operator used to create offspring from parents
     * @param parentSelection   the parent selection operator used to select parents for the next generation
     * @param random            the source of randomness for this algorithm
     */
    public SimpleGeneticAlgorithm(
            final StoppingCondition stoppingCondition,
            final EncodingGenerator<E> encodingGenerator,
            final FitnessFunction<E> fitnessFunction,
            final Crossover<E> crossover,
            final ParentSelection<E> parentSelection,
            final Random random) {
        this.stoppingCondition = stoppingCondition;
        this.encodingGenerator = encodingGenerator;
        this.fitnessFunction = fitnessFunction;
        this.crossOver = crossover;
        this.parentSelection = parentSelection;
        this.random = random;

        //throw new UnsupportedOperationException("Implement me");
    }


    /**
     * Runs the genetic algorithm to find a solution to the given problem.
     *
     * @return the best individual found by the genetic algorithm
     */
    @Override
    public E findSolution() {
        final int populationSize = 20;
        final double p_xor = 0.7;
        E bestCandidate = null;
        E elite = null;
        double bestFitnessScore = Double.NEGATIVE_INFINITY;
        List<E> population = new ArrayList<>(populationSize);
        this.stoppingCondition.notifySearchStarted();
        for (int i=0; i<populationSize; i++){
            E candidate =  this.encodingGenerator.get();
            double fitnessScore = this.fitnessFunction.maximise(candidate);
            this.stoppingCondition.notifyFitnessEvaluation();
            population.add(candidate);
            if (fitnessScore > bestFitnessScore){
                bestCandidate = candidate;
                bestFitnessScore = fitnessScore;
            }
        }
        elite = bestCandidate.deepCopy();
        while (!stoppingCondition.searchMustStop()){
            List <E> new_population = new ArrayList<>(populationSize);
            if (elite != null) {

                new_population.add(elite.deepCopy());
            }
            while (new_population.size() < populationSize){
                E parent1 = this.parentSelection.selectParent(population);
                E parent2 = this.parentSelection.selectParent(population);
                E offSpring;
                if (random.nextDouble() < p_xor){
                    offSpring = this.crossOver.apply(parent1, parent2);
                }
                else{
                    offSpring = parent1.deepCopy();
                }
                offSpring = offSpring.mutate();
                new_population.add(offSpring);
            }
            population = new_population;
            for (int i=1; i< populationSize; i++){
                E candidate  = population.get(i);
                double fitnessScore = this.fitnessFunction.maximise(candidate);
                if (stoppingCondition.searchMustStop()) {
                    break;
                }
                stoppingCondition.notifyFitnessEvaluation();
                if (fitnessScore > bestFitnessScore){
                    bestFitnessScore = fitnessScore;
                    bestCandidate = candidate;
                    elite = candidate.deepCopy();
                }
            }
            bestCandidate = elite;
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
