/**
 * File: ParameterSetOp.java
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
import at.aau.frevo.Operator;
import at.aau.frevo.RepresentationBuilder;

/**
 * Operator for {@link ParameterSet}.
 */
public class ParameterSetOp extends Operator<ParameterSet> {

  protected RepresentationBuilder<ParameterSet> representationBuilder;

  protected double directMutationProbability;
  protected double directMutationSeverity;
  protected double proportionalMutationProbability;
  protected double proportionalMutationSeverity;

  /**
   * Creates a new {@code ParameterSetOp} instance using the specified configuration.
   * 
   * @param builder               the {@code ParameterSetOpBuilder} used for configuration
   * @param representationBuilder the {@code RepresentationBuilder} used for creating new
   *                              {@code ParameterSet} instances
   * @param random                the random number generator to use
   */
  public ParameterSetOp(ParameterSetOpBuilder builder,
      RepresentationBuilder<ParameterSet> representationBuilder, SplittableRandom random) {
    super(random);
    this.representationBuilder = representationBuilder;
    directMutationProbability = builder.getDirectMutationProbability();
    directMutationSeverity = builder.getDirectMutationSeverity();
    proportionalMutationProbability = builder.getProportionalMutationProbability();
    proportionalMutationSeverity = builder.getProportionalMutationSeverity();
  }

  @Override
  public ParameterSet operator0() {
    var paramSet = representationBuilder.create();
    for (int i = 0; i < paramSet.values.length; i++) {
      paramSet.values[i] =
          random.nextInt(paramSet.parameters[i].minimum, paramSet.parameters[i].maximum + 1);
    }
    return paramSet;
  }

  @Override
  public ParameterSet operator1(ParameterSet r) {
    var mutantSet = new ParameterSet(r);
    for (int i = 0; i < mutantSet.values.length; i++) {
      var param = mutantSet.parameters[i];

      if (random.nextDouble() < directMutationProbability) {
        var range = Math.max((int) (directMutationSeverity * (param.maximum - param.minimum)), 0);        
        mutantSet.values[i] += random.nextInt(-range, range + 1);
      }

      if (random.nextDouble() < proportionalMutationProbability) {
        var x = Math.max((int) Math.abs(proportionalMutationSeverity * mutantSet.values[i]), 0);
        mutantSet.values[i] += random.nextInt(-x, x + 1);
      }

      if (mutantSet.values[i] > param.maximum) {
        mutantSet.values[i] = param.maximum;
      } else if (mutantSet.values[i] < param.minimum) {
        mutantSet.values[i] = param.minimum;
      }
    }
    return mutantSet;
  }

  @Override
  public ParameterSet operator2(ParameterSet set1, ParameterSet set2) {

    if (random.nextBoolean()) {
      var t = set1;
      set1 = set2;
      set2 = t;
    }

    var crossSet = new ParameterSet(set1);
    var count = crossSet.values.length;
    int startIndex = random.nextInt(0, count - 1);
    int stopIndex = random.nextInt(startIndex + 1, count);

    for (int i = startIndex; i < stopIndex; i++) {
      crossSet.values[i] = set2.values[i];
    }

    return crossSet;
  }

  @Override
  public double difference(ParameterSet set1, ParameterSet set2) {
    double diff = 0;
    var count = set1.getOutputCount();
    for (int i = 0; i < count; i++) {
      diff += (Math.abs(set1.values[i] - set2.values[i])
          / (set1.parameters[i].maximum - set1.parameters[i].minimum));
    }
    return diff;
  }

  /**
   * Gets the associated {@code RepresentationBuilder}.
   * 
   * @return the {@code RepresentationBuilder}
   */
  public RepresentationBuilder<ParameterSet> getParamSetBuilder() {
    return representationBuilder;
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
   * Gets the direct mutation severity.
   * 
   * @return the direct mutation severity
   */
  public double getDirectMutationSeverity() {
    return directMutationSeverity;
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
   * Gets the proportional mutation severity.
   * 
   * @return the proportional mutation severity
   */
  public double getProportionalMutationSeverity() {
    return proportionalMutationSeverity;
  }
}
