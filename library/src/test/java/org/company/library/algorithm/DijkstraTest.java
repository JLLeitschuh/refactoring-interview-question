package org.company.library.algorithm;

import static it.ozimov.cirneco.hamcrest.guava.GuavaMatchers.emptyKeySet;
import static it.ozimov.cirneco.hamcrest.guava.GuavaMatchers.keyWithSize;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.google.common.collect.ListMultimap;
import com.google.common.graph.ImmutableValueGraph;
import com.google.common.graph.MutableValueGraph;
import com.google.common.graph.ValueGraphBuilder;
import java.util.function.Supplier;
import java.util.stream.Stream;
import org.company.library.graph.Node;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

@Disabled("These tests should all pass when you are done.")
class DijkstraTest {

  @ParameterizedTest
  @MethodSource("valueGraphBuilderSupplier")
  void emptyGraphProducesEmptyList(
      final Supplier<ValueGraphBuilder<Object, Object>> graphBuilderSupplier) {
    final Node nodeA = new Node("A");
    final MutableValueGraph<Node, Integer> graph = graphBuilderSupplier.get().build();
    graph.addNode(nodeA);
    MatcherAssert.assertThat(
        Dijkstra.computeShortestPath(ImmutableValueGraph.copyOf(graph), nodeA), emptyKeySet());
  }

  @ParameterizedTest
  @MethodSource("valueGraphBuilderSupplier")
  void shortestPathForTwoElementsIsTheTwoElements(
      final Supplier<ValueGraphBuilder<Object, Object>> graphBuilderSupplier) {
    final MutableValueGraph<Node, Integer> graph = graphBuilderSupplier.get().build();
    graph.putEdgeValue(new Node("A"), new Node("B"), 30);
    final ListMultimap<Node, Node> shortestPaths =
        Dijkstra.computeShortestPath(ImmutableValueGraph.copyOf(graph), new Node("A"));
    assertThat(shortestPaths, keyWithSize(new Node("B"), 1));
  }

  @ParameterizedTest
  @MethodSource("valueGraphBuilderSupplier")
  void knownShortestPathTest(
      final Supplier<ValueGraphBuilder<Object, Object>> graphBuilderSupplier) {
    final MutableValueGraph<Node, Integer> graph = graphBuilderSupplier.get().build();
    final Node nodeA = new Node("A");
    final Node nodeB = new Node("B");
    final Node nodeC = new Node("C");
    final Node nodeD = new Node("D");
    final Node nodeE = new Node("E");
    final Node nodeF = new Node("F");

    graph.putEdgeValue(nodeA, nodeB, 10);
    graph.putEdgeValue(nodeA, nodeC, 15);

    graph.putEdgeValue(nodeB, nodeD, 12);
    graph.putEdgeValue(nodeB, nodeF, 15);

    graph.putEdgeValue(nodeC, nodeE, 10);

    graph.putEdgeValue(nodeD, nodeE, 2);
    graph.putEdgeValue(nodeD, nodeF, 1);

    graph.putEdgeValue(nodeF, nodeE, 5);

    final ListMultimap<Node, Node> shortestPaths =
        Dijkstra.computeShortestPath(ImmutableValueGraph.copyOf(graph), nodeA);

    assertAll(
        () -> assertThat(shortestPaths, keyWithSize(nodeE, 3)),
        () -> assertThat(shortestPaths.get(nodeE), contains(nodeA, nodeB, nodeD)),
        () -> assertThat(shortestPaths, keyWithSize(nodeF, 3)),
        () -> assertThat(shortestPaths.get(nodeF), contains(nodeA, nodeB, nodeD)),
        () -> assertThat(shortestPaths, keyWithSize(nodeC, 1)),
        () -> assertThat(shortestPaths.get(nodeC), contains(nodeA)),
        () -> assertThat(shortestPaths, keyWithSize(nodeD, 2)),
        () -> assertThat(shortestPaths.get(nodeD), contains(nodeA, nodeB)));
  }

  static Stream<Supplier<ValueGraphBuilder<Object, Object>>> valueGraphBuilderSupplier() {
    return Stream.of(ValueGraphBuilder::directed, ValueGraphBuilder::undirected);
  }
}
