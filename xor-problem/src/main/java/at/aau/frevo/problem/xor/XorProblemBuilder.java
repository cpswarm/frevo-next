/**
 * File: XorProblemBuilder.java
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

package at.aau.frevo.problem.xor;

import java.util.SplittableRandom;
import at.aau.frevo.ProblemBuilder;

/**
 * Builder for {@link XorProblem} instances.
 */
public class XorProblemBuilder extends ProblemBuilder<XorProblem> {

  protected float trueValue;
  protected float falseValue;
  protected float tolerance;
  protected int operationCount;

  /**
   * Creates a new {@code XorProblemBuilder} instance.
   */
  public XorProblemBuilder() {
    trueValue = 1;
    falseValue = -1;
    tolerance = 0.05f;
    operationCount = 100;
  }

  /**
   * Constructs a new {@code XorProblemBuilder} instance by copying the properties of the specified
   * instance.
   * 
   * @param source the source {@code XorProblemBuilder} instance
   */
  public XorProblemBuilder(XorProblemBuilder source) {
    trueValue = source.trueValue;
    falseValue = source.falseValue;
    tolerance = source.tolerance;
    operationCount = source.operationCount;
  }

  @Override
  public XorProblem create(SplittableRandom random) {
    return new XorProblem(this, random);
  }

  @Override
  public double getMaximumFitness() {
    return 100;
  }

  @Override
  public String getName() {
    return XorProblem.class.getName();
  }

  @Override
  public XorProblemBuilder cloneBuilder() {
    return new XorProblemBuilder(this);
  }

  @Override
  public int getRepresentationInputCount() {
    return 2;
  }

  @Override
  public int getRepresentationOutputCount() {
    return 1;
  }

  /**
   * Gets the value used to represent {@code true}.
   * 
   * @return the true value
   */
  public float getTrueValue() {
    return trueValue;
  }

  /**
   * Sets the value used to represent {@code true}.
   * 
   * @param trueValue the true value
   * @return this {@code XorProblemBuilder} instance
   */
  public XorProblemBuilder setTrueValue(float trueValue) {
    this.trueValue = trueValue;
    return this;
  }

  /**
   * Gets the value used to represent {@code false}.
   * 
   * @return the false value
   */
  public float getFalseValue() {
    return falseValue;
  }

  /**
   * Sets the value used to represent {@code false}.
   * 
   * @param falseValue the false value
   * @return this {@code XorProblemBuilder} instance
   */
  public XorProblemBuilder setFalseValue(float falseValue) {
    this.falseValue = falseValue;
    return this;
  }

  /**
   * Gets the acceptable tolerance for the true and false values.
   * 
   * @return the tolerance
   * @see XorProblem#getTolerance()
   */
  public float getTolerance() {
    return tolerance;
  }

  /**
   * Sets the acceptable tolerance for the true and false values.
   * 
   * @param tolerance the tolerance
   * @return this {@code XorProblemBuilder} instance
   * @see XorProblem#getTolerance()
   */
  public XorProblemBuilder setTolerance(float tolerance) {
    this.tolerance = tolerance;
    return this;
  }

  /**
   * Gets the number of operations to perform during evaluation.
   * 
   * @return the operation count
   */
  public int getOperationCount() {
    return operationCount;
  }

  /**
   * Sets the number of operations to perform during evaluation.
   * 
   * @param operationCount the operation count
   * @return this {@code XorProblemBuilder} instance
   */
  public XorProblemBuilder setOperationCount(int operationCount) {
    this.operationCount = operationCount;
    return this;
  }
}
