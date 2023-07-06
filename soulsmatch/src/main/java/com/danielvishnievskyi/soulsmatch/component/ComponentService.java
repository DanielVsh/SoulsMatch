package com.danielvishnievskyi.soulsmatch.component;

public interface ComponentService<Entity, Type> {
  Entity createIfNotFound(Type target);
}
