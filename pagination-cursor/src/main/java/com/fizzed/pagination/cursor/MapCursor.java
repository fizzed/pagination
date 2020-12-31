package com.fizzed.pagination.cursor;

import com.fizzed.crux.util.Maybe;
import static com.fizzed.crux.util.Maybe.maybe;
import static com.fizzed.crux.util.MoreObjects.isBlank;
import static com.fizzed.crux.util.MoreObjects.isEmpty;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class MapCursor<K,V> implements Cursor {
 
    private final Function<K,String> keySerializer;
    private final Function<V,String> valueSerializer;
    private final Map<K,V> values;

    public MapCursor(
            Function<K,String> keySerializer,
            Function<V,String> valueSerializer) {
        this.keySerializer = keySerializer;
        this.valueSerializer = valueSerializer;
        this.values = new LinkedHashMap<>();
    }
    
    public Map<K,V> getValues() {
        return values;
    }
    
    public MapCursor<K,V> remove(K key) {
        this.values.remove(key);
        return this;
    }
    
    public MapCursor<K,V> put(K key, V value) {
        this.values.put(key, value);
        return this;
    }
    
    public MapCursor<K,V> putIfAbsent(K key, V value) {
        if (!this.values.containsKey(key)) {
            this.values.put(key, value);
        }
        return this;
    }
    
    public Maybe<V> get(K key) {
        return maybe(this.values.get(key));
    }
    
    private String toString(K key, V value) {
        String _key = this.keySerializer.apply(key);
        String _value = this.valueSerializer.apply(value);
        
        if (_value == null) {
            return _key;
        }
        
        return _key + "-" + value;
    }
    
    @Override
    public String toString() {
        if (isEmpty(this.values)) {
            return null;
        }
        return this.values.entrySet().stream()
            .map(v -> toString(v.getKey(), v.getValue()))
            .collect(Collectors.joining(","));
    }
    
    static protected <K,V> boolean parse(String value, MapCursor<K,V> cursor, Function<String,K> keyDeserializer, Function<String,V> valueDeserializer) {
        if (isBlank(value)) {
            return false;
        }
        String[] tokens = value.split(",");
        for (String token : tokens) {
            String[] pair = token.split("-");
            if (pair.length != 2) {
                throw new IllegalArgumentException("Invalid cursor entry " + token);
            }
            K key = keyDeserializer.apply(pair[0]);
            V v = valueDeserializer.apply(pair[1]);
            cursor.put(key, v);
        }
        return true;
    }
    
}