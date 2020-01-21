/**
 * File: Problem.java
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

/**
 * Abstract base class for all problems.
 * <p>
 * Problems are used to evaluate representations in specific scenarios.
 */
public abstract class Problem extends Component {

  protected long seed;

  /**
   * Creates a new {@code Problem} instance with the specified seed.
   * 
   * @param seed the seed to use
   */
  public Problem(long seed) {
    this.seed = seed;
  }

  @Override
  public ComponentType getType() {
    return ComponentType.PROBLEM;
  }

  /**
   * Evaluates a {@code Representation} and computes a fitness value. This method may be called
   * multiple times and should always return the same result for identical {@code Representation}
   * instances.
   * 
   * @param representation the {@code Representation} to evaluate
   * @return the fitness value. Higher is better.
   */
  public abstract double evaluateRepresentation(Representation representation);
}
