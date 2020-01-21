/**
 * File: ProblemBuilder.java
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
 * Abstract base class for all problem builders.
 * 
 * @param <T> the type of {@code Problem} associated with this {@code ProblemBuilder}
 */
public abstract class ProblemBuilder<T extends Problem> extends Builder<T> {

  /**
   * Creates a new {@code Problem} instance based on the current configuration.
   * 
   * @param seed the seed used by the {@code Problem}
   * @return a new {@code Problem} instance
   */
  public abstract Problem create(long seed);

  @Override
  public ComponentType getType() {
    return ComponentType.PROBLEM;
  }

  @Override
  public abstract ProblemBuilder<T> cloneBuilder();

  /**
   * Gets the number of inputs required by {@code Representation} instances compatible with this
   * {@code Problem}.
   * 
   * @return the number of inputs required by {@code Representation} instances compatible with this
   *         {@code Problem}
   */
  public abstract int getRepresentationInputCount();

  /**
   * Gets the number of outputs produced by {@code Representation} instances compatible with this
   * {@code Problem}.
   * 
   * @return the number of outputs produced by {@code Representation} instances compatible with this
   *         {@code Problem}
   */
  public abstract int getRepresentationOutputCount();

  /**
   * Gets the maximum fitness value obtainable in this {@code Problem}. This value is used to stop
   * evolution early if the maximum fitness has been reached. If known, implementations should
   * override this.
   * 
   * @return the maximum fitness value
   */
  public double getMaximumFitness() {
    return Double.MAX_VALUE;
  }
}
