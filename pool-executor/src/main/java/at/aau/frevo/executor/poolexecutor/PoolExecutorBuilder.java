/**
 * File: PoolExecutorBuilder.java
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

import java.util.SplittableRandom;
import at.aau.frevo.ComponentType;
import at.aau.frevo.ExecutorBuilder;
import at.aau.frevo.Problem;
import at.aau.frevo.ProblemBuilder;
import at.aau.frevo.Representation;

/**
 * Builder for {@link PoolExecutor} instances.
 */
public class PoolExecutorBuilder extends ExecutorBuilder<PoolExecutor> {

  protected int threadCount;
  protected int problemVariantCount;
  protected PoolType poolType;

  /**
   * Constructs a new {@code PoolExecutorBuilder} instance.
   */
  public PoolExecutorBuilder() {
    threadCount = 0;
    problemVariantCount = 1;
    poolType = PoolType.ProblemPool;
  }

  /**
   * Constructs a new {@code PoolExecutorBuilder} instance by copying the properties of the
   * specified instance.
   * 
   * @param source the source {@code PoolExecutorBuilder} instance
   */
  public PoolExecutorBuilder(PoolExecutorBuilder source) {
    threadCount = source.threadCount;
    problemVariantCount = source.problemVariantCount;
  }

  @Override
  public String getName() {
    return PoolExecutor.class.getName();
  }

  @Override
  public ComponentType getType() {
    return ComponentType.EXECUTOR;
  }

  @Override
  public PoolExecutor create(ProblemBuilder<? extends Problem> problemBuilder,
      SplittableRandom random) {
    if (poolType == PoolType.ProblemPool) {
      return new ProblemPoolExecutor(this, problemBuilder, random);
    } else {
      return new CandidatePoolExecutor(this, problemBuilder, random);
    }
  }

  @Override
  public PoolExecutorBuilder cloneBuilder() {
    return new PoolExecutorBuilder(this);
  }

  /**
   * Gets the number of threads used to evaluate candidate {@link Representation} instances.
   * 
   * @return the thread count
   */
  public int getThreadCount() {
    return threadCount;
  }

  /**
   * Sets the number of threads used to evaluate candidate {@link Representation} instances.
   * 
   * @param threadCount the thread count, {@code 0} indicates a work stealing pool
   * @return this {@code PoolExecutorBuilder} instance
   */
  public PoolExecutorBuilder setThreadCount(int threadCount) {
    this.threadCount = threadCount;
    return this;
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

  /**
   * Sets the number of different problem variants used to evaluate a candidate
   * {@link Representation}.
   * 
   * @param problemVariantCount the problem variant count
   * @return this {@code PoolExecutorBuilder} instance
   */
  public PoolExecutorBuilder setProblemVariantCount(int problemVariantCount) {
    this.problemVariantCount = problemVariantCount;
    return this;
  }

  /**
   * Gets the pool type, that is the {@code PoolType}.
   * 
   * @return the pool type
   */
  public PoolType getPoolType() {
    return poolType;
  }

  /**
   * Sets the pool type, that is the {@code PoolType}.
   * 
   * @param poolType the pool type
   * @return this {@code PoolExecutorBuilder} instance
   */
  public PoolExecutorBuilder setPoolType(PoolType poolType) {
    this.poolType = poolType;
    return this;
  }
}
