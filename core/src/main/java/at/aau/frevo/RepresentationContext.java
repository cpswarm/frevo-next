/**
 * File: RepresentationContext.java
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
 * The mutable state of a {@code Representation}. By creating separate {@code RepresentationContext}
 * instances, {@code Representation} instances may be used in multiple threads.
 * 
 * @param <R> the type of {@code Representation} associated with this {@code RepresentationContext}
 */
public abstract class RepresentationContext<R extends Representation> {

  protected R representation;

  /**
   * Creates a new {@code RepresentationContext} instance associated with the given
   * {@code Representation}.
   * 
   * @param representation the {@code Representation} to use
   */
  public RepresentationContext(R representation) {
    this.representation = representation;
  }

  /**
   * Calculates the output of the {@link Representation} for the given input.
   * <p>
   * This may affect the state of the {@code RepresentationContext}.
   * 
   * @param input  the array of input
   * @param output the array in which to store output
   */
  public abstract void calculate(float[] input, float[] output);

  /**
   * Gets the associated {@code Representation}.
   * 
   * @return the associated {@code Representation}
   */
  public R getRepresentation() {
    return representation;
  }
}
