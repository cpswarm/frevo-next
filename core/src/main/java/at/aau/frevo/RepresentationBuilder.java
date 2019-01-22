/**
 * File: RepresentationBuilder.java
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
 * Abstract base class for all representation builders.
 *
 * @param <R> the type of {@code Representation} associated with this {@code RepresentationBuilder}
 */
public abstract class RepresentationBuilder<R extends Representation> extends Builder<R> {

  protected int inputCount;
  protected int outputCount;

  /**
   * Creates a new {@code RepresentationBuilder} instance.
   */
  public RepresentationBuilder() {
    inputCount = 0;
    outputCount = 0;
  }

  /**
   * Creates a new {@code RepresentationBuilder} instance based on the source
   * {@code RepresentationBuilder}.
   * 
   * @param source the source {@code RepresentationBuilder}
   */
  public RepresentationBuilder(RepresentationBuilder<R> source) {
    inputCount = source.inputCount;
    outputCount = source.outputCount;
  }

  /**
   * Creates a new {@code Representation} instance based on the current configuration.
   * 
   * @return a new {@code Representation} instance
   */
  public abstract R create();

  @Override
  public ComponentType getType() {
    return ComponentType.REPRESENTATION;
  }

  @Override
  public abstract RepresentationBuilder<R> cloneBuilder();

  /**
   * Gets the number of inputs required by {@code Representations} created by this
   * {@code RepresentationBuilder}.
   * 
   * @return the number of inputs required by {@code Representations} created by this
   *         {@code RepresentationBuilder}
   */
  public int getInputCount() {
    return inputCount;
  }

  /**
   * Sets the number of inputs required by {@code Representations} created by this
   * {@code RepresentationBuilder}.
   * 
   * @param inputCount the number of inputs required by {@code Representations} created by this
   *                   {@code RepresentationBuilder}
   * @return this {@code RepresentationBuilder}
   */
  public RepresentationBuilder<R> setInputCount(int inputCount) {
    this.inputCount = inputCount;
    return this;
  }

  /**
   * Gets the number of outputs produced by {@code Representations} created by this
   * {@code RepresentationBuilder}.
   * 
   * @return the number of outputs produced by {@code Representations} created by this
   *         {@code RepresentationBuilder}
   */
  public int getOutputCount() {
    return outputCount;
  }

  /**
   * Sets the number of outputs produced by {@code Representations} created by this
   * {@code RepresentationBuilder}.
   * 
   * @param outputCount the number of outputs produced by {@code Representations} created by this
   *                    {@code RepresentationBuilder}
   * @return this {@code RepresentationBuilder}
   */
  public RepresentationBuilder<R> setOutputCount(int outputCount) {
    this.outputCount = outputCount;
    return this;
  }
}
