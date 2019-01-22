/**
 * File: FullyMeshedNetContext.java
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
import at.aau.frevo.RepresentationContext;

/**
 * Context for {@link FullyMeshedNet}.
 */
public class FullyMeshedNetContext extends RepresentationContext<FullyMeshedNet> {

  protected SplittableRandom random;

  protected float[] state;
  protected float[] sums;

  /**
   * Creates a new {@code FullyMeshedNetContext} instance associated with the given
   * {@code FullyMeshedNet}.
   * 
   * @param representation the associated {@code FullyMeshedNet}
   * @param random         the random number generator to use
   */
  public FullyMeshedNetContext(FullyMeshedNet representation, SplittableRandom random) {
    super(representation);
    this.random = random;

    state = new float[representation.nodeCount];
    sums = new float[representation.nodeCount];
  }

  @Override
  public void calculate(float[] input, float[] output) {
    var activationFunction = representation.activationFunction;
    var weights = representation.weights;
    var biases = representation.biases;
    var randomBiases = representation.randomBiases;
    var iterationCount = representation.iterationCount;
    var inputSize = representation.getInputCount();
    var outputSize = representation.getOutputCount();
    var nodeCount = representation.nodeCount;
    var hiddenNodeCount = representation.hiddenNodeCount;

    // copy in input
    for (int i = 0; i < inputSize; i++) {
      state[i] = input[i];
    }

    for (int k = 0; k < iterationCount; k++) {

      // calculate sums
      for (int i = inputSize; i < nodeCount; i++) {
        float sum = 0;
        for (int j = 0; j < nodeCount; j++) {
          sum += weights[i][j] * state[j];
        }
        sums[i] = biases[i] + sum;

        var randomBias = randomBiases[i];
        if (randomBias > 0) {
          sums[i] += random.nextDouble(-randomBias, randomBias);
        }
      }

      // activate
      switch (activationFunction) {
        case SIGMOID:
          for (int i = inputSize; i < nodeCount; i++) {
            state[i] = (float) (1.0f / (1.0f + Math.exp(-sums[i])));
          }
          break;

        case TANH:
          for (int i = inputSize; i < nodeCount; i++) {
            state[i] = (float) Math.tanh(sums[i]);
          }
          break;

        case RELU:
        default:
          for (int i = inputSize; i < nodeCount; i++) {
            float x = sums[i];
            if (x > 1) {
              state[i] = 1;
            } else if (x < 0) {
              state[i] = 0;
            } else {
              state[i] = x;
            }
          }
          break;
      }
    }

    // copy out output
    int offset = inputSize + hiddenNodeCount;
    for (int i = 0; i < outputSize; i++) {
      output[i] = state[offset + i];
    }
  }

}
