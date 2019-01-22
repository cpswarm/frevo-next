/**
 * File: NngaMethod.java
 * 
 * Copyright (C) 2019 FREVO project contributors
 *
 * Universitaet Klagenfurt licenses this file to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance with the License. You may obtain a
 * copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package at.aau.frevo.method.nnga;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.SplittableRandom;
import at.aau.frevo.Executor;
import at.aau.frevo.Method;
import at.aau.frevo.Operator;
import at.aau.frevo.Representation;
import at.aau.frevo.Result;

/**
 * Basic implementation of Neural Network Genertic Algorithm (NNGA).
 * <p>
 * For details, see "Neural Network Training Using Genetic Algorithms" (Van Rooij et al., 1996).
 * <p>
 * Each new generation consists of a number of elites, some random copied candidates, some crosses,
 * some mutations and some new candidates.
 *
 * @param <R> the type of {@code Representation} associated with the NNGA method
 */
public class NngaMethod<R extends Representation> extends Method<R> {

  protected double skewFactor;
  protected double eliteWeight;
  protected double randomWeight;
  protected double mutatedWeight;
  protected double crossedWeight;
  protected double newWeight;

  protected double[] skewArray;
  protected int candidateCount;
  protected List<Result<R>> rankedCandidates;

  /**
   * Creates a new {@code NngaMethod} instance with the specified configuration.
   * 
   * @param builder          the {@code NngaMethodBuilder} used for configuration
   * @param rankedCandidates a list of initial pre-ranked candidate {@code Representation} instances
   * @param operator         the {@code Operator} to use
   * @param executor         the {@code Executor} to use
   * @param random           the random number generator to use
   */
  public NngaMethod(NngaMethodBuilder builder, List<Result<R>> rankedCandidates,
      Operator<R> operator, Executor executor, SplittableRandom random) {
    super(executor, operator, random);

    skewFactor = builder.getSkewFactor();
    eliteWeight = builder.getEliteWeight();
    randomWeight = builder.getRandomWeight();
    mutatedWeight = builder.getMutatedWeight();
    crossedWeight = builder.getCrossedWeight();
    newWeight = builder.getNewWeight();

    this.rankedCandidates = new ArrayList<>(rankedCandidates);
    candidateCount = rankedCandidates.size();
    skewArray = createSkewArray(skewFactor, candidateCount);
  }

  /**
   * Creates a skewed array of normalized weights.
   * 
   * @param skewFactor the skew factor
   * @param size       the size of the skewed array
   * @return the skew array
   */
  protected double[] createSkewArray(double skewFactor, int size) {
    var skewArray = new double[size];
    double sum = 0;
    for (int i = 0; i < size; i++) {
      double v = Math.pow(size - i, skewFactor);
      skewArray[i] = v;
      sum += v;
    }

    for (int i = 0; i < size; i++) {
      skewArray[i] /= sum;
    }
    return skewArray;
  }

  /**
   * Gets a random candidate from the current generation. Better ranked candidates are more likely
   * to be selected.
   * 
   * @return a random candidate from the current generation
   */
  protected R getSkewedRandomCandidate() {
    double r = random.nextDouble();
    int i = 0;
    while ((i < (skewArray.length - 1)) && (r > skewArray[i])) {
      r -= skewArray[i];
      i++;
    }
    return rankedCandidates.get(i).getRepresentation();
  }

  @Override
  public List<Result<R>> run(int generationCount) throws InterruptedException {
    for (int generation = 0; generation < generationCount; generation++) {
      if (rankedCandidates.get(0).getFitness() >= executor.getProblemBuilder()
          .getMaximumFitness()) {
        break;
      }
      rankedCandidates = executor.evaluateRepresentations(evolve());
      totalGenerationCount++;
    }
    return rankedCandidates;
  }

  @Override
  public List<Result<R>> getRankedCandidates() {
    return rankedCandidates;
  }

  /**
   * Evolves the current generation. Produce a new generation consisting of elite, randomly selected
   * candidates, crosses, mutations and new candidates.
   * 
   * @return the new generation of candidate {@code Representation} instances
   */
  protected ArrayList<R> evolve() {
    var set = new HashSet<R>();
    var totalWeight = eliteWeight + randomWeight + mutatedWeight + crossedWeight + newWeight;

    // copy some elite
    int eliteCount = (int) (eliteWeight / totalWeight * candidateCount);
    for (int i = 0; i < eliteCount; i++) {
      set.add(rankedCandidates.get(i).getRepresentation());
    }

    // copy some random members of the population; better ranked members are more
    // likely to be copied
    int randomCount = (int) (randomWeight / totalWeight * candidateCount);
    for (int i = 0; i < randomCount; i++) {
      while (!set.add(getSkewedRandomCandidate())) {
      }
    }

    // create some mutated candidates
    int mutateCount = (int) (mutatedWeight / totalWeight * candidateCount);
    for (int i = 0; i < mutateCount; i++) {
      set.add(operator.operator1(getSkewedRandomCandidate()));
    }

    // create some crossed candidates
    int crossCount = (int) (crossedWeight / totalWeight * candidateCount);
    for (int i = 0; i < crossCount; i++) {
      R parent1 = getSkewedRandomCandidate();
      R parent2 = null;
      double bestDifference = 0;

      for (int j = 0; j < 10; j++) {
        R t = getSkewedRandomCandidate();
        double difference = operator.difference(parent1, t);
        if (difference > bestDifference) {
          parent2 = t;
          bestDifference = difference;
        }
      }

      set.add(operator.operator2(parent1, parent2));
    }

    // generate new (remaining) candidates
    while (set.size() < candidateCount) {
      set.add(operator.operator0());
    }

    return new ArrayList<R>(set);
  }

  /**
   * Gets a parameter which influences the likelihood of more successful candidates being used in a
   * new generation.
   * 
   * @return the skew factor
   */
  public double getSkewFactor() {
    return skewFactor;
  }

  /**
   * Gets the proportion of elite copied to a new generation.
   * 
   * @return the elite weight
   */
  public double getEliteWeight() {
    return eliteWeight;
  }

  /**
   * Gets the proportion of random members copied to a new generation.
   * 
   * @return the random weight
   */
  public double getRandomWeight() {
    return randomWeight;
  }

  /**
   * Gets the proportion of mutated members included in a new generation.
   * 
   * @return the mutated weight
   */
  public double getMutatedWeight() {
    return mutatedWeight;
  }

  /**
   * Gets the proportion of crossed members included in a new generation.
   * 
   * @return the crossed weight
   */
  public double getCrossedWeight() {
    return crossedWeight;
  }

  /**
   * Gets the proportion of new members included in a new generation.
   * 
   * @return the new weight
   */
  public double getNewWeight() {
    return newWeight;
  }
}
