/**
 * File: SimpleExecutor.java
 * 
 * Copyright (C) 2020 FREVO project contributors
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

package at.aau.frevo.executor.simpleexecutor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.SplittableRandom;
import at.aau.frevo.Executor;
import at.aau.frevo.Problem;
import at.aau.frevo.ProblemBuilder;
import at.aau.frevo.Representation;
import at.aau.frevo.Result;

/**
 * Simple executor that executes evaluations on the current thread.
 */
public class SimpleExecutor extends Executor {

  protected int problemVariantCount;
  protected long problemRandomSeed;

  /**
   * Creates a new {@code SimpleExecutor} instance with the specified configuration.
   * 
   * @param builder        the {@code SimpleExecutorBuilder} used for configuration
   * @param problemBuilder the {@code ProblemBuilder} used to create {@code Problem} instances
   * @param random         the random number generator used to create seeds for {@code Problem}
   *                       instances
   */
  public SimpleExecutor(SimpleExecutorBuilder builder,
      ProblemBuilder<? extends Problem> problemBuilder, SplittableRandom random) {
    super(problemBuilder);
    problemVariantCount = builder.getProblemVariantCount();
    problemRandomSeed = random.nextLong();
  }

  @Override
  public <R extends Representation> List<Result<R>> evaluateRepresentations(
      List<R> representations) {
    var results = new ArrayList<Result<R>>(representations.size() * problemVariantCount);

    // evaluate the representations sequentially on this thread
    for (var representation : representations) {
      var problemRandom = new SplittableRandom(problemRandomSeed);
      double fitnessSum = 0;
      for (var i = 0; i < problemVariantCount; i++) {
        var problem = problemBuilder.create(problemRandom.nextLong());
        fitnessSum += problem.evaluateRepresentation(representation);
      }

      // create a result with average fitness
      results.add(new Result<R>(representation, fitnessSum / problemVariantCount));
    }
    Collections.sort(results);
    return results;
  }
}
