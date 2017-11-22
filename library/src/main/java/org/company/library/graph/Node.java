package org.company.library.graph;

import static java.util.Objects.requireNonNull;

/**
 * Represents a node in a graph. Really is just a placeholder for any object with a consistent
 * {@link Object#hashCode()} and {@link Object#equals(Object)} method.
 */
public class Node {
  private final String name;

  public Node(final String name) {
    this.name = requireNonNull(name, "name");
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Node node = (Node) o;

    return name.equals(node.name);
  }

  @Override
  public int hashCode() {
    return name.hashCode();
  }

  @Override
  public String toString() {
    return "Node{" + "name='" + name + '\'' + '}';
  }
}
