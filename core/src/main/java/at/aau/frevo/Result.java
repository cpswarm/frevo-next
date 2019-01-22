/**
 * File: Result.java
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
 * Links a {@code Representation} instance with a fitness value.
 * 
 * @param <R> the type of {@code Representation} included in the {@code Result}
 */
public class Result<R extends Representation> implements Comparable<Result<R>> {

  protected R representation;
  protected double fitness;

  /**
   * Creates a new {@code Result} instance with the specified {@code Representation} and fitness
   * value.
   * 
   * @param representation the {@code Representation}
   * @param fitness        the fitness value
   */
  public Result(R representation, double fitness) {
    this.representation = representation;
    this.fitness = fitness;
  }

  /**
   * Gets the {@code Representation}.
   * 
   * @return the {@code Representation}
   */
  public R getRepresentation() {
    return representation;
  }

  /**
   * Gets the fitness value.
   * 
   * @return the fitness value. Higher is better.
   */
  public double getFitness() {
    return fitness;
  }

  /**
   * Compares this object with another {@code Result} instance.
   * 
   * @param other the other {@code Result}
   * @return {@code -1} if this object's fitness is higher, {@code 1} if the other fitness value is higer, {@code 0} if they
   *         are the same.
   */
  @Override
  public int compareTo(Result<R> other) {
    if (fitness > other.fitness) {
      return -1;
    } else if (fitness < other.fitness) {
      return 1;
    } else {
      return 0;
    }
  }
}
