package com.fizzed.pagination.cursor;

import com.fizzed.crux.util.Maybe;
import static com.fizzed.crux.util.Maybe.maybe;
import static com.fizzed.crux.util.MoreObjects.isBlank;
import java.util.function.Function;

public class ValueCursor<V> implements Cursor {
    
    private final Function<V,String> valueSerializer;
    private V value;

    public ValueCursor(
            Function<V,String> valueSerializer) {
        this.valueSerializer = valueSerializer;
    }
    
    public Maybe<V> getValue() {
        return maybe(this.value );
    }

    public void setValue(V value) {
        this.value = value;
    }
    
    @Override
    public String toString() {
        if (this.value == null) {
            return null;
        }
        return this.valueSerializer.apply(value);
    }
    
    static public <V> boolean parse(String value, ValueCursor<V> cursor, Function<String,V> valueDeserializer) {
        if (isBlank(value)) {
            return false;
        }
        V _value = valueDeserializer.apply(value);
        cursor.setValue(_value);
        return true;
    }
    
}