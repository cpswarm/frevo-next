/**
 * File: ActivationFunction.java
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

/**
 * Activation functions for the fully meshed neural network. Note that range of outputs produced
 * depends on the function.
 */
public enum ActivationFunction {

  /**
   * Rectified linear unit: {@code output = input > 1 ? 1 : (input < 0 ? 0 : input)}
   * <p>
   * Output range: {@code 0 <= output <= 1}
   */
  RELU,

  /**
   * Sigmoid: {@code output = 1.0 / (1.0 + Math.exp(input))}
   * <p>
   * Output range: {@code 0 <= output <= 1}
   */
  SIGMOID,

  /**
   * Hyperbolic tangent: {@code output = Math.tanh(input)}
   * <p>
   * Output range: {@code -1 <= output <= 1}
   */
  TANH
}
