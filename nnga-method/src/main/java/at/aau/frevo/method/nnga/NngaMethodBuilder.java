/**
 * File: NngaMethodBuilder.java
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

package at.aau.frevo.method.nnga;

import java.util.List;
import java.util.SplittableRandom;
import at.aau.frevo.ComponentType;
import at.aau.frevo.Executor;
import at.aau.frevo.MethodBuilder;
import at.aau.frevo.Operator;
import at.aau.frevo.Representation;
import at.aau.frevo.Result;

/**
 * Builder for {@link NngaMethod} instances.
 */
public class NngaMethodBuilder extends MethodBuilder<NngaMethod<? extends Representation>> {

  protected double skewFactor;
  protected double eliteWeight;
  protected double randomWeight;
  protected double mutatedWeight;
  protected double crossedWeight;
  protected double newWeight;

  /**
   * Constructs a new {@code NngaMethodBuilder} instance with default properties.
   */
  public NngaMethodBuilder() {
    skewFactor = 1;
    eliteWeight = 0.2;
    randomWeight = 0.2;
    mutatedWeight = 0.2;
    crossedWeight = 0.2;
    newWeight = 0.2;
  }

  /**
   * Constructs a new {@code NngaMethodBuilder} instance by copying the properties of the specified
   * instance.
   * 
   * @param source the source {@code NngaMethodBuilder} instance
   */
  public NngaMethodBuilder(NngaMethodBuilder source) {
    skewFactor = source.getSkewFactor();
    eliteWeight = source.getEliteWeight();
    randomWeight = source.getRandomWeight();
    mutatedWeight = source.getMutatedWeight();
    crossedWeight = source.getCrossedWeight();
    newWeight = source.getNewWeight();
  }

  @Override
  public String getName() {
    return NngaMethod.class.getName();
  }

  @Override
  public ComponentType getType() {
    return ComponentType.METHOD;
  }

  @Override
  public <R extends Representation> NngaMethod<R> create(List<Result<R>> rankedRepresentations,
      Operator<R> operator, Executor executor, SplittableRandom random) {
    return new NngaMethod<R>(this, rankedRepresentations, operator, executor, random);
  }

  @Override
  public NngaMethodBuilder cloneBuilder() {
    return new NngaMethodBuilder(this);
  }

  /**
   * Gets the skew factor, used to influence the likelihood of successful candidates being used in a
   * new generation.
   * 
   * @return the skew factor
   */
  public double getSkewFactor() {
    return skewFactor;
  }

  /**
   * Gets a parameter which influences the likelihood of more successful candidates being used in a
   * new generation.
   * 
   * @param skewFactor the skew factor
   * @return this {@code NngaMethodBuilder} instance
   */
  public NngaMethodBuilder setSkewFactor(double skewFactor) {
    this.skewFactor = skewFactor;
    return this;
  }

  /**
   * Gets the proportion of elite copied to a new generation.
   * 
   * @return the elite weight
   */
  public double getEliteWeight() {
    return eliteWeight;
  }

  /**
   * Sets the proportion of elite copied to a new generation.
   * 
   * @param eliteWeight the elite weight
   * @return this {@code NngaMethodBuilder} instance
   */
  public NngaMethodBuilder setEliteWeight(double eliteWeight) {
    this.eliteWeight = eliteWeight;
    return this;
  }

  /**
   * Gets the proportion of random members copied to a new generation.
   * 
   * @return the random weight
   */
  public double getRandomWeight() {
    return randomWeight;
  }

  /**
   * Sets the proportion of random members copied to a new generation.
   * 
   * @param randomWeight the random weight
   * @return this {@code NngaMethodBuilder} instance
   */
  public NngaMethodBuilder setRandomWeight(double randomWeight) {
    this.randomWeight = randomWeight;
    return this;
  }

  /**
   * Gets the proportion of mutated members included in a new generation.
   * 
   * @return the mutated weight
   */
  public double getMutatedWeight() {
    return mutatedWeight;
  }

  /**
   * Sets the proportion of mutated members included in a new generation.
   * 
   * @param mutatedWeight the mutated weight
   * @return this {@code NngaMethodBuilder} instance
   */
  public NngaMethodBuilder setMutatedWeight(double mutatedWeight) {
    this.mutatedWeight = mutatedWeight;
    return this;
  }

  /**
   * Gets the proportion of crossed members included in a new generation.
   * 
   * @return the crossed weight
   */
  public double getCrossedWeight() {
    return crossedWeight;
  }

  /**
   * Sets the proportion of crossed members included in a new generation.
   * 
   * @param crossedWeight the crossed weight
   * @return this {@code NngaMethodBuilder} instance
   */
  public NngaMethodBuilder setCrossedWeight(double crossedWeight) {
    this.crossedWeight = crossedWeight;
    return this;
  }

  /**
   * Gets the proportion of new members included in a new generation.
   * 
   * @return the new weight
   */
  public double getNewWeight() {
    return newWeight;
  }

  /**
   * Sets the proportion of new members included in a new generation.
   * 
   * @param newWeight the new weight
   * @return this {@code NngaMethodBuilder} instance
   */
  public NngaMethodBuilder setNewWeight(double newWeight) {
    this.newWeight = newWeight;
    return this;
  }
}
