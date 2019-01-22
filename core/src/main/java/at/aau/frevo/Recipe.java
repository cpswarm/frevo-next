/**
 * File: Recipe.java
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

import java.util.ArrayList;
import java.util.List;
import java.util.SplittableRandom;

/**
 * A sugar class for making evolutionary optimization easy.
 * <p>
 * At the same time, the {@code Recipe} class demontrates how the different FREVO components work
 * together.
 * 
 * @param <RB> the type of {@code RepresentationBuilder} used in the {@code Recipe}
 * @param <OB> the type of {@code OperatorBuilder} used in the {@code Recipe}
 * @param <MB> the type of {@code MethodBuilder} used in the {@code Recipe}
 * @param <EB> the type of {@code ExecutorBuilder} used in the {@code Recipe}
 * @param <PB> the type of {@code ProblemBuilder} used in the {@code Recipe}
 * @param <R> the type of {@code Representation} used in the {@code Recipe}
 * @param <O> the type of {@code Operator} used in the {@code Recipe}
 * @param <M> the type of {@code Method} used in the {@code Recipe}
 * @param <E> the type of {@code Executor} used in the {@code Recipe}
 * @param <P> the type of {@code Problem} used in the {@code Recipe}
 */
public class Recipe<RB extends RepresentationBuilder<R>, OB extends OperatorBuilder<O, R>,
    MB extends MethodBuilder<M>, EB extends ExecutorBuilder<E>, PB extends ProblemBuilder<P>,
    R extends Representation, O extends Operator<R>, M extends Method<? extends Representation>,
    E extends Executor, P extends Problem> {

  protected PB problemBuilder;
  protected RB representationBuilder;
  protected OB operatorBuilder;
  protected MB methodBuilder;
  protected EB executorBuilder;

  protected long evolutionSeed;
  protected long evaluationSeed;

  protected O operator;
  protected Method<R> method;
  protected E executor;

  /**
   * Creates a new {@code Recipe} instance using the configuration specified by the
   * {@code RepresentationBuilder}, {@code OperatorBuilder}, {@code MethodBuilder},
   * {@code ExecutorBuilder} and {@code ProblemBuilder} instances.
   * 
   * @param representationBuilder the {@code RepresentationBuilder} to use
   * @param operatorBuilder       the {@code OperatorBuilder} to use
   * @param methodBuilder         the {@code MethodBuilder} to use
   * @param executorBuilder       the {@code ExecutorBuilder} to use
   * @param problemBuilder        the {@code ProblemBuilder} to use
   * @param evolutionSeed         the seed used during evolution
   * @param evaluationSeed        the seed use during evaluation
   */
  public Recipe(RB representationBuilder, OB operatorBuilder, MB methodBuilder, EB executorBuilder,
      PB problemBuilder, long evolutionSeed, long evaluationSeed) {
    this.representationBuilder = representationBuilder;
    this.operatorBuilder = operatorBuilder;
    var methodBuilderCasted = methodBuilder;
    this.methodBuilder = methodBuilderCasted;
    this.problemBuilder = problemBuilder;
    this.executorBuilder = executorBuilder;
    this.evolutionSeed = evolutionSeed;
    this.evaluationSeed = evaluationSeed;

    representationBuilder.setInputCount(problemBuilder.getRepresentationInputCount());
    representationBuilder.setOutputCount(problemBuilder.getRepresentationOutputCount());
  }

  /**
   * Forces the construction of a new {@code Recipe} when the compiler cannot deduce that the
   * {@code RepresentationBuilder} and {@code OperatorBuilder} use the same underlying
   * {@code Representation} type.
   * <p>
   * This method is useful following deserialization.
   * 
   * @param <RB> the type of {@code RepresentationBuilder} used in the {@code Recipe}
   * @param <OB> the type of {@code OperatorBuilder} used in the {@code Recipe}
   * @param <MB> the type of {@code MethodBuilder} used in the {@code Recipe}
   * @param <EB> the type of {@code ExecutorBuilder} used in the {@code Recipe}
   * @param <PB> the type of {@code ProblemBuilder} used in the {@code Recipe}
   * @param <R> the type of {@code Representation} used in the {@code Recipe}
   * @param <O> the type of {@code Operator} used in the {@code Recipe}
   * @param <M> the type of {@code Method} used in the {@code Recipe}
   * @param <E> the type of {@code Executor} used in the {@code Recipe}
   * @param <P> the type of {@code Problem} used in the {@code Recipe}
   * @param representationBuilder the {@code RepresentationBuilder} to use
   * @param operatorBuilder       the {@code OperatorBuilder} to use
   * @param methodBuilder         the {@code MethodBuilder} to use
   * @param executorBuilder       the {@code ExecutorBuilder} to use
   * @param problemBuilder        the {@code ProblemBuilder} to use
   * @param evolutionSeed         the seed used during evolution
   * @param evaluationSeed        the seed use during evaluation
   * @return the newly constructed {@code Recipe}
   */
  public static <RB extends RepresentationBuilder<R>, OB extends OperatorBuilder<O, R>,
      MB extends MethodBuilder<M>, EB extends ExecutorBuilder<E>, PB extends ProblemBuilder<P>,
      R extends Representation, O extends Operator<R>, M extends Method<? extends Representation>,
      E extends Executor, P extends Problem> Recipe<RB, OB, MB, EB, PB, R, O, M, E, P> forceConstruction(
      RB representationBuilder,
      OperatorBuilder<? extends Operator<? extends Representation>, ? extends Representation> operatorBuilder,
      MB methodBuilder, EB executorBuilder, PB problemBuilder, long evolutionSeed,
      long evaluationSeed) {
    @SuppressWarnings("unchecked")
    var operatorBuilderCasted = (OB) operatorBuilder;
    return new Recipe<>(representationBuilder, operatorBuilderCasted, methodBuilder,
        executorBuilder, problemBuilder, evolutionSeed, evaluationSeed);
  }

  /**
   * Prepares for evolution by creating and evaluating an initial set of candidate
   * {@code Representation} instances.
   * 
   * @param candidateCount number of representations to create
   * @throws InterruptedException if the current thread was interrupted
   */
  public void prepare(int candidateCount) throws InterruptedException {
    var evolutionRandom = new SplittableRandom(evolutionSeed);
    operator = operatorBuilder.create(representationBuilder, evolutionRandom);
    executor = executorBuilder.create(problemBuilder, new SplittableRandom(evaluationSeed));

    var candidates = new ArrayList<R>();
    for (int i = 0; i < candidateCount; i++) {
      candidates.add(operator.operator0());
    }

    method = methodBuilder.create(executor.evaluateRepresentations(candidates), operator, executor,
        evolutionRandom);
  }

  /**
   * Prepares for evolution using a list of pre-ranked candidates.
   * 
   * @param rankedCandidates a list of pre-ranked candidate {@code Representation} instances
   */
  public void prepare(List<Result<R>> rankedCandidates) {
    var evolutionRandom = new SplittableRandom(evolutionSeed);
    operator = operatorBuilder.create(representationBuilder, evolutionRandom);
    executor = executorBuilder.create(problemBuilder, new SplittableRandom(evaluationSeed));
    method = methodBuilder.create(rankedCandidates, operator, executor, evolutionRandom);
  }

  /**
   * Runs the evolution optimization process for the specified number of generations.
   * 
   * @param generationCount number of generations
   * @return the results of the last generation
   * @throws InterruptedException if the current thread was interrupted
   */
  public List<Result<R>> run(int generationCount) throws InterruptedException {
    return method.run(generationCount);
  }

  /**
   * Gets the {@code RepresentationBuilder} instance used in the {@code Recipe}.
   * 
   * @return the {@code RepresentationBuilder}
   */
  public RB getRepresentationBuilder() {
    return representationBuilder;
  }

  /**
   * Gets the {@code OperatorBuilder} instance used in the {@code Recipe}.
   * 
   * @return the {@code OperatorBuilder}
   */
  public OB getOperatorBuilder() {
    return operatorBuilder;
  }

  /**
   * Gets the {@code MethodBuilder} instance used in the {@code Recipe}.
   * 
   * @return the {@code MethodBuilder}
   */
  public MB getMethodBuilder() {
    return methodBuilder;
  }

  /**
   * Gets the {@code ExecutorBuilder} instance used in the {@code Recipe}.
   * 
   * @return the {@code ExecutorBuilder}
   */
  public EB getExecutorBuilder() {
    return executorBuilder;
  }

  /**
   * Gets the {@code ProblemBuilder} instance used in the {@code Recipe}.
   * 
   * @return the {@code ProblemBuilder}
   */
  public PB getProblemBuilder() {
    return problemBuilder;
  }

  /**
   * Gets the evolution seed used during the {@code Recipe}.
   * 
   * @return the evolution seed
   */
  public long getRecipeRandom() {
    return evolutionSeed;
  }

  /**
   * Gets the evaluation seed used during the {@code Recipe}.
   * 
   * @return the evaluation seed
   */
  public long getEvaluationSeed() {
    return evaluationSeed;
  }

  /**
   * Gets the {@code Operator} used in the {@code Recipe}.
   * 
   * @return the {@code Operator}
   */
  public O getOperator() {
    return operator;
  }

  /**
   * Gets the {@code Method} used in the {@code Recipe}.
   * 
   * @return the {@code Method}
   */
  public Method<R> getMethod() {
    return method;
  }

  /**
   * Gets the {@code Executor} used in the {@code Recipe}.
   * 
   * @return the {@code Executor}
   */
  public E getExecutor() {
    return executor;
  }
}
