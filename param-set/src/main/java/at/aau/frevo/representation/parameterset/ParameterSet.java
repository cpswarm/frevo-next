/**
 * File: ParameterSet.java
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

import java.util.SplittableRandom;

import at.aau.frevo.Representation;
import at.aau.frevo.RepresentationContext;

/**
 * Parameter set representation.
 */
public class ParameterSet extends Representation {

  protected Parameter[] parameters;
  protected int[] values;

  /**
   * Creates a new {@code ParameterSet} instance using the specified configuration.
   * 
   * @param builder the {@code ParameterSetBuilder} used for configuration
   */
  public ParameterSet(ParameterSetBuilder builder) {
    super(builder);
    var count = builder.getOutputCount();
    parameters = builder.getParameters().clone();
    values = new int[count];
  }

  /**
   * Creates a new {@code ParameterSet} instance based on an existing instance.
   * 
   * @param source the source {@code ParameterSet}
   */
  public ParameterSet(ParameterSet source) {
    super(source);
    parameters = source.parameters.clone();
    values = source.values.clone();
  }

  @Override
  public RepresentationContext<? extends Representation> createContext(SplittableRandom random) {
    return new ParameterSetContext(this);
  }

  @Override
  public ParameterSet cloneRepresentation() {
    return new ParameterSet(this);
  }

  @Override
  public String getHash() {
    int sum = 0;
    for (float value : values) {
      sum += value;
    }
    return Integer.toHexString(Integer.toString(sum).hashCode() & 0xFFFFF);
  }

  /**
   * Gets the parameters.
   * 
   * @return the parameters
   */
  public Parameter[] getParameters() {
    return parameters;
  }

  /**
   * Gets the values.
   * 
   * @return the values
   */
  public int[] getValues() {
    return values;
  }
}
