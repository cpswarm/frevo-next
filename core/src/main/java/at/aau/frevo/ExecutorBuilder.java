/**
 * File: ExecutorBuilder.java
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

package at.aau.frevo;

import java.util.SplittableRandom;

/**
 * Abstract base class for all executor builders.
 * 
 * @param <T> the type of {@code Executor} associated with the {@code ExecutorBuilder}
 */
public abstract class ExecutorBuilder<T extends Executor> extends Builder<T> {

  /**
   * Creates a new {@code Executor} instance with the specified {@code ProblemBuilder} and random
   * number generator.
   * 
   * @param problemBuilder the {@code ProblemBuilder} to use
   * @param random         the random number generator used to create {@code Problem} instances
   * @return a new {@code Executor} instance
   */
  public abstract T create(ProblemBuilder<? extends Problem> problemBuilder,
      SplittableRandom random);

  @Override
  public ComponentType getType() {
    return ComponentType.EXECUTOR;
  }

  @Override
  public abstract ExecutorBuilder<T> cloneBuilder();
}
