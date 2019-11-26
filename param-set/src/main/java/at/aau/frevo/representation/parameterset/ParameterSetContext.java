/**
 * File: ParameterSetContext.java
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

import at.aau.frevo.RepresentationContext;

/**
 * Context for {@link ParameterSet}.
 */
public class ParameterSetContext extends RepresentationContext<ParameterSet> {

  /**
   * Creates a new {@code ParameterSetContext} instance associated with the given
   * {@code ParameterSet}.
   * 
   * @param representation the associated {@code ParameterSet}
   */
  public ParameterSetContext(ParameterSet representation) {
    super(representation);
  }

  @Override
  public void calculate(float[] input, float[] output) {
    var outputSize = representation.getOutputCount();
    for (int i = 0; i < outputSize; i++) {
      output[i] = representation.values[i] * representation.parameters[i].scale;
    }
  }
}
