/**
 * File: FullyMeshedNetBuilder.java
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

package at.aau.frevo.representation.fullymeshednet;

import at.aau.frevo.ComponentType;
import at.aau.frevo.RepresentationBuilder;

/**
 * Builder for {@link FullyMeshedNet} instances.
 */
public class FullyMeshedNetBuilder extends RepresentationBuilder<FullyMeshedNet> {

  protected ActivationFunction activationFunction;
  protected int hiddenNodeCount;
  protected int iterationCount;

  /**
   * Constructs a new {@code FullyMeshedNetBuilder} instance with default properties.
   */
  public FullyMeshedNetBuilder() {
    activationFunction = ActivationFunction.RELU;
    hiddenNodeCount = 2;
    iterationCount = 2;
  }

  /**
   * Constructs a new {@code FullyMeshedNetBuilder} instance by copying the properties of the
   * specified instance.
   * 
   * @param source the source {@code FullyMeshedNetBuilder} instance
   */
  public FullyMeshedNetBuilder(FullyMeshedNetBuilder source) {
    super(source);
    activationFunction = source.activationFunction;
    hiddenNodeCount = source.hiddenNodeCount;
    iterationCount = source.iterationCount;
  }

  @Override
  public String getName() {
    return FullyMeshedNet.class.getName();
  }

  @Override
  public ComponentType getType() {
    return ComponentType.REPRESENTATION;
  }

  @Override
  public FullyMeshedNet create() {
    return new FullyMeshedNet(this);
  }

  @Override
  public RepresentationBuilder<FullyMeshedNet> cloneBuilder() {
    return new FullyMeshedNetBuilder(this);
  }

  @Override
  public FullyMeshedNetBuilder setInputCount(int inputCount) {
    return (FullyMeshedNetBuilder) super.setInputCount(inputCount);
  }

  @Override
  public FullyMeshedNetBuilder setOutputCount(int outputCount) {
    return (FullyMeshedNetBuilder) super.setOutputCount(outputCount);
  }

  /**
   * Gets the activation function.
   * 
   * @return the activation function
   */
  public ActivationFunction getActivationFunction() {
    return activationFunction;
  }


  /**
   * Sets the activation function.
   * 
   * @param activationFunction the activation function
   * @return this {@code FullyMeshedNetBuilder} instance
   */
  public FullyMeshedNetBuilder setActivationFunction(ActivationFunction activationFunction) {
    this.activationFunction = activationFunction;
    return this;
  }

  /**
   * Gets the hidden node count.
   * 
   * @return the hidden node count
   */
  public int getHiddenNodeCount() {
    return hiddenNodeCount;
  }

  /**
   * Sets the hidden node count.
   * 
   * @param hiddenNodeCount the hidden node count
   * @return this {@code FullyMeshedNetBuilder} instance
   */
  public FullyMeshedNetBuilder setHiddenNodeCount(int hiddenNodeCount) {
    this.hiddenNodeCount = hiddenNodeCount;
    return this;
  }

  /**
   * Gets the iteration count.
   * 
   * @return the iteration count
   */
  public int getIterationCount() {
    return iterationCount;
  }

  /**
   * Sets the iteration count.
   * 
   * @param iterationCount the iteration count
   * @return this {@code FullyMeshedNetBuilder} instance
   */
  public FullyMeshedNetBuilder setIterationCount(int iterationCount) {
    this.iterationCount = iterationCount;
    return this;
  }
}
