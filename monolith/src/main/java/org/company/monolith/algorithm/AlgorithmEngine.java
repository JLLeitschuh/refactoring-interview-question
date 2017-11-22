package org.company.monolith.algorithm;

import javax.inject.Inject;
import javax.inject.Singleton;
import org.company.external.engine.Engine;
import org.company.external.event.EventTransmitter;
import org.company.external.uuid.UUIDProducer;
import org.company.monolith.service.GraphService;
import org.company.monolith.service.PathsService;

/** An engine that drives the execution of the underlying algorithms. */
@Singleton
public class AlgorithmEngine implements Engine {

  private final Algorithm algorithm;

  @Inject
  AlgorithmEngine(
      final GraphService graphService,
      final PathsService pathsService,
      final EventTransmitter eventTransmitter,
      final UUIDProducer uuidProducer) {
    this.algorithm = new Algorithm(graphService, pathsService, eventTransmitter, uuidProducer);
  }

  @Override
  public void doWork() {
    algorithm.runForAll();
  }
}
