/**
 * File: Parameter.java
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

package at.aau.frevo.representation.parameterset;

/**
 * Represents a parameter to be optimized.
 * <p>
 * The parameter may be assigned an integer value between {@code minimum} and {@code maximum} which
 * is then scaled using {@code scale}.
 * <p>
 * The {@code metaInformation} field allows additional information describing the parameter to be
 * passed through to consumers.
 */
public class Parameter {

  protected String name;
  protected String metaInformation;
  protected int minimum;
  protected int maximum;
  protected float scale;

  /**
   * Creates a new {@code Parameter} instance.
   */
  public Parameter() {
    name = "";
    metaInformation = "";
    minimum = 0;
    maximum = 0;
    scale = 1;
  }

  /**
   * Creates a new {@code Parameter} instance using the specified values.
   * 
   * @param name            the name
   * @param metaInformation the meta information
   * @param minimum         the minimum value
   * @param maximum         the maximum value
   * @param scale           the scale
   */
  public Parameter(String name, String metaInformation, int minimum, int maximum, float scale) {
    this.name = name;
    this.metaInformation = metaInformation;
    this.minimum = minimum;
    this.maximum = maximum;
    this.scale = scale;
  }

  /**
   * Gets the name.
   * 
   * @return the name
   */
  public String getName() {
    return name;
  }

  /**
   * Gets the meta information.
   * 
   * @return the meta information
   */
  public String getMetaInformation() {
    return metaInformation;
  }

  /**
   * Gets the minimum.
   * 
   * @return the minimum
   */
  public int getMinimum() {
    return minimum;
  }

  /**
   * Gets the maximum.
   * 
   * @return the maximum
   */
  public int getMaximum() {
    return maximum;
  }

  /**
   * Gets the scale.
   * 
   * @return the scale
   */
  public float getScale() {
    return scale;
  }
}
