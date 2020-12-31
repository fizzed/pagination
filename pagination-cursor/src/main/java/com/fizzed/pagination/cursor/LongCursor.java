package com.fizzed.pagination.cursor;

public class LongCursor extends ValueCursor<Long> {
    
    public LongCursor() {
        super(Cursors.LONG_SERIALIZER);
    }
    
    static public ValueCursor<Long> of(Long value) {
        LongCursor cursor = new LongCursor();
        cursor.setValue(value);
        return cursor;
    }
    
    static public ValueCursor<Long> parse(String value) {
        LongCursor cursor = new LongCursor();
        ValueCursor.parse(value, cursor, Cursors.LONG_DESERIALIZER);
        return cursor;
    }
    
}