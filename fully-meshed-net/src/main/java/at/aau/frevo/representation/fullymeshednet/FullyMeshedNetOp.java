/**
 * File: FullyMeshedNetOp.java
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

package at.aau.frevo.representation.fullymeshednet;

import java.util.SplittableRandom;
import at.aau.frevo.Operator;
import at.aau.frevo.RepresentationBuilder;

/**
 * Operator for {@link FullyMeshedNet}.
 */
public class FullyMeshedNetOp extends Operator<FullyMeshedNet> {

  protected RepresentationBuilder<FullyMeshedNet> representationBuilder;
  protected float initialWeightRange;
  protected float intialBiasRange;
  protected float initialRandomBiasRange;
  protected float weightRange;
  protected float biasRange;
  protected float randomBiasRange;

  protected double directMutationProbability;
  protected double directMutationSeverity;
  protected double proportionalMutationProbability;
  protected double proportionalMutationSeverity;

  /**
   * Creates a new {@code FullyMeshedNetOp} instance using the specified configuration.
   * 
   * @param builder               the {@code FullyMeshedNetOpBuilder} used for configuration
   * @param representationBuilder the {@code RepresentationBuilder} used for creating new
   *                              {@code FullyMeshedNet} instances
   * @param random                the random number generator to use
   */
  public FullyMeshedNetOp(FullyMeshedNetOpBuilder builder,
      RepresentationBuilder<FullyMeshedNet> representationBuilder, SplittableRandom random) {
    super(random);
    this.representationBuilder = representationBuilder;

    initialWeightRange = builder.getInitialWeightRange();
    intialBiasRange = builder.getInitialBiasRange();
    initialRandomBiasRange = builder.getInitialRandomBiasRange();
    weightRange = builder.getWeightRange();
    biasRange = builder.getBiasRange();
    randomBiasRange = builder.getRandomBiasRange();

    directMutationProbability = builder.getDirectMutationProbability();
    directMutationSeverity = builder.getDirectMutationSeverity();
    proportionalMutationProbability = builder.getProportionalMutationProbability();
    proportionalMutationSeverity = builder.getProportionalMutationSeverity();
  }

  /**
   * Mutates a section of the specified array, starting at {@code startIndex} and stopping before
   * {@code stopIndex}.
   * 
   * @param array      the array to mutate
   * @param startIndex the start index
   * @param stopIndex  the stop index
   */
  protected void mutateArray(float[] array, int startIndex, int stopIndex) {
    for (int i = startIndex; i < stopIndex; i++) {
      if (random.nextDouble() < directMutationProbability) {
        array[i] += random.nextDouble(-directMutationSeverity, directMutationSeverity);
      }
      if (random.nextDouble() < proportionalMutationProbability) {
        var x = Math.abs(array[i] * proportionalMutationSeverity);
        array[i] += random.nextDouble(-x, x);
      }
    }
  }

  protected void enforceArrayValueRanges(float[] array, float minValue, float maxValue) {
    for (int i = 0; i < array.length; i++) {
      array[i] = Math.max(Math.min(array[i], maxValue), minValue);
    }
  }

  @Override
  public FullyMeshedNet operator0() {
    var net = representationBuilder.create();
    var weights = net.getWeights();
    var biases = net.getBiases();
    var randomBiases = net.getRandomBiases();
    var nodeCount = net.getNodeCount();
    for (int i = net.getInputCount(); i < nodeCount; i++) {
      for (int j = 0; j < nodeCount; j++) {
        weights[i][j] = (float) random.nextDouble(-initialWeightRange, initialWeightRange);
      }
      enforceArrayValueRanges(weights[i], -weightRange, weightRange);
      biases[i] = (float) random.nextDouble(-intialBiasRange, intialBiasRange);
      if (initialRandomBiasRange > 0) {
        randomBiases[i] =
            (float) random.nextDouble(-initialRandomBiasRange, initialRandomBiasRange);
      }
    }
    enforceArrayValueRanges(biases, -biasRange, biasRange);
    enforceArrayValueRanges(randomBiases, -randomBiasRange, biasRange);
    return net;
  }

