/**
 * File: Operator.java
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
 * Abstract base class for all operators.
 * <p>
 * Operators manipulate representations.
 * 
 * @param <R> the {@code Representation} associated with the {@code Operator}
 */
public abstract class Operator<R extends Representation> extends Component {

  protected SplittableRandom random;

  /**
   * Creates a new {@code Operator} instance with the specified random number generator.
   * 
   * @param random the random number generator to use
   */
  public Operator(SplittableRandom random) {
    this.random = random;
  }

  @Override
  public ComponentType getType() {
    return ComponentType.OPERATOR;
  }

  /**
   * Creates a new {@code Representation} instance.
   * 
   * @return a new {@code Representation} instance
   */
  public abstract R operator0();

  /**
   * Creates a new {@code Representation} instance based on one existing {@code Representation}.
   * 
   * @param r the existing {@code Representation} to use during the operation
   * @return a new {@code Representation} instance
   */
  public abstract R operator1(R r);

  /**
   * Creates a new {@code Representation} instance based on two existing {@code Representation}
   * instances.
   * 
   * @param r1 an existing {@code Representation} to use during the operation
   * @param r2 an existing {@code Representation} to use during the operation
   * @return a new {@code Representation} instance
   */
  public abstract R operator2(R r1, R r2);

  /**
   * Calculates the difference between two {@code Representation} instances.
   * 
   * @param r1 an existing {@code Representation} to use during the operation
   * @param r2 an existing {@code Representation} to use during the operation
   * @return a value indicating difference. Higher values indicate more difference.
   */
  public abstract double difference(R r1, R r2);
}
