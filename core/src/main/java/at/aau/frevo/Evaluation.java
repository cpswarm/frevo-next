/**
 * File: Evaluation.java
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

package at.aau.frevo;

/**
 * Represents an evaluation of a {@code Representation} be performed.
 * 
 * @param <R> the type of {@code Representation} included in the {@code Evaluation}
 */
public class Evaluation<R extends Representation> {

  protected R representation;
  protected long seed;
  protected volatile double fitness;

  /**
   * Creates a new {@code Evaluation} instance with the specified {@code Representation} and seed.
   * 
   * @param representation the {@code Representation}
   * @param seed           the seed
   */
  public Evaluation(R representation, long seed) {
    this.representation = representation;
    this.seed = seed;
    fitness = -1;
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
   * Gets the seed.
   * 
   * @return the seed
   */
  public long getSeed() {
    return seed;
  }

  /**
   * Gets the fitness.
   * 
   * @return the fitness
   */
  public double getFitness() {
    return fitness;
  }

  /**
   * Sets the fitness.
   * 
   * @param fitness the fitness
   */
  public void setFitness(double fitness) {
    this.fitness = fitness;
  }
}
