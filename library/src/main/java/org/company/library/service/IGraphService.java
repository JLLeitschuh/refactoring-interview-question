package org.company.library.service;

import java.util.Collection;

@FunctionalInterface
public interface IGraphService {
  Collection<Graph> getAll();
}
