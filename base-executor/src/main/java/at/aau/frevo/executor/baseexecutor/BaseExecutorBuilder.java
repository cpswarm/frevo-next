/**
 * File: BaseExecutorBuilder.java
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

package at.aau.frevo.executor.baseexecutor;

import at.aau.frevo.ComponentType;
import at.aau.frevo.ExecutorBuilder;
import at.aau.frevo.Representation;

/**
 * Builder for {@link BaseExecutor} instances.
 */
public abstract class BaseExecutorBuilder<E extends BaseExecutor> extends ExecutorBuilder<E> {

  protected int problemVariantCount;
  protected boolean strict;
  protected long timeoutMilliSeconds;

  /**
   * Constructs a new {@code BaseExecutorBuilder} instance.
   */
  public BaseExecutorBuilder() {
    problemVariantCount = 1;
    strict = true;
    timeoutMilliSeconds = 0;
  }

  /**
   * Constructs a new {@code BaseExecutorBuilder} instance by copying the properties of the
   * specified instance.
   * 
   * @param source the source {@code BaseExecutorBuilder} instance
   */
  public BaseExecutorBuilder(BaseExecutorBuilder<E> source) {
    problemVariantCount = source.problemVariantCount;
    strict = source.strict;
    timeoutMilliSeconds = source.timeoutMilliSeconds;
  }

  @Override
  public ComponentType getType() {
    return ComponentType.EXECUTOR;
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
   * @return this {@code BaseExecutorBuilder} instance
   */
  public BaseExecutorBuilder<E> setProblemVariantCount(int problemVariantCount) {
    this.problemVariantCount = problemVariantCount;
    return this;
  }

  /**
   * Gets the flag indicating that the {@code BaseExecutor} is strict, that is, requires all problem
   * variants to be executed.
   * 
   * @return {@code true} if the {@code BaseExecutor} is strict
   */
  public boolean isStrict() {
    return strict;
  }

  /**
   * Sets the flag indicating that the {@code BaseExecutor} is strict, that is, requires all problem
   * variants to be executed.
   * 
   * @param strict the strict flag
   * @return this {@code BaseExecutorBuilder} instance
   */
  public BaseExecutorBuilder<E> setStrict(boolean strict) {
    this.strict = strict;
    return this;
  }

  /**
   * Gets the timeout milliseconds for the evaluation of {@link Representation} instances.
   * 
   * @return the timeout
   */
  public long getTimeoutMilliSeconds() {
    return timeoutMilliSeconds;
  }

  /**
   * Sets the timeout milliseconds for the evaluation of {@link Representation} instances.
   * 
   * @param timeoutMilliSeconds the timeout in milliseconds
   * @return this {@code BaseExecutorBuilder} instance
   */
  public BaseExecutorBuilder<E> setTimeoutMilliSeconds(long timeoutMilliSeconds) {
    this.timeoutMilliSeconds = timeoutMilliSeconds;
    return this;
  }
}
