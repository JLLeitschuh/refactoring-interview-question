package org.company.library.event;

public interface EventSender {
  /**
   * Sends an event about something happening.
   *
   * @param message The message to be sent in the event.
   */
  void sendEvent(String message);
}
