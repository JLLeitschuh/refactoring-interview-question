package org.company.library.algorithm;

import com.google.common.collect.ListMultimap;
import com.google.common.graph.ImmutableValueGraph;
import com.google.common.graph.ValueGraph;
import java.util.Collection;
import java.util.UUID;
import java.util.function.Supplier;
import org.company.library.event.EventSender;
import org.company.library.event.Messages;
import org.company.library.graph.Node;
import org.company.library.service.Graph;
import org.company.library.service.PathsServiceSaver;

public class Algorithm {
  private final Supplier<Collection<Graph>> graphSupplier;
  private final PathsServiceSaver pathsService;
  private final EventSender eventSender;

  public Algorithm(
      final Supplier<Collection<Graph>> graphSupplier,
      final PathsServiceSaver pathsService,
      final EventSender eventSender) {
    this.graphSupplier = graphSupplier;
    this.pathsService = pathsService;
    this.eventSender = eventSender;
    sendEvent(Messages.INSTANTIATED);
  }

  void sendEvent(final String message) {
    eventSender.sendEvent(message);
  }

  /**
   * @param graphUuid The UUID of the graph that this shortest path was computed from.
   * @param sourceNode The source node used in computing the shortest path.
   * @param shortestPath The shortest path computed.
   */
  void saveShortestPath(
      final UUID graphUuid, final Node sourceNode, final ListMultimap<Node, Node> shortestPath) {
    sendEvent(Messages.savingShortestPathForGraphWithSource(graphUuid, sourceNode));
    pathsService.saveNew(graphUuid, sourceNode, shortestPath);
  }

  /**
   * Runs the algorithm for all of the graphs in the database and saves the shortest paths to
   * another database table.
   */
  public void runForAll() {
    graphSupplier
        .get()
        .forEach(
            databaseGraph -> {
              final UUID uuid = databaseGraph.getUuid();
              final ValueGraph<Node, Integer> graph = databaseGraph.getGraph();
              sendEvent(Messages.beginningComputationForGraph(uuid));
              graph
                  .nodes()
                  .forEach(
                      node ->
                          saveShortestPath(
                              uuid,
                              node,
                              Dijkstra.computeShortestPath(
                                  ImmutableValueGraph.copyOf(graph), node)));
              sendEvent(Messages.finishedComputingForGraph(uuid));
            });
  }
}
