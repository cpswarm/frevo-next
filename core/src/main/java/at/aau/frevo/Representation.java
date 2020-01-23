/**
 * File: Representation.java
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
 * Abstract base class for all representations.
 */
public abstract class Representation extends Component implements Comparable<Representation> {

  protected int inputCount;
  protected int outputCount;

  /**
   * Creates a new {@code Representation} instance using the configuration specified by the
   * {@code RepresentationBuilder}.
   * 
   * @param builder the {@code RepresentationBuilder} used for configuration
   */
  public Representation(RepresentationBuilder<? extends Representation> builder) {
    inputCount = builder.getInputCount();
    outputCount = builder.getOutputCount();
  }

  /**
   * Creates a new {@code Representation} instance copying from the source {@code Representation}.
   * 
   * @param source the source {@code Representation}
   */
  public Representation(Representation source) {
    inputCount = source.inputCount;
    outputCount = source.outputCount;
  }

  @Override
  public ComponentType getType() {
    return ComponentType.REPRESENTATION;
  }

  /**
   * Gets the number of inputs required by this {@code Representation}.
   * 
   * @return the number of inputs required by this {@code Representation}
   */
  public int getInputCount() {
    return inputCount;
  }

  /**
   * Gets the number of outputs produced by this {@code Representation}.
   * 
   * @return the number of outputs produced by this {@code Representation}
   */
  public int getOutputCount() {
    return outputCount;
  }

  /**
   * Creates a context of the the {@code Representation}.
   * 
   * @param random the random number to use
   * @return a new {@code RepresentationContext}
   */
  public abstract RepresentationContext<? extends Representation> createContext(
      SplittableRandom random);

  /**
   * Clones the {@code Representation}.
   * 
   * @return a new {@code Representation} instance
   */
  public abstract Representation cloneRepresentation();

  /**
   * Gets a name for the {@code Representation}.
   * 
   * @return a name {@code String}
   */
  public String getName() {
    return getClass().getName();
  }

  /**
   * Gets a {@code String} hash for the {@code Representation}.
   * 
   * @return a {@code String} hash of the {@code Representation}
   */
  public abstract String getHash();

  @Override
  final public String toString() {
    return getName() + "(" + this.getHash() + ")";
  }

  /**
   * Compares this {@code Representation} instance with another instance.
   * <p>
   * Sub classes should first call this method and carry out further comparisons if the result is
   * zero.
   * 
   * @param other the second {@code Representation} instance
   * @return a negative integer if this {@code Representation} is less than the other
   *         {@code Representation}, positive if greater or zero if the same
   */
  public int compareTo(Representation other) {
    // only functionally equivalent Representation instances can be compared
    if ((getInputCount() != other.getInputCount())
        || (getOutputCount() != other.getOutputCount())) {
      throw new IllegalArgumentException();
    }
    return getName().compareTo(other.getName());
  }
}
