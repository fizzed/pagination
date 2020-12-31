package com.fizzed.pagination.cursor;

import java.util.function.Function;

public class StringMapCursor<V> extends MapCursor<String,V> {
 
    public StringMapCursor(Function<V, String> valueSerializer) {
        super(Cursors.STRING_SERIALIZER, valueSerializer);
    }
    
    static protected <V> boolean parse(String value, MapCursor<String,V> cursor, Function<String,V> valueDeserializer) {
        return MapCursor.parse(value, cursor, Cursors.STRING_DESERIALIZER, valueDeserializer);
    }

}