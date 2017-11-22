package org.company.library.event;

import java.util.UUID;
import org.company.library.graph.Node;

/** Kind of impractical in a real code base, but this makes it easier for me to write test. */
public final class Messages {
  public static final String INSTANTIATED = "Algorithm instantiated";

  public static String beginningComputationForGraph(UUID uuid) {
    return "Beginning computation for graph: " + uuid;
  }

  public static String finishedComputingForGraph(UUID uuid) {
    return "Finished computing for graph: " + uuid;
  }

  public static String savingShortestPathForGraphWithSource(UUID uuid, Node source) {
    return "Saving shortest path for graph " + uuid + " with node " + source;
  }
}
