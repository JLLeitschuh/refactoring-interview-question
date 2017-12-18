package org.company.library.service;

import com.google.common.collect.ListMultimap;
import java.util.UUID;
import org.company.library.graph.Node;

public interface IPathService {
  void saveNew(
      final UUID graphUuid, final Node sourceNode, final ListMultimap<Node, Node> shortestPaths);
}
