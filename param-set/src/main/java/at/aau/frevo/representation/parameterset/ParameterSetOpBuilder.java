/**
 * File: ParameterSetOpBuilder.java
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
import at.aau.frevo.OperatorBuilder;
import at.aau.frevo.RepresentationBuilder;

/**
 * Builder for {@link ParameterSetOp} instances.
 */
public class ParameterSetOpBuilder extends OperatorBuilder<ParameterSetOp, ParameterSet> {

  protected double directMutationProbability;
  protected double directMutationSeverity;
  protected double proportionalMutationProbability;
  protected double proportionalMutationSeverity;

  /**
   * Constructs a new {@code ParameterSetOpBuilder} instance with default properties.
   */
  public ParameterSetOpBuilder() {
    directMutationProbability = 0.2;
    directMutationSeverity = 0.1;
    proportionalMutationProbability = 0.1;
    proportionalMutationSeverity = 0.1;
  }

  /**
   * Constructs a new {@code ParameterSetOpBuilder} instance by copying the properties of the
   * specified instance.
   * 
   * @param source the source {@code ParameterSetOpBuilder} instance
   */
  protected ParameterSetOpBuilder(ParameterSetOpBuilder source) {
    directMutationProbability = source.directMutationProbability;
    directMutationSeverity = source.directMutationSeverity;
    proportionalMutationProbability = source.proportionalMutationProbability;
    proportionalMutationSeverity = source.proportionalMutationSeverity;
  }

  @Override
  public ParameterSetOp create(RepresentationBuilder<ParameterSet> paramSetBuilder,
      SplittableRandom random) {
    return new ParameterSetOp(this, paramSetBuilder, random);
  }

  @Override
  public String getName() {
    return ParameterSetOpBuilder.class.getName();
  }

  @Override
  public ParameterSetOpBuilder cloneBuilder() {
    return new ParameterSetOpBuilder(this);
  }

  /**
   * Gets the direct mutation probability.
   * 
   * @return the direct mutation probability
   */
  public double getDirectMutationProbability() {
    return directMutationProbability;
  }

  /**
   * Sets the direct mutation probability.
   * 
   * @param directMutationProbability the direct mutation probability
   * @return this {@code ParameterSetOpBuilder} instance
   */
  public ParameterSetOpBuilder setDirectMutationProbability(double directMutationProbability) {
    this.directMutationProbability = directMutationProbability;
    return this;
  }

  /**
   * Gets the direct mutation severity.
   * 
   * @return the direct mutation severity
   */
  public double getDirectMutationSeverity() {
    return directMutationSeverity;
  }

  /**
   * Sets the direct mutation severity.
   * 
   * @param directMutationSeverity the direct mutation severity
   * @return this {@code ParameterSetOpBuilder} instance
   */
  public ParameterSetOpBuilder setDirectMutationSeverity(double directMutationSeverity) {
    this.directMutationSeverity = directMutationSeverity;
    return this;
  }

  /**
   * Gets the proportional mutation probability.
   * 
   * @return the proportional mutation probability
   */
  public double getProportionalMutationProbability() {
    return proportionalMutationProbability;
  }

  /**
   * Sets the proportional mutation probability.
   * 
   * @param proportionalMutationProbability the proportional mutation probability
   * @return this {@code ParameterSetOpBuilder} instance
   */
  public ParameterSetOpBuilder setProportionalMutationProbability(
      double proportionalMutationProbability) {
    this.proportionalMutationProbability = proportionalMutationProbability;
    return this;
  }

  /**
   * Gets the proportional mutation severity.
   * 
   * @return the proportional mutation severity
   */
  public double getProportionalMutationSeverity() {
    return proportionalMutationSeverity;
  }

  /**
   * Sets the proportional mutation severity.
   * 
   * @param proportionalMutationSeverity the proportional mutation severity
   * @return this {@code ParameterSetOpBuilder} instance
   */
  public ParameterSetOpBuilder setProportionalMutationSeverity(
      double proportionalMutationSeverity) {
    this.proportionalMutationSeverity = proportionalMutationSeverity;
    return this;
  }
}
