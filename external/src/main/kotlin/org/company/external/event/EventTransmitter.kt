package org.company.external.event

import java.io.IOException

/**
 * Transmits events to an external event broker.
 *
 * **This type is fixed and can't be changed!**
 */
class EventTransmitter {
    @Suppress("UNUSED_PARAMETER")
    fun transmitEvent(event : Event) {
        throw IOException("Not connected to the event broker.")
    }
}
