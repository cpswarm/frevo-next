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

import java.util.SplittableRandom;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import at.aau.frevo.Evaluation;
import at.aau.frevo.Problem;
import at.aau.frevo.ProblemBuilder;
import at.aau.frevo.Representation;
import at.aau.frevo.executor.baseexecutor.BaseExecutor;

/**
 * Uses a work-stealing pool to run evaluations on a number of workers.
 * <p>
 * Out of the box, {@link LocalWorker} is provided to do the work, however subclasses could provide
 * different functionality.
 */
public class LocalExecutor extends BaseExecutor {

  protected ExecutorService executorService = null;
  protected int workerCount;

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
    super(builder, problemBuilder, random);

    // use available processors if worker count is zero
    workerCount = builder.getWorkerCount();
    if (workerCount == 0) {
      workerCount = Runtime.getRuntime().availableProcessors();
    }
    executorService = Executors.newWorkStealingPool();
  }

  @Override
  protected <R extends Representation> void dispatchEvaluation(
      ArrayBlockingQueue<Evaluation<R>> evaluationQueue, CountDownLatch evaluationCountDownLatch) {
    for (int i = 0; i < workerCount; ++i) {
      executorService
          .submit(new LocalWorker<R>(i, evaluationQueue, evaluationCountDownLatch, problemBuilder));
    }
  }

  /**
   * Gets the number of workers created to evaluate candidate {@link Representation} instances.
   * 
   * @return the worker count
   */
  public int getWorkerCount() {
    return workerCount;
  }
}
