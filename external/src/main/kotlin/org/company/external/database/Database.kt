package org.company.external.database

import com.google.common.collect.ImmutableCollection
import java.sql.SQLException
import java.util.UUID

/**
 * A database holding some table of elements of type [T].
 *
 * **This type is fixed and can't be changed!**
 *
 * @param T the type held in this database.
 */
class Database<T> {

    companion object {
        @JvmStatic
        fun isConnected() = false
    }

    val all : ImmutableCollection<T>
        get() { notConnected() }

    @Suppress("UNUSED_PARAMETER")
    fun getByUuid(uuid : UUID) : T = notConnected()

    @Suppress("UNUSED_PARAMETER")
    fun save(t : T) : Boolean = notConnected()

    private inline fun notConnected() : Nothing {
        throw SQLException("Not connected to a database")
    }
}
