/**
 * File: SimpleExecutorBuilder.java
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

import java.util.SplittableRandom;
import at.aau.frevo.ComponentType;
import at.aau.frevo.ExecutorBuilder;
import at.aau.frevo.Problem;
import at.aau.frevo.ProblemBuilder;
import at.aau.frevo.Representation;

/**
 * Builder for {@link SimpleExecutor} instances.
 */
public class SimpleExecutorBuilder extends ExecutorBuilder<SimpleExecutor> {

  protected int problemVariantCount;

  /**
   * Constructs a new {@code SimpleExecutorBuilder} instance.
   */
  public SimpleExecutorBuilder() {
    problemVariantCount = 1;
  }

  /**
   * Constructs a new {@code SimpleExecutorBuilder} instance by copying the properties of the
   * specified instance.
   * 
   * @param source the source {@code SimpleExecutorBuilder} instance
   */
  public SimpleExecutorBuilder(SimpleExecutorBuilder source) {
    problemVariantCount = source.problemVariantCount;
  }

  @Override
  public String getName() {
    return SimpleExecutor.class.getName();
  }

  @Override
  public ComponentType getType() {
    return ComponentType.EXECUTOR;
  }

  @Override
  public SimpleExecutor create(ProblemBuilder<? extends Problem> problemBuilder,
      SplittableRandom random) {
    return new SimpleExecutor(this, problemBuilder, random);
  }

  @Override
  public SimpleExecutorBuilder cloneBuilder() {
    return new SimpleExecutorBuilder(this);
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
   * @return this {@code SimpleExecutorBuilder} instance
   */
  public SimpleExecutorBuilder setProblemVariantCount(int problemVariantCount) {
    this.problemVariantCount = problemVariantCount;
    return this;
  }
}
