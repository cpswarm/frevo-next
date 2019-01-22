/**
 * File: Method.java
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
import java.util.SplittableRandom;

/**
 * Abstract base class for all methods.
 * <p>
 * A method specifies an evolution algorithm.
 * 
 * @param <R> the type of {@code Representation} associated with the method
 */
public abstract class Method<R extends Representation> extends Component {

  protected Executor executor;
  protected Operator<R> operator;
  protected SplittableRandom random;
  protected int totalGenerationCount;

  /**
   * Creates a new {@code Method} instance with the specified {@code Executor}, {@code Operator} and
   * random number generator.
   * 
   * @param executor the {@code Executor} to use
   * @param operator the {@code Operator} to use
   * @param random   the random number generator to use
   */
  public Method(Executor executor, Operator<R> operator, SplittableRandom random) {
    this.executor = executor;
    this.operator = operator;
    this.random = random;
    totalGenerationCount = 0;
  }

  @Override
  public ComponentType getType() {
    return ComponentType.METHOD;
  }

  /**
   * Runs the {@code Method} for the specified number of generations.
   * 
   * @param generationCount the number of generations to execute
   * @return the current generation of ranked candidate {@code Representation} instances
   * @throws InterruptedException if the current thread was interrupted
   */
  public abstract List<Result<R>> run(int generationCount) throws InterruptedException;

  /**
   * Gets the current generation of ranked candidates.
   * 
   * @return the current generation of ranked candidate {@code Representation} instances
   */
  public abstract List<Result<R>> getRankedCandidates();

  /**
   * Gets the total number of evolution generations performed by this {@code Method}.
   * 
   * @return the total generation count
   */
  public int getTotalGenerationCount() {
    return totalGenerationCount;
  }
}
