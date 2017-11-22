package org.company.monolith.service;

import com.google.common.collect.ListMultimap;
import java.util.UUID;
import javax.inject.Inject;
import javax.inject.Singleton;
import org.company.external.database.Database;
import org.company.external.uuid.UUIDProducer;
import org.company.library.graph.Node;
import org.company.library.service.Paths;
import org.company.library.service.PathsServiceSaver;

@Singleton
public class PathsService implements PathsServiceSaver {
  @Inject private Database<Paths> database;

  @Inject private UUIDProducer uuidProducer;

  /**
   * @param graphUuid The UUID of the graph that this path was generated from.
   * @param sourceNode The source node that this path was generated for.
   * @param shortestPaths All of the paths calculated for this source node.
   */
  @Override
  public void saveNew(
      final UUID graphUuid, final Node sourceNode, final ListMultimap<Node, Node> shortestPaths) {
    database.save(
        new Paths(
            uuidProducer.newFullyGuaranteedUniqueUUID(), graphUuid, sourceNode, shortestPaths));
  }
}
