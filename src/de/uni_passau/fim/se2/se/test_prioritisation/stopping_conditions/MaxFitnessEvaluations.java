package de.uni_passau.fim.se2.se.test_prioritisation.stopping_conditions;

/**
 * Stopping condition that stops the search after a specified number of fitness evaluations.
 */
public class MaxFitnessEvaluations implements StoppingCondition {

    private final int maxFitnessEvaluations;
    private int numberOfEvalutaions;

    public MaxFitnessEvaluations(final int maxFitnessEvaluations) {
        this.maxFitnessEvaluations = maxFitnessEvaluations;
        this.numberOfEvalutaions = 0;
        //throw new UnsupportedOperationException("Implement me");
    }

    @Override
    public void notifySearchStarted() {
        this.numberOfEvalutaions = 0;
        //throw new UnsupportedOperationException("Implement me");
    }

    @Override
    public void notifyFitnessEvaluation() {
        this.numberOfEvalutaions += 1;
        //throw new UnsupportedOperationException("Implement me");
    }

    @Override
    public boolean searchMustStop() {
        if (this.numberOfEvalutaions >= this.maxFitnessEvaluations){
            return true;
        }
        return false;
        //throw new UnsupportedOperationException("Implement me");
    }

    @Override
    public double getProgress() {
        if (this.maxFitnessEvaluations == 0){
            return 1.0; //no source to use.
        }
        return (double) this.numberOfEvalutaions / (double ) this.maxFitnessEvaluations;
        //throw new UnsupportedOperationException("Implement me");
    }
}
