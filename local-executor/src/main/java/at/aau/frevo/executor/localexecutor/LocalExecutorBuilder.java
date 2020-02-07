/**
 * File: LocalExecutorBuilder.java
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
import at.aau.frevo.Problem;
import at.aau.frevo.ProblemBuilder;
import at.aau.frevo.Representation;
import at.aau.frevo.executor.baseexecutor.BaseExecutorBuilder;

/**
 * Builder for {@link LocalExecutor} instances.
 */
public class LocalExecutorBuilder extends BaseExecutorBuilder<LocalExecutor> {

  protected int workerCount;

  /**
   * Constructs a new {@code LocalExecutorBuilder} instance.
   */
  public LocalExecutorBuilder() {
    workerCount = 0;
  }

  /**
   * Constructs a new {@code LocalExecutorBuilder} instance by copying the properties of the
   * specified instance.
   * 
   * @param source the source {@code LocalExecutorBuilder} instance
   */
  public LocalExecutorBuilder(LocalExecutorBuilder source) {
    super(source);
    workerCount = source.workerCount;
  }

  @Override
  public String getName() {
    return LocalExecutor.class.getName();
  }

  @Override
  public LocalExecutor create(ProblemBuilder<? extends Problem> problemBuilder,
      SplittableRandom random) {
    return new LocalExecutor(this, problemBuilder, random);
  }

  @Override
  public LocalExecutorBuilder cloneBuilder() {
    return new LocalExecutorBuilder(this);
  }

  /**
   * Gets the number of workers created to evaluate candidate {@link Representation} instances.
   * 
   * @return the worker count
   */
  public int getWorkerCount() {
    return workerCount;
  }

  /**
   * Sets the number of workers created to evaluate candidate {@link Representation} instances.
   * 
   * @param workerCount the worker count
   * @return this {@code LocalExecutorBuilder} instance
   */
  public LocalExecutorBuilder setWorkerCount(int workerCount) {
    this.workerCount = workerCount;
    return this;
  }

  @Override
  public LocalExecutorBuilder setProblemVariantCount(int problemVariantCount) {
    return (LocalExecutorBuilder) super.setProblemVariantCount(problemVariantCount);
  }

  @Override
  public BaseExecutorBuilder<LocalExecutor> setTimeoutMilliSeconds(long timeoutMilliSeconds) {
    return super.setTimeoutMilliSeconds(timeoutMilliSeconds);
  }
}
