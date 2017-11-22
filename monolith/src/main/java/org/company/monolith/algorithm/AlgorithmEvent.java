package org.company.monolith.algorithm;

import java.util.UUID;
import org.company.external.event.Event;
import org.jetbrains.annotations.NotNull;

/** An event that is emitted as a part of the algorithm running. */
public final class AlgorithmEvent implements Event {
  private final UUID uuid;
  private final String message;

  public AlgorithmEvent(final UUID uuid, final String message) {
    this.uuid = uuid;
    this.message = message;
  }

  @NotNull
  @Override
  public UUID getUuid() {
    return uuid;
  }

  @NotNull
  @Override
  public String getMessage() {
    return message;
  }
}
