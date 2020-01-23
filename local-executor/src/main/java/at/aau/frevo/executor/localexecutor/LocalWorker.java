/**
 * File: LocalWorker.java
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

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import at.aau.frevo.Evaluation;
import at.aau.frevo.Problem;
import at.aau.frevo.ProblemBuilder;
import at.aau.frevo.Representation;

/**
 * Example worker class for {@link LocalExecutor} that carries out evaluations on a local thread.
 * 
 * @param <R> the type of {@code Representation} to be evaluated
 */
public class LocalWorker<R extends Representation> implements Runnable {

  protected final static long POLL_TIMEOUT_MILLISECONDS = 10;

  protected int id;
  protected BlockingQueue<Evaluation<R>> evaluationQueue;
  protected CountDownLatch evaluationCountDownLatch;
  protected ProblemBuilder<? extends Problem> problemBuilder;

  /**
   * Creates a new {@code LocalWorker} instance using the specified parameters.
   * 
   * @param id                       the id of this worker
   * @param evaluationQueue          the queue of {@code Evaluation} instances to be evaluated
   * @param evaluationCountDownLatch count down latch used to signal work complete
   * @param problemBuilder           the {@code ProblemBuilder} used to create {@code Problem}
   *                                 instances
   */
  public LocalWorker(int id, BlockingQueue<Evaluation<R>> evaluationQueue,
      CountDownLatch evaluationCountDownLatch, ProblemBuilder<? extends Problem> problemBuilder) {
    this.id = id;
    this.evaluationQueue = evaluationQueue;
    this.evaluationCountDownLatch = evaluationCountDownLatch;
    this.problemBuilder = problemBuilder;
  }

  @Override
  public void run() {
    try {
      // carry out evaluations while there is work to do
      while (evaluationCountDownLatch.getCount() > 0) {
        var evaluation = evaluationQueue.poll(POLL_TIMEOUT_MILLISECONDS, TimeUnit.MILLISECONDS);
        if (evaluation != null) {
          try {
            var problem = problemBuilder.create(evaluation.getSeed());
            evaluation.setFitness(problem.evaluateRepresentation(evaluation.getRepresentation()));
            evaluationCountDownLatch.countDown();
          } catch (Exception e) {
            // if something goes wrong, put back th evaluation so that it can be done again
            evaluationQueue.put(evaluation);
          }
        }
      }
    } catch (InterruptedException e) {
    }
  }
}
