package org.company.monolith.service;

import com.google.common.graph.ValueGraph;
import java.util.Collection;
import java.util.UUID;
import javax.inject.Inject;
import javax.inject.Singleton;
import org.company.external.database.Database;
import org.company.external.uuid.UUIDProducer;
import org.company.library.graph.Node;
import org.company.library.service.Graph;

@Singleton
public final class GraphService {

  @Inject private Database<Graph> database;

  @Inject private UUIDProducer uuidProducer;

  public Collection<Graph> getAll() {
    return database.getAll();
  }

  public void saveNew(final ValueGraph<Node, Integer> graph) {
    database.save(new Graph(uuidProducer.newFullyGuaranteedUniqueUUID(), graph));
  }

  public ValueGraph<Node, Integer> getByUuid(final UUID uuid) {
    return database.getByUuid(uuid).getGraph();
  }
}
