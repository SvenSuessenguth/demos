package org.cc.testing.dbunit.service;

import org.cc.testing.dbunit.model.Entity;

/**
 * Exception on creating (first time persisting via JPA) an entity.
 */
public class CreateEntityException extends RuntimeException {
  public CreateEntityException(Entity e, Throwable throwable) {
    super("Error on creating entity with type '%s'".formatted(e.getClass().getSimpleName()),
      throwable);
  }
}