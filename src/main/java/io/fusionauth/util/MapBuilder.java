package io.fusionauth.util;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Tyler Scott
 */
public class MapBuilder<K, V> {

  private final Map<K, V> map;

  public MapBuilder() {
    map = new HashMap<>();
  }

  public MapBuilder(K key, V value) {
    this();
    put(key, value);
  }

  public Map<K, V> build() {
    return map;
  }

  public MapBuilder<K, V> put(K key, V value) {
    map.put(key, value);
    return this;
  }

}
