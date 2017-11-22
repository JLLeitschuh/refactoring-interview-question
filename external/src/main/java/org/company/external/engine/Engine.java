package org.company.external.engine;

/**
 * Drives some sort of action to be executed.
 *
 * <p><b>This type is fixed and can't be changed!</b>
 */
public interface Engine {
  /** Does some bit of work. */
  @SuppressWarnings("unused")
  void doWork();
}
