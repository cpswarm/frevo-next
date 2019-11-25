/**
 * File: FullyMeshedNetOpBuilder.java
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

import java.util.SplittableRandom;
import at.aau.frevo.OperatorBuilder;
import at.aau.frevo.RepresentationBuilder;

/**
 * Builder for {@link FullyMeshedNetOp} instances.
 */
public class FullyMeshedNetOpBuilder extends OperatorBuilder<FullyMeshedNetOp, FullyMeshedNet> {

  protected float initialWeightRange;
  protected float initialBiasRange;
  protected float initialRandomBiasRange;
  protected float weightRange;
  protected float biasRange;
  protected float randomBiasRange;
  protected double directMutationProbability;
  protected double directMutationSeverity;
  protected double proportionalMutationProbability;
  protected double proportionalMutationSeverity;

  /**
   * Constructs a new {@code FullyMeshedNetOpBuilder} instance with default properties.
   */
  public FullyMeshedNetOpBuilder() {
    initialWeightRange = 2;
    initialBiasRange = 2;
    initialRandomBiasRange = 0;
    weightRange = 10;
    biasRange = 10;
    randomBiasRange = 10;
    directMutationProbability = 0.2;
    directMutationSeverity = 0.1;
    proportionalMutationProbability = 0.1;
    proportionalMutationSeverity = 0.1;
  }

  /**
   * Constructs a new {@code FullyMeshedNetOpBuilder} instance by copying the properties of the
   * specified instance.
   * 
   * @param source the source {@code FullyMeshedNetOpBuilder} instance
   */
  protected FullyMeshedNetOpBuilder(FullyMeshedNetOpBuilder source) {
    initialWeightRange = source.initialWeightRange;
    initialBiasRange = source.initialBiasRange;
    initialRandomBiasRange = source.initialRandomBiasRange;
    weightRange = source.weightRange;
    biasRange = source.biasRange;
    randomBiasRange = source.randomBiasRange;
    directMutationProbability = source.directMutationProbability;
    directMutationSeverity = source.directMutationSeverity;
    proportionalMutationProbability = source.proportionalMutationProbability;
    proportionalMutationSeverity = source.proportionalMutationSeverity;
  }

  @Override
  public FullyMeshedNetOp create(RepresentationBuilder<FullyMeshedNet> fullyMeshedNetBuilder,
      SplittableRandom random) {
    return new FullyMeshedNetOp(this, fullyMeshedNetBuilder, random);
  }

  @Override
  public String getName() {
    return FullyMeshedNetOpBuilder.class.getName();
  }

  @Override
  public FullyMeshedNetOpBuilder cloneBuilder() {
    return new FullyMeshedNetOpBuilder(this);
  }

  /**
   * Gets the initial weight range.
   * 
   * @return the initial weight range
   */
  public float getInitialWeightRange() {
    return initialWeightRange;
  }

  /**
   * Sets the initial weight range.
   * 
   * @param initialWeightRange the initial weight range
   * @return this {@code FullyMeshedNetOpBuilder} instance
   */
  public FullyMeshedNetOpBuilder setInitialWeightRange(float initialWeightRange) {
    this.initialWeightRange = initialWeightRange;
    return this;
  }

  /**
   * Gets the initial bias range.
   * 
   * @return the initial bias range.
   */
  public float getInitialBiasRange() {
    return initialBiasRange;
  }

  /**
   * Sets the initial bias range.
   * 
   * @param initialBiasRange the initial bias range
   * @return this {@code FullyMeshedNetOpBuilder} instance
   */
  public FullyMeshedNetOpBuilder setInitialBiasRange(float initialBiasRange) {
    this.initialBiasRange = initialBiasRange;
    return this;
  }

  /**
   * Gets the initial random bias range.
   * 
   * @return the initial random bias range
   */
  public float getInitialRandomBiasRange() {
    return initialRandomBiasRange;
  }

  /**
   * Sets the initial random bias range.
   * 
   * @param initialRandomBiasRange the initial random bias range
   * @return this {@code FullyMeshedNetOpBuilder} instance
   */
  public FullyMeshedNetOpBuilder setInitialRandomBiasRange(float initialRandomBiasRange) {
    this.initialRandomBiasRange = initialRandomBiasRange;
    return this;
  }

  /**
   * Gets the weight range.
   * 
   * @return the initial weight range
   */
  public float getWeightRange() {
    return weightRange;
  }

  /**
   * Sets the weight range.
   * 
   * @param weightRange the weight range
   * @return this {@code FullyMeshedNetOpBuilder} instance
   */
  public FullyMeshedNetOpBuilder setWeightRange(float weightRange) {
    this.weightRange = weightRange;
    return this;
  }

  /**
   * Gets the bias range.
   * 
   * @return the bias range.
   */
  public float getBiasRange() {
    return biasRange;
  }

  /**
   * Sets the bias range.
   * 
   * @param biasRange the bias range
   * @return this {@code FullyMeshedNetOpBuilder} instance
   */
  public FullyMeshedNetOpBuilder setBiasRange(float biasRange) {
    this.biasRange = biasRange;
    return this;
  }

  /**
   * Gets the random bias range.
   * 
   * @return the random bias range
   */
  public float getRandomBiasRange() {
    return randomBiasRange;
  }

  /**
   * Sets the random bias range.
   * 
   * @param randomBiasRange the random bias range
   * @return this {@code FullyMeshedNetOpBuilder} instance
   */
  public FullyMeshedNetOpBuilder setRandomBiasRange(float randomBiasRange) {
    this.randomBiasRange = randomBiasRange;
    return this;
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
   * @return this {@code FullyMeshedNetOpBuilder} instance
   */
  public FullyMeshedNetOpBuilder setDirectMutationProbability(double directMutationProbability) {
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
   * @return this {@code FullyMeshedNetOpBuilder} instance
   */
  public FullyMeshedNetOpBuilder setDirectMutationSeverity(double directMutationSeverity) {
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
   * @return this {@code FullyMeshedNetOpBuilder} instance
   */
  public FullyMeshedNetOpBuilder setProportionalMutationProbability(
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
   * @return this {@code FullyMeshedNetOpBuilder} instance
   */
  public FullyMeshedNetOpBuilder setProportionalMutationSeverity(
      double proportionalMutationSeverity) {
    this.proportionalMutationSeverity = proportionalMutationSeverity;
    return this;
  }
}
