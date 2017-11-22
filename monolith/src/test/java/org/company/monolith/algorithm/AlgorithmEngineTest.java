package org.company.monolith.algorithm;

import static org.junit.jupiter.api.Assumptions.assumeTrue;

import org.company.external.database.Database;
import org.company.external.event.EventTransmitter;
import org.company.external.uuid.UUIDProducer;
import org.company.monolith.service.GraphService;
import org.company.monolith.service.PathsService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 * Don't worry about these tests passing. However, I want to make sure that this file compiles when
 * you are done.
 */
class AlgorithmEngineTest {
  @BeforeAll
  static void checkDatabaseRunning() {
    assumeTrue(Database.isConnected(), "Database is not connected");
  }

  @Test
  void haveAlgorithmsDoWork(
      final GraphService graphService,
      final PathsService pathsService,
      final EventTransmitter eventTransmitter,
      final UUIDProducer uuidProducer) {
    new AlgorithmEngine(graphService, pathsService, eventTransmitter, uuidProducer).doWork();
  }
}
