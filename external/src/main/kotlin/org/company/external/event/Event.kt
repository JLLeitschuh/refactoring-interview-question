package org.company.external.event

import java.util.UUID

/**
 * An event being transmitted.
 *
 * **This type is fixed and can't be changed!**
 */
interface Event {
    /**
     * Must be unique across the entire system.
     */
    val uuid: UUID
    /**
     * The message associated with the event.
     */
    val message: String
}
