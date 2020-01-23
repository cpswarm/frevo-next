/**
 * File: XorRecipeExample.java
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

package at.aau.frevo.example.xor;

import at.aau.frevo.Recipe;
import at.aau.frevo.executor.simpleexecutor.SimpleExecutorBuilder;
import at.aau.frevo.method.nnga.NngaMethodBuilder;
import at.aau.frevo.problem.xor.XorProblemBuilder;
import at.aau.frevo.representation.fullymeshednet.ActivationFunction;
import at.aau.frevo.representation.fullymeshednet.FullyMeshedNetBuilder;
import at.aau.frevo.representation.fullymeshednet.FullyMeshedNetOpBuilder;

/**
 * Example how to use the {@code Recipe} to carry out evolution with minimal effort using the
 * {@code XorProblem}.
 */
public class XorRecipeExample {

  final static int CANDIDATE_COUNT = 100;
  final static int GENERATION_COUNT = 4000;

  final static protected long EVOLUTION_SEED = 1;
  final static protected long EVALUATION_SEED = 2;

  /**
   * Main method of the example.
   * 
   * @param args command line arguments, not used
   */
  public static void main(String[] args) {

    var recipe = new Recipe<>(
        new FullyMeshedNetBuilder().setActivationFunction(ActivationFunction.TANH)
            .setHiddenNodeCount(1).setIterationCount(2),
        new FullyMeshedNetOpBuilder().setWeightRange(6).setBiasRange(6).setInitialBiasRange(6)
            .setInitialWeightRange(6),
        new NngaMethodBuilder(), new SimpleExecutorBuilder(), new XorProblemBuilder(),
        EVOLUTION_SEED, EVALUATION_SEED);
    try {
      var startNanoTime = System.nanoTime();
      recipe.prepare(CANDIDATE_COUNT);
      var results = recipe.run(GENERATION_COUNT);
      var stopNanoTime = System.nanoTime();
      var bestResult = results.get(0);
      System.out.println("Best fitness: " + bestResult.getFitness());
      System.out.println("Total generations: " + recipe.getMethod().getTotalGenerationCount());
      System.out
          .println("Total time: " + (double) (stopNanoTime - startNanoTime) / 1000000 + "ms");
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}
