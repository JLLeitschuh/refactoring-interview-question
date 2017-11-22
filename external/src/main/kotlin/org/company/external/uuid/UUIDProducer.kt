package org.company.external.uuid

import java.util.UUID

/**
 * Produces new [UUID] that are guaranteed to be unique across the entire system.
 *
 * **This type is fixed and can't be changed!**
 */
class UUIDProducer {

    /**
     * Ensures that every [UUID] is 100% unique across the entire system.
     */
    fun newFullyGuaranteedUniqueUUID() : UUID {
        throw UnsupportedOperationException("The database isn't connected to provide unique UUID's")
    }
}
