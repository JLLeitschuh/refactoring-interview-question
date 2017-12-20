package org.company.library.algorithm;

import com.google.common.collect.ListMultimap;
import com.google.common.graph.ImmutableValueGraph;
import java.util.UUID;
import org.company.library.event.EventSender;
import org.company.library.event.Messages;
import org.company.library.graph.Node;
import org.company.library.service.IGraphService;
import org.company.library.service.IPathService;

public final class Algorithm {
  private final IGraphService graphService;
  private final IPathService pathsService;
  private final EventSender eventSender;

  public Algorithm(
      final IGraphService graphService,
      final IPathService pathsService,
      final EventSender eventSender) {
    this.graphService = graphService;
    this.pathsService = pathsService;
    this.eventSender = eventSender;
    sendEvent(Messages.INSTANTIATED);
  }

  private void sendEvent(final String message) {
    eventSender.sendEvent(message);
  }

  /**
   * @param graphUuid The UUID of the graph that this shortest path was computed from.
   * @param sourceNode The source node used in computing the shortest path.
   * @param shortestPath The shortest path computed.
   */
  private void saveShortestPath(
      final UUID graphUuid, final Node sourceNode, final ListMultimap<Node, Node> shortestPath) {
    sendEvent(Messages.savingShortestPathForGraphWithSource(graphUuid, sourceNode));
    pathsService.saveNew(graphUuid, sourceNode, shortestPath);
  }

  /**
   * Runs the algorithm for all of the graphs in the database and saves the shortest paths to
   * another database table.
   */
  public void runForAll() {
    graphService
        .getAll()
        .forEach(
            databaseGraph -> {
              final UUID uuid = databaseGraph.getUuid();
              final ImmutableValueGraph<Node, Integer> graph =
                  ImmutableValueGraph.copyOf(databaseGraph.getGraph());
              sendEvent(Messages.beginningComputationForGraph(uuid));
              graph
                  .nodes()
                  .forEach(
                      node ->
                          saveShortestPath(uuid, node, Dijkstra.computeShortestPath(graph, node)));
              sendEvent(Messages.finishedComputingForGraph(uuid));
            });
  }
}
