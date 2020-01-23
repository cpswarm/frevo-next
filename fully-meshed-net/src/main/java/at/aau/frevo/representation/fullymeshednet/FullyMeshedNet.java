/**
 * File: FullyMeshedNet.java
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
import at.aau.frevo.Representation;

/**
 * Fully meshed neural network representation.
 * <p>
 * All nodes may use may use the outputs of other nodes or network inputs as inputs.
 * <p>
 * All nodes are activated the specified number of iteration counts before the outputs are copied
 * out of the network.
 */
public class FullyMeshedNet extends Representation {

  protected ActivationFunction activationFunction;
  protected int hiddenNodeCount;
  protected int iterationCount;
  protected int nodeCount;

  protected float[][] weights;
  protected float[] biases;
  protected float[] randomBiases;

  /**
   * Creates a new {@code FullyMeshedNet} instance using the specified configuration.
   * 
   * @param builder the {@code FullyMeshedNetBuilder} used for configuration
   */
  public FullyMeshedNet(FullyMeshedNetBuilder builder) {
    super(builder);

    activationFunction = builder.getActivationFunction();
    hiddenNodeCount = builder.getHiddenNodeCount();
    iterationCount = builder.getIterationCount();

    nodeCount = inputCount + outputCount + hiddenNodeCount;

    weights = new float[nodeCount][nodeCount];
    biases = new float[nodeCount];
    randomBiases = new float[nodeCount];
  }

  /**
   * Creates a new {@code FullyMeshedNet} instance based on an existing instance.
   * 
   * @param source the source {@code FullyMeshedNet}
   */
  public FullyMeshedNet(FullyMeshedNet source) {
    super(source);

    activationFunction = source.activationFunction;
    hiddenNodeCount = source.hiddenNodeCount;
    iterationCount = source.iterationCount;
    nodeCount = source.nodeCount;

    weights = new float[nodeCount][];
    for (var i = 0; i < nodeCount; i++) {
      weights[i] = source.weights[i].clone();
    }

    biases = source.biases.clone();
    randomBiases = source.randomBiases.clone();
  }

  @Override
  public FullyMeshedNetContext createContext(SplittableRandom random) {
    return new FullyMeshedNetContext(this, random);
  }

  @Override
  public String getHash() {
    double sum = 0;
    for (var weightRow : weights) {
      for (float weight : weightRow) {
        sum += weight;
      }
    }
    for (float bias : biases) {
      sum += bias;
    }
    for (float randomBias : randomBiases) {
      sum += randomBias;
    }
    return Integer.toHexString(Double.toString(sum).hashCode());
  }

  @Override
  public Representation cloneRepresentation() {
    return new FullyMeshedNet(this);
  }

  /**
   * Gets the activation function.
   * 
   * @return the activation function
   */
  public ActivationFunction getActivationFunction() {
    return activationFunction;
  }

  /**
   * Gets the hidden node count.
   * 
   * @return the hidden node count
   */
  public int getHiddenNodeCount() {
    return hiddenNodeCount;
  }

  /**
   * Gets the iteration count.
   * 
   * @return the iteration count
   */
  public int getIterationCount() {
    return iterationCount;
  }

  /**
   * Gets the node count.
   * 
   * @return the node count
   */
  public int getNodeCount() {
    return nodeCount;
  }

  /**
   * Gets the weights.
   * 
   * @return the weights
   */
  public float[][] getWeights() {
    return weights;
  }

  /**
   * Gets the biases.
   * 
   * @return the biases
   */
  public float[] getBiases() {
    return biases;
  }

  /**
   * Gets the magnitude of the random biases.
   * 
   * @return the magnitude of random biases
   */
  public float[] getRandomBiases() {
    return randomBiases;
  }

  @Override
  public int compareTo(Representation other) {
    // carry out initial comparison
    var result = super.compareTo(other);
    if (result != 0) {
      return result;
    }

    var otherNet = (FullyMeshedNet) other;

    // first compare basic properties
    if (activationFunction.ordinal() != otherNet.activationFunction.ordinal()) {
      return activationFunction.ordinal() - otherNet.activationFunction.ordinal();
    }
    if (hiddenNodeCount != otherNet.hiddenNodeCount) {
      return hiddenNodeCount - otherNet.hiddenNodeCount;
    }
    if (iterationCount != otherNet.iterationCount) {
      return iterationCount - otherNet.iterationCount;
    }

    // compare weights
    for (int i = 0; i < nodeCount; i++) {
      for (int j = 0; j < nodeCount; j++) {
        if (weights[i][j] != otherNet.weights[i][j]) {
          return (weights[i][j] < otherNet.weights[i][j]) ? -1 : 1;
        }
      }
    }

    // compare biases
    for (int i = 0; i < nodeCount; i++) {
      if (biases[i] != otherNet.biases[i]) {
        return (biases[i] < otherNet.biases[i]) ? -1 : 1;
      }
    }

    // compare random biases
    for (int i = 0; i < nodeCount; i++) {
      if (randomBiases[i] != otherNet.randomBiases[i]) {
        return (randomBiases[i] < otherNet.randomBiases[i]) ? -1 : 1;
      }
    }

    return 0;
  }
}
