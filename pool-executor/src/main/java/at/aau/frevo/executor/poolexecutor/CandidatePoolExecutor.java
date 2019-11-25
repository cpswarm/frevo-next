/**
 * File: CandidatePoolExecutor.java
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

package at.aau.frevo.executor.poolexecutor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.SplittableRandom;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import at.aau.frevo.Problem;
import at.aau.frevo.ProblemBuilder;
import at.aau.frevo.Representation;
import at.aau.frevo.Result;

/**
 * Candidate pool based executor.
 * <p>
 * Candidate pool executors create a number of threads and use these to evaluate candidates
 * concurrently. For efficient execution, the number of candidates should be significantly larger
 * than the {@code threadCount}.
 */
public class CandidatePoolExecutor extends PoolExecutor {

  protected long problemRandomSeed;

  /**
   * Creates a new {@code PoolExecutor} instance with the specified configuration.
   * 
   * @param builder        the {@code PoolExecutorBuilder} used for configuration
   * @param problemBuilder the {@code ProblemBuilder} used to create {@code Problem} instances
   * @param random         the random number generator used to create {@code Problem} instances
   */
  public CandidatePoolExecutor(PoolExecutorBuilder builder,
      ProblemBuilder<? extends Problem> problemBuilder, SplittableRandom random) {
    super(builder, problemBuilder);
    problemRandomSeed = random.nextLong();
  }

  @Override
  public <R extends Representation> ArrayList<Result<R>> evaluateRepresentations(List<R> candidates)
      throws InterruptedException {
    // create and run Callables
    var tasks = new ArrayList<Callable<Result<R>>>(problemVariantCount);
    for (var candidate : candidates) {
      tasks.add(() -> {
        double fitnessSum = 0;
        var problemRandom = new SplittableRandom(problemRandomSeed);

        for (var i = 0; i < problemVariantCount; i++) {
          var problem = problemBuilder.create(problemRandom.split());
          var fitness = problem.evaluateRepresentation(candidate);
          fitnessSum += fitness;
        }
        return new Result<R>(candidate, fitnessSum / problemVariantCount);
      });
    }
    var futures = executorService.invokeAll(tasks);

    // collect results
    var results = new ArrayList<Result<R>>();
    for (var future : futures) {
      try {
        results.add(future.get());
      } catch (ExecutionException e) {
        // TODO: although an ExecutionException should not occur, this should be improved
        throw new InterruptedException("Caused by ExecutionException. Fix this.");
      }
    }
    Collections.sort(results);
    return results;
  }
}