  @Override
  public FullyMeshedNet operator1(FullyMeshedNet r) {
    var mutantNet = new FullyMeshedNet(r);
    var inputCount = mutantNet.getInputCount();
    var nodeCount = mutantNet.getNodeCount();
    mutateArray(mutantNet.getBiases(), inputCount, nodeCount);
    var weights = mutantNet.getWeights();
    for (int i = inputCount; i < nodeCount; i++) {
      mutateArray(weights[i], 0, nodeCount);
      enforceArrayValueRanges(weights[i], -weightRange, weightRange);
    }
    if (initialRandomBiasRange > 0) {
      mutateArray(mutantNet.getRandomBiases(), inputCount, nodeCount);
    }
    enforceArrayValueRanges(mutantNet.getBiases(), -biasRange, biasRange);
    enforceArrayValueRanges(mutantNet.getRandomBiases(), -randomBiasRange, biasRange);
    return mutantNet;
  }

  @Override
  public FullyMeshedNet operator2(FullyMeshedNet net1, FullyMeshedNet net2) {
    if (random.nextBoolean()) {
      var t = net1;
      net1 = net2;
      net2 = t;
    }

    var crossNet = new FullyMeshedNet(net1);
    var nodeCount = net1.getNodeCount();
    int startIndex = random.nextInt(net1.getInputCount(), nodeCount - 1);
    int stopIndex = random.nextInt(startIndex + 1, nodeCount);

    var crossWeights = crossNet.getWeights();
    var crossBiases = crossNet.getBiases();
    var crossRandomBiases = crossNet.getRandomBiases();

    var r2Weights = net2.getWeights();
    var r2Biases = net2.getBiases();
    var r2RandomBiases = net2.getRandomBiases();

    for (int i = startIndex; i < stopIndex; i++) {
      for (int j = 0; j < nodeCount; j++) {
        crossWeights[i][j] = r2Weights[i][j];
      }
      crossBiases[i] = r2Biases[i];
      crossRandomBiases[i] = r2RandomBiases[i];
    }
    return crossNet;
  }

  @Override
  public double difference(FullyMeshedNet net1, FullyMeshedNet net2) {
    double diff = 0;
    var inputCount = net1.getInputCount();
    var nodeCount = net1.getNodeCount();

    var r1Weights = net1.getWeights();
    var r1Biases = net1.getBiases();
    var r1RandomBiases = net1.getRandomBiases();

    var r2Weights = net2.getWeights();
    var r2Biases = net2.getBiases();
    var r2RandomBiases = net2.getRandomBiases();

    for (int i = inputCount; i < nodeCount; i++) {
      for (int j = 0; j < nodeCount; j++) {
        diff += Math.abs(r1Weights[i][j] - r2Weights[i][j]);
      }
      diff += Math.abs(r1Biases[i] - r2Biases[i]);
      diff += Math.abs(r1RandomBiases[i] - r2RandomBiases[i]);
    }
    return diff;
  }

  /**
   * Gets the associated {@code RepresentationBuilder}.
   * 
   * @return the {@code RepresentationBuilder}
   */
  public RepresentationBuilder<FullyMeshedNet> getFullyMeshedNetBuilder() {
    return representationBuilder;
  }

  /**
   * Gets the initial weight range.
   * 
   * @return the initial weight range
   */
  public float getInitialWeightRange() {
    return initialWeightRange;
  }

  /**
   * Gets the initial bias range.
   * 
   * @return the initial bias range
   */
  public float getInitialBiasRange() {
    return intialBiasRange;
  }

  /**
   * Gets the intial random bias range.
   * 
   * @return the initial random bias range
   */
  public float getInitialRandomBiasRange() {
    return initialRandomBiasRange;
  }

  /**
   * Gets the direct mutation probability.
   * 
   * @return the direct mutation probability
   */
  public double getDirectMutationProbability() {
    return directMutationProbability;
  }

  /**
   * Gets the direct mutation severity.
   * 
   * @return the direct mutation severity
   */
  public double getDirectMutationSeverity() {
    return directMutationSeverity;
  }

  /**
   * Gets the proportional mutation probability.
   * 
   * @return the proportional mutation probability
   */
  public double getProportionalMutationProbability() {
    return proportionalMutationProbability;
  }

  /**
   * Gets the proportional mutation severity.
   * 
   * @return the proportional mutation severity
   */
  public double getProportionalMutationSeverity() {
    return proportionalMutationSeverity;
  }
}
