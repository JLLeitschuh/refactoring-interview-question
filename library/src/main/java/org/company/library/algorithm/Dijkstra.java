package org.company.library.algorithm;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ListMultimap;
import com.google.common.graph.ImmutableValueGraph;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.company.library.graph.Node;

/**
 * Algorithm loosely based upon <a href="http://www.baeldung.com/java-dijkstra">this article</a>.
 */
public final class Dijkstra {

  /**
   * Runs the {@link Dijkstra} algorithm.
   *
   * @param source The source node to compute the graph for.
   * @return A map of the destination node to the list of nodes required to be traversed to reach
   *     the destination from the source.
   */
  public static ImmutableListMultimap<Node, Node> computeShortestPath(
      final ImmutableValueGraph<Node, Integer> graph, final Node source) {
    final InternalNode internalSource = buildInternalGraph(graph, source);
    calculateShortestPathFromSource(internalSource);
    return ImmutableListMultimap.copyOf(buildShortestPaths(internalSource));
  }

  /**
   * Recursively walks the graph to build the {@link InternalNode} graph used for the {@link
   * Dijkstra} algorithm.
   *
   * @param source The source node to build the graph for.
   * @param nodeMap Used to prevent duplicate {@link InternalNode InternalNodes} from being
   *     instantiated.
   * @return Either the {@link InternalNode} from the <code>nodeMap</code> or a new {@link
   *     InternalNode}.
   */
  private static InternalNode buildInternalGraphRecursive(
      final ImmutableValueGraph<Node, Integer> graph,
      final Node source,
      final Map<Node, InternalNode> nodeMap) {
    if (nodeMap.containsKey(source)) {
      return nodeMap.get(source);
    }
    // The value will always be absent at this point.
    final InternalNode internalSourceNode = nodeMap.computeIfAbsent(source, InternalNode::new);
    graph
        .successors(source)
        .forEach(
            adjacent -> {
              final InternalNode internalAdjacent =
                  buildInternalGraphRecursive(graph, adjacent, nodeMap);
              internalSourceNode.addDestination(
                  internalAdjacent, graph.edgeValue(source, adjacent));
            });
    return internalSourceNode;
  }

  private static InternalNode buildInternalGraph(
      final ImmutableValueGraph<Node, Integer> graph, final Node source) {
    return buildInternalGraphRecursive(graph, source, new HashMap<>());
  }

  /**
   * Recursively walks the graph held within the {@link InternalNode} and adding an entry to the
   * {@link ListMultimap} for ever shortest path.
   *
   * @param source The node to append the shortest path for.
   * @param shortestPath The paths extracted from the graph for each node.
   * @return The <code>shortestPath</code> argument passed in.
   */
  private static ListMultimap<Node, Node> buildShortestPathsRecursive(
      final InternalNode source, final ListMultimap<Node, Node> shortestPath) {
    if (shortestPath.containsKey(source.getNode())) {
      return shortestPath;
    }
    shortestPath.putAll(
        source.getNode(),
        source.getShortestPath().stream().map(InternalNode::getNode).collect(Collectors.toList()));
    source
        .getAdjacentNodes()
        .keySet()
        .forEach(adjacent -> buildShortestPathsRecursive(adjacent, shortestPath));
    return shortestPath;
  }

  private static ListMultimap<Node, Node> buildShortestPaths(final InternalNode source) {
    return buildShortestPathsRecursive(source, ArrayListMultimap.create());
  }

  private static void calculateShortestPathFromSource(final InternalNode source) {
    source.setDistance(0);

    final Set<InternalNode> settledNodes = new HashSet<>();
    final Set<InternalNode> unsettledNodes = new HashSet<>();

    unsettledNodes.add(source);

    while (unsettledNodes.size() != 0) {
      final InternalNode currentNode = getLowestDistanceNode(unsettledNodes);
      unsettledNodes.remove(currentNode);
      for (final Map.Entry<InternalNode, Integer> adjacencyPair :
          currentNode.getAdjacentNodes().entrySet()) {
        final InternalNode adjacentNode = adjacencyPair.getKey();
        final Integer edgeWeight = adjacencyPair.getValue();
        if (!settledNodes.contains(adjacentNode)) {
          calculateMinimumDistance(adjacentNode, edgeWeight, currentNode);
          unsettledNodes.add(adjacentNode);
        }
      }
      settledNodes.add(currentNode);
    }
  }

  private static InternalNode getLowestDistanceNode(Set<InternalNode> unsettledNodes) {
    InternalNode lowestDistanceNode = null;
    int lowestDistance = Integer.MAX_VALUE;
    for (final InternalNode node : unsettledNodes) {
      int nodeDistance = node.getDistance();
      if (nodeDistance < lowestDistance) {
        lowestDistance = nodeDistance;
        lowestDistanceNode = node;
      }
    }
    return lowestDistanceNode;
  }

  private static void calculateMinimumDistance(
      final InternalNode evaluationNode, final int edgeWeigh, final InternalNode sourceNode) {
    final Integer sourceDistance = sourceNode.getDistance();
    if (sourceDistance + edgeWeigh < evaluationNode.getDistance()) {
      evaluationNode.setDistance(sourceDistance + edgeWeigh);
      final List<InternalNode> shortestPath = new LinkedList<>(sourceNode.getShortestPath());
      shortestPath.add(sourceNode);
      evaluationNode.setShortestPath(shortestPath);
    }
  }
}
