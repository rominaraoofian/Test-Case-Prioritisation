package de.uni_passau.fim.se2.se.test_prioritisation.stopping_conditions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MaxFitnessEvaluationsTest {

    @Test
    public void notifySearchStartShouldHaveZeroNumberOfEvaluations(){
        MaxFitnessEvaluations maxFitnessEvaluations = new MaxFitnessEvaluations(10);
        maxFitnessEvaluations.notifySearchStarted();
        assertEquals(0.0, maxFitnessEvaluations.getProgress());
    }
    //helping Gemini
    @Test
    public void notifyFitnessEvaluationShouldIncreaseNumberOfEvaluations(){
        MaxFitnessEvaluations maxFitnessEvaluations = new MaxFitnessEvaluations(2);
        maxFitnessEvaluations.notifySearchStarted();
        assertFalse(maxFitnessEvaluations.searchMustStop());
        maxFitnessEvaluations.notifyFitnessEvaluation();
        assertFalse(maxFitnessEvaluations.searchMustStop());
    }
    @Test
    public void searchMustStopShouldReturnTrueDecision(){
        MaxFitnessEvaluations maxFitnessEvaluations = new MaxFitnessEvaluations(1);
        maxFitnessEvaluations.notifySearchStarted();
        maxFitnessEvaluations.notifyFitnessEvaluation();
        assertTrue(maxFitnessEvaluations.searchMustStop());
    }
    @Test
    public void getProgressShouldReturnCorrectFraction(){
        MaxFitnessEvaluations maxFitnessEvaluations = new MaxFitnessEvaluations(2);
        maxFitnessEvaluations.notifySearchStarted();
        maxFitnessEvaluations.notifyFitnessEvaluation();
        assertEquals(1.0/2.0, maxFitnessEvaluations.getProgress());
    }
    @Test
    public void zeroMaxNumberEvaluationShouldReturnOne(){
        MaxFitnessEvaluations maxFitnessEvaluations = new MaxFitnessEvaluations(0);
        maxFitnessEvaluations.notifySearchStarted();
        maxFitnessEvaluations.notifyFitnessEvaluation();
        assertEquals(1.0, maxFitnessEvaluations.getProgress());
    }

}