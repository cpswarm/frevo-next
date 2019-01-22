/**
 * File: PoolExecutor.java
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

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import at.aau.frevo.Executor;
import at.aau.frevo.Problem;
import at.aau.frevo.ProblemBuilder;
import at.aau.frevo.Representation;

/**
 * Problem pool based executor.
 * <p>
 * Pool executors create a number of problem variants and use these to evaluate candidates
 * concurrently. For efficient execution, the {@code problemVairantCount} should be significantly
 * larger than the {@code threadCount}.
 */
public abstract class PoolExecutor extends Executor {

  protected int threadCount;
  protected int problemVariantCount;
  protected ExecutorService executorService = null;

  /**
   * Creates a new {@code PoolExecutor} instance with the specified configuration.
   * 
   * @param builder        the {@code PoolExecutorBuilder} used for configuration
   * @param problemBuilder the {@code ProblemBuilder} used to create {@code Problem} instances
   */
  public PoolExecutor(PoolExecutorBuilder builder,
      ProblemBuilder<? extends Problem> problemBuilder) {
    super(problemBuilder);
    threadCount = builder.getThreadCount();
    problemVariantCount = builder.getProblemVariantCount();
    if (threadCount == 0) {
      executorService = Executors.newWorkStealingPool();
    } else {
      executorService = Executors.newFixedThreadPool(threadCount);
    }
  }

  /**
   * Gets the thread count.
   * 
   * @return the thread count, {@code 0} indicates a work stealing pool
   */
  public int getThreadCount() {
    return threadCount;
  }

  /**
   * Gets the number of different problem variants used to evaluate a candidate
   * {@link Representation}.
   * 
   * @return the problem variant count
   */
  public int getProblemVariantCount() {
    return problemVariantCount;
  }
}
