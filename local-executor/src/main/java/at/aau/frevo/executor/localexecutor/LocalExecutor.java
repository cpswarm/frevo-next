/**
 * File: LocalExecutor.java
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

package at.aau.frevo.executor.localexecutor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.SplittableRandom;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import at.aau.frevo.Evaluation;
import at.aau.frevo.Executor;
import at.aau.frevo.Problem;
import at.aau.frevo.ProblemBuilder;
import at.aau.frevo.Representation;
import at.aau.frevo.Result;

/**
 * Uses a work-stealing pool to run evaluations on a number of workers.
 * <p>
 * Out of the box, {@link LocalWorker} is provided to do the work, however subclasses could provide
 * different functionality.
 */
public class LocalExecutor extends Executor {

  protected ExecutorService executorService = null;

  protected int workerCount;
  protected int problemVariantCount;
  protected long problemRandomSeed;
  protected long timeoutMilliSeconds;

  /**
   * Creates a new {@code LocalExecutor} instance with the specified configuration.
   * 
   * @param builder        the {@code LocalExecutorBuilder} used for configuration
   * @param problemBuilder the {@code ProblemBuilder} used to create {@code Problem} instances
   * @param random         the random number generator used to create seeds for {@code Problem}
   *                       instances
   */
  public LocalExecutor(LocalExecutorBuilder builder,
      ProblemBuilder<? extends Problem> problemBuilder, SplittableRandom random) {
    super(problemBuilder);
    problemVariantCount = builder.getProblemVariantCount();
    workerCount = builder.getWorkerCount();

    // use available processors if worker count is zero
    if (workerCount == 0) {
      workerCount = Runtime.getRuntime().availableProcessors();
    }
    timeoutMilliSeconds = builder.getTimeoutMilliSeconds();
    problemRandomSeed = random.nextLong();
    executorService = Executors.newWorkStealingPool();
  }

  @Override
  public <R extends Representation> List<Result<R>> evaluateRepresentations(List<R> representations)
      throws InterruptedException {

    var evaluationCount = representations.size() * problemVariantCount;
    var evaluations = new ArrayList<Evaluation<R>>(evaluationCount);
    var evaluationQueue = new ArrayBlockingQueue<Evaluation<R>>(evaluationCount);
    var problemRandom = new SplittableRandom(problemRandomSeed);
    var evalutionSeeds = new ArrayList<Long>(representations.size());

    // create evaluations
    for (var i = 0; i < problemVariantCount; i++) {
      var evaluationSeed = problemRandom.nextLong();
      evalutionSeeds.add(evaluationSeed);
      for (var representation : representations) {
        var evaluation = new Evaluation<R>(representation, evaluationSeed);
        evaluations.add(evaluation);
        evaluationQueue.add(evaluation);
      }
    }

    final var evaluationCountDownLatch = new CountDownLatch(evaluationCount);

    startWorkers(evaluationQueue, evaluationCountDownLatch);

    // wait for workers to complete
    if (timeoutMilliSeconds == 0) {
      evaluationCountDownLatch.await();
    } else {
      if (!evaluationCountDownLatch.await(timeoutMilliSeconds, TimeUnit.MILLISECONDS)) {
        // if the timeout occured, clear queue and try to terminate workers
        evaluationQueue.clear();
        while (evaluationCountDownLatch.getCount() > 0) {
          evaluationCountDownLatch.countDown();
        }
      }
    }

    // create results
    // start by collating results by representation
    var resultHashtable = new HashMap<R, ArrayList<Evaluation<R>>>();
    for (var evaluation : evaluations) {
      // only consider evaluations where a fitness was set
      if (evaluation.getFitness() >= 0) {
        var resultSet = resultHashtable.get(evaluation.getRepresentation());
        if (resultSet == null) {
          resultSet = new ArrayList<>();
          resultHashtable.put(evaluation.getRepresentation(), resultSet);
        }
        resultSet.add(evaluation);
      }
    }

    // average fitness
    var results = new ArrayList<Result<R>>();
    resultHashtable.forEach((k, v) -> {
      double fitnessSum = 0;
      for (var evaluation : v) {
        fitnessSum += evaluation.getFitness();
      }
      results.add(new Result<R>(k, fitnessSum / v.size()));
    });

    Collections.sort(results);
    return results;
  }

  /**
   * Starts workers to actually carry out the evaluation of {@code Representation} instances.
   * <p>
   * This is provided as a separate method to allow subclasse to easily start other sorts of
   * workers.
   * 
   * @param <R>                      the type of {@code Representation} to evaluate
   * @param evaluationQueue          the queue of {@code Evaluation} instances
   * @param evaluationCountDownLatch the count down latch used for signalling the completion of work
   */
  protected <R extends Representation> void startWorkers(
      ArrayBlockingQueue<Evaluation<R>> evaluationQueue, CountDownLatch evaluationCountDownLatch) {
    for (int i = 0; i < workerCount; ++i) {
      executorService
          .submit(new LocalWorker<R>(i, evaluationQueue, evaluationCountDownLatch, problemBuilder));
    }
  }
}
