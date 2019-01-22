/**
 * File: Executor.java
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

package at.aau.frevo;

import java.util.List;

/**
 * Abstract base class for all executors.
 * <p>
 * Executors are responsible for scheduling and carrying out the evaluation of representations using
 * problem instances.
 */
public abstract class Executor extends Component {

  protected ProblemBuilder<? extends Problem> problemBuilder;

  /**
   * Creates a new {@code Executor} instance with the specified {@code ProblemBuilder}.
   * 
   * @param problemBuilder the {@code ProblemBuilder} used to create {@code Problem} instances
   */
  public Executor(ProblemBuilder<? extends Problem> problemBuilder) {
    this.problemBuilder = problemBuilder;
  }

  @Override
  public ComponentType getType() {
    return ComponentType.EXECUTOR;
  }

  /**
   * Evaluates a list of {@code Representation} instances.
   * 
   * @param                 <R> the type of {@code Representation} to evaluate
   * @param representations the {@code Representation} instances to evaluate
   * @return a list of {@code Result} instances, sorted by decreasing fitness value
   * @throws InterruptedException if the current thread was interrupted
   */
  public abstract <R extends Representation> List<Result<R>> evaluateRepresentations(
      List<R> representations) throws InterruptedException;

  /**
   * Gets the {@code ProblemBuilder}
   * 
   * @return the {@code ProblemBuilder}
   */
  public ProblemBuilder<? extends Problem> getProblemBuilder() {
    return problemBuilder;
  }
}
