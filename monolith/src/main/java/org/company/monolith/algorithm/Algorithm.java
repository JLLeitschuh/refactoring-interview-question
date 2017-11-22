package org.company.monolith.algorithm;

import com.google.common.collect.ListMultimap;
import com.google.common.graph.ImmutableValueGraph;
import com.google.common.graph.ValueGraph;
import java.util.UUID;
import org.company.external.event.EventTransmitter;
import org.company.external.uuid.UUIDProducer;
import org.company.library.event.Messages;
import org.company.library.graph.Node;
import org.company.monolith.service.GraphService;
import org.company.monolith.service.PathsService;

class Algorithm {
  private final GraphService graphService;
  private final PathsService pathsService;
  private final EventTransmitter eventTransmitter;
  private final UUIDProducer uuidProducer;

  Algorithm(
      final GraphService graphService,
      final PathsService pathsService,
      final EventTransmitter eventTransmitter,
      final UUIDProducer uuidProducer) {
    this.graphService = graphService;
    this.pathsService = pathsService;
    this.eventTransmitter = eventTransmitter;
    this.uuidProducer = uuidProducer;
    sendEvent(Messages.INSTANTIATED);
  }

  void sendEvent(final String message) {
    eventTransmitter.transmitEvent(
        new AlgorithmEvent(uuidProducer.newFullyGuaranteedUniqueUUID(), message));
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
  void runForAll() {
    graphService
        .getAll()
        .forEach(
            databaseGraph -> {
              final UUID uuid = databaseGraph.getUuid();
              final ValueGraph<Node, Integer> graph = databaseGraph.getGraph();
              sendEvent(Messages.beginningComputationForGraph(uuid));
              final Dijkstra dijkstra = new Dijkstra(ImmutableValueGraph.copyOf(graph));
              graph
                  .nodes()
                  .forEach(node -> saveShortestPath(uuid, node, dijkstra.computeForSource(node)));
              sendEvent(Messages.finishedComputingForGraph(uuid));
            });
  }
}
