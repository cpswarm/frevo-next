/**
 * File: ProblemPoolExecutor.java
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
 * Problem pool based executor.
 * <p>
 * Problem pool executors create a number of problem variants and use these to evaluate candidates
 * concurrently. For efficient execution, the {@code problemVairantCount} should be significantly
 * larger than the {@code threadCount}.
 */
public class ProblemPoolExecutor extends PoolExecutor {

  protected ArrayList<Problem> problems = new ArrayList<>();

  /**
   * Creates a new {@code PoolExecutor} instance with the specified configuration.
   * 
   * @param builder        the {@code PoolExecutorBuilder} used for configuration
   * @param problemBuilder the {@code ProblemBuilder} used to create {@code Problem} instances
   * @param random         the random number generator used to create {@code Problem} instances
   */
  public ProblemPoolExecutor(PoolExecutorBuilder builder,
      ProblemBuilder<? extends Problem> problemBuilder, SplittableRandom random) {
    super(builder, problemBuilder);

    // create Problem pool for evaluation
    var problemRadom = new SplittableRandom(random.nextLong());
    for (var i = 0; i < problemVariantCount; i++) {
      problems.add(problemBuilder.create(problemRadom.split()));
    }
  }

  @Override
  public <R extends Representation> ArrayList<Result<R>> evaluateRepresentations(List<R> candidates)
      throws InterruptedException {

    // create and run Callables
    var tasks = new ArrayList<Callable<double[]>>(problemVariantCount);
    for (var problem : problems) {
      tasks.add(() -> {
        var fitnessValues = new double[candidates.size()];
        int index = 0;
        for (var candidate : candidates) {
          try {
            fitnessValues[index] = problem.evaluateRepresentation(candidate);
          } catch (Exception e) {
            // TODO: handle errors better?
            e.printStackTrace();
            fitnessValues[index] = Double.NaN;
          }
          index++;
        }
        return fitnessValues;
      });
    }
    var futures = executorService.invokeAll(tasks);

    // collect fitness values
    var candidateCount = candidates.size();
    var sums = new double[candidateCount];
    var counts = new int[candidateCount];
    for (var future : futures) {
      try {
        var fitnessValues = future.get();
        for (var i = 0; i < candidateCount; i++) {
          var fitness = fitnessValues[i];
          if (!Double.isNaN(fitness)) {
            sums[i] += fitness;
            counts[i]++;
          }
        }
      } catch (ExecutionException e) {
        // TODO: although an ExecutionException should not occur, this should be improved
        e.printStackTrace();
      }
    }

    // build results
    var results = new ArrayList<Result<R>>();
    var index = 0;
    for (var candidate : candidates) {
      results.add(new Result<R>(candidate, sums[index] / Math.max(1, counts[index])));
      index++;
    }
    Collections.sort(results);
    return results;
  }
}
