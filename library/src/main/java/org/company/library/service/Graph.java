package org.company.library.service;

import com.google.common.graph.ValueGraph;
import java.util.UUID;
import org.company.library.graph.Node;

/** Represents a Graph that can be saved in the database. */
public final class Graph {
  private final UUID uuid;
  private final ValueGraph<Node, Integer> graph;

  public Graph(final UUID uuid, final ValueGraph<Node, Integer> graph) {
    this.uuid = uuid;
    this.graph = graph;
  }

  public UUID getUuid() {
    return uuid;
  }

  public ValueGraph<Node, Integer> getGraph() {
    return graph;
  }
}
