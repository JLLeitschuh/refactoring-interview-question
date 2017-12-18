package org.company.library.algorithm

import org.company.library.graph.Node

/**
 * Internal version of a [Node] used in the [Dijkstra] algorithm.
 */
internal data class InternalNode(val node: Node) {
    var shortestPath : MutableList<InternalNode> = mutableListOf()
    var distance : Int = Int.MAX_VALUE
    val adjacentNodes: MutableMap<InternalNode, Int> = mutableMapOf()

    fun addDestination(destination: InternalNode, distance: Int) {
        adjacentNodes.put(destination, distance)
    }
}
