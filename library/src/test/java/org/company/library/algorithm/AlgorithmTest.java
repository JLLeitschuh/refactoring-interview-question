package org.company.library.algorithm;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;

import com.google.common.collect.ImmutableMap;
import com.google.common.graph.ImmutableValueGraph;
import com.google.common.graph.MutableValueGraph;
import com.google.common.graph.ValueGraph;
import com.google.common.graph.ValueGraphBuilder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.company.library.event.EventSender;
import org.company.library.event.Messages;
import org.company.library.graph.Node;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

@Disabled("These tests should all pass when you are done.")
public class AlgorithmTest {
  /**
   * @param graphs A fake of all of the graphs in the database.
   * @param eventSender The sender that will send all events.
   */
  static Algorithm create(
      final Map<UUID, ValueGraph<Node, Integer>> graphs, final EventSender eventSender) {
    // TODO: Your job is to implement some logic to make these tests pass.
    return null;
  }

  /** Calls {@link #create(Map, EventSender)} with only one element in the Map. */
  static Algorithm create(
      final UUID uuid, final ValueGraph<Node, Integer> graph, final EventSender eventSender) {
    return create(ImmutableMap.of(uuid, graph), eventSender);
  }

  /** Mock implementation of an {@link EventSender} to be used in testing. */
  static class MockEventSender implements EventSender {
    private List<String> messages = new ArrayList<>();

    @Override
    public void sendEvent(final String message) {
      messages.add(message);
    }
  }

  static ImmutableValueGraph<Node, Integer> emptyGraph() {
    return ImmutableValueGraph.copyOf(ValueGraphBuilder.directed().build());
  }

  @Test
  void algorithmSendsInstantiation() {
    final MockEventSender eventSender = new MockEventSender();
    final Algorithm algorithm = create(ImmutableMap.of(), eventSender);
    assertThat(eventSender.messages, contains(Messages.INSTANTIATED));
    algorithm.runForAll();
    // If you have nothing having the algorithm being run against then no additional events should be emmited.
    assertThat(eventSender.messages, contains(Messages.INSTANTIATED));
  }

  @Test
  void algorithmSendsStartingAndFinishedWhenGraphPassed() {
    final MockEventSender eventSender = new MockEventSender();
    final UUID uuid = UUID.randomUUID();
    final Algorithm algorithm = create(uuid, emptyGraph(), eventSender);
    assertThat(eventSender.messages, contains(Messages.INSTANTIATED));
    algorithm.runForAll();
    assertThat(
        eventSender.messages,
        contains(
            Messages.INSTANTIATED,
            Messages.beginningComputationForGraph(uuid),
            Messages.finishedComputingForGraph(uuid)));
  }

  @Test
  void algorithmSendsSavingWhenPathsAreComputed() {
    final MockEventSender eventSender = new MockEventSender();
    final UUID uuid = UUID.randomUUID();
    final MutableValueGraph<Node, Integer> graph = ValueGraphBuilder.directed().build();
    final Node nodeA = new Node("A");
    final Node nodeB = new Node("B");
    graph.putEdgeValue(nodeA, nodeB, 39);
    final Algorithm algorithm = create(uuid, graph, eventSender);
    assertThat(eventSender.messages, contains(Messages.INSTANTIATED));
    algorithm.runForAll();
    assertThat(
        eventSender.messages,
        contains(
            Messages.INSTANTIATED,
            Messages.beginningComputationForGraph(uuid),
            Messages.savingShortestPathForGraphWithSource(uuid, nodeA),
            Messages.savingShortestPathForGraphWithSource(uuid, nodeB),
            Messages.finishedComputingForGraph(uuid)));
  }

  /* ******************************************************** *
   * What other tests might be relevant?                      *
   * Consider testing that shortest paths actually get saved? *
   * ******************************************************** */
}
