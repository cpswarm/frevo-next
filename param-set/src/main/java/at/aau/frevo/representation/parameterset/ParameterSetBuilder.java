/**
 * File: ParameterSetBuilder.java
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

import at.aau.frevo.ComponentType;
import at.aau.frevo.RepresentationBuilder;

/**
 * Builder for {@link ParameterSet} instances.
 */
public class ParameterSetBuilder extends RepresentationBuilder<ParameterSet> {

  protected Parameter[] parameters;

  /**
   * Constructs a new {@code ParameterSetBuilder} instance with default properties.
   */
  public ParameterSetBuilder() {
    super();
    parameters = new Parameter[0];
  }

  /**
   * Constructs a new {@code ParameterSetBuilder} instance by copying the properties of the
   * specified instance.
   * 
   * @param source the source {@code ParameterSetBuilder} instance
   */
  public ParameterSetBuilder(ParameterSetBuilder source) {
    super(source);
    parameters = source.parameters.clone();
  }

  @Override
  public String getName() {
    return ParameterSetBuilder.class.getName();
  }

  @Override
  public ComponentType getType() {
    return ComponentType.REPRESENTATION;
  }

  @Override
  public ParameterSet create() {
    return new ParameterSet(this);
  }

  @Override
  public ParameterSetBuilder cloneBuilder() {
    return new ParameterSetBuilder(this);
  }

  @Override
  public ParameterSetBuilder setInputCount(int inputCount) {
    return (ParameterSetBuilder) super.setInputCount(inputCount);
  }

  @Override
  public ParameterSetBuilder setOutputCount(int outputCount) {
    return (ParameterSetBuilder) super.setOutputCount(outputCount);
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
   * Sets the parameters.
   * 
   * @param parameters the parameters
   * @return this {@code ParameterSetBuilder} instance
   */
  public ParameterSetBuilder setParameters(Parameter[] parameters) {
    this.parameters = parameters;
    return this;
  }
}
