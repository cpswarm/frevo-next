/**
 * File: Builder.java
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
 * The abstract base class for all builders.
 * 
 * @param <T> the type of component associated with the {@code Builder}. This type parameter allows
 *        the compiler to reason about the type of objects produced by {@code Builder} instances.
 */
public abstract class Builder<T extends Component> {

  /**
   * Gets the name of the {@code Builder}. By convention this is the full class name, returned by
   * {@code Class.getName()}.
   * 
   * @return the name of the {@code Builder}
   */
  public abstract String getName();

  /**
   * Gets the type of {@link Component} created by the {@code Builder}.
   * 
   * @return the {@code ComponentType} of the {@code Component}
   */
  public abstract ComponentType getType();

  /**
   * Clones the {@code Builder}.
   * 
   * @return a new {@code Builder} instance
   */
  public abstract Builder<T> cloneBuilder();
}
