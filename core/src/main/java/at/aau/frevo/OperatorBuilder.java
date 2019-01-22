/**
 * File: OperatorBuilder.java
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
 * Abstract base class of all operator builders.
 * 
 * @param <T> the type of {@code Operator} associated with this {@code OperatorBuilder}
 * @param <R> the type of {@code Representation} associated with this {@code OperatorBuilder}
 */
public abstract class OperatorBuilder<T extends Operator<R>, R extends Representation>
    extends Builder<T> {

  /**
   * Creates a new {@code Operator} instance based on the current configuration.
   * 
   * @param representationBuilder the {@code RepresentationBuilder} used to create
   *                              {@code Representation} instances
   * @param random                the random number generator to use
   * @return a new {@code Operator} instance
   */
  public abstract T create(RepresentationBuilder<R> representationBuilder, SplittableRandom random);

  @Override
  public ComponentType getType() {
    return ComponentType.OPERATOR;
  }
  
  @Override
  public abstract OperatorBuilder<T, R> cloneBuilder();
}
