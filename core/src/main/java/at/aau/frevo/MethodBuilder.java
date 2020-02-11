/**
 * File: MethodBuilder.java
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

import java.util.List;
import java.util.SplittableRandom;

/**
 * Abstract base class for all method builders.
 * 
 * @param <T> the type of {@code Method} associated with the {@code MethodBuilder}
 */
public abstract class MethodBuilder<T extends Method<? extends Representation>> extends Builder<T> {

  /**
   * Creates a new {@code Method} instance based on the current configuration.
   * 
   * @param <R>              the type of {@code Representation} to use
   * @param rankedCandidates the initial {@code Representation} instances to use
   * @param operator         the {@code Operator} to use
   * @param executor         the {@code Executor} to use
   * @param random           the random number generator to use
   * @return a new {@code Method} instance
   */
  public abstract <R extends Representation> Method<R> create(List<Result<R>> rankedCandidates,
      Operator<R> operator, Executor executor, SplittableRandom random);

  @Override
  public ComponentType getType() {
    return ComponentType.METHOD;
  }

  @Override
  public abstract MethodBuilder<T> cloneBuilder();
}
