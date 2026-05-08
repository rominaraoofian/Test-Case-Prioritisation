# Regression Test Case Prioritization

## Overview
This project explores search-based techniques for improving regression testing efficiency. Instead of executing test cases in their original order, the system searches for an optimized execution sequence that increases early software coverage and fault detection.

Different optimization strategies are implemented and evaluated to analyze their effectiveness on the prioritization problem.

Algorithms in this project:

- Genetic Algorithm (GA)
- Simulated Annealing (SA)
- Random Search (RS)
- Random Walk (RW)

## Core Idea
Each candidate solution represents an ordered sequence of test cases. The optimization process attempts to discover execution orders that achieve higher coverage earlier during testing.

The quality of each solution is measured using the **Average Percentage of Line Coverage (APLC)** metric.

## Implemented Features

### Search Operators
- Order-based crossover
- Mutation through test case repositioning
- Tournament parent selection

### Optimization Strategies
- Population-based evolutionary search using GA
- Temperature-driven local search using SA
- Baseline stochastic exploration with RS and RW

### Stopping Criterion
All algorithms terminate after a predefined number of fitness evaluations.
