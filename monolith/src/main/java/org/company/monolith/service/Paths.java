package org.company.monolith.service;

import com.google.common.collect.ListMultimap;
import java.util.UUID;
import org.company.library.graph.Node;

public final class Paths {
  @SuppressWarnings("unused")
  private final UUID uuid;
  /** The {@link UUID} of the graph that this path was calculated using. */
  @SuppressWarnings("unused")
  private final UUID graphUuid;

  @SuppressWarnings("unused")
  private final Node sourceNode;

  @SuppressWarnings("unused")
  private final ListMultimap<Node, Node> shortestPaths;

  public Paths(
      final UUID uuid,
      final UUID graphUuid,
      final Node sourceNode,
      final ListMultimap<Node, Node> shortestPaths) {
    this.uuid = uuid;
    this.graphUuid = graphUuid;
    this.sourceNode = sourceNode;
    this.shortestPaths = shortestPaths;
  }
}
