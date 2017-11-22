package org.company.library.service;

import com.google.common.collect.ListMultimap;
import java.util.UUID;
import org.company.library.graph.Node;

public interface PathsServiceSaver {
  void saveNew(UUID graphUuid, Node sourceNode, ListMultimap<Node, Node> shortestPath);
}
