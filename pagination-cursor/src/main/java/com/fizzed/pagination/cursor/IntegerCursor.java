package com.fizzed.pagination.cursor;

public class IntegerCursor extends ValueCursor<Integer> {
    
    public IntegerCursor() {
        super(Cursors.INTEGER_SERIALIZER);
    }
    
    static public ValueCursor<Integer> of(Integer value) {
        IntegerCursor cursor = new IntegerCursor();
        cursor.setValue(value);
        return cursor;
    }
    
    static public ValueCursor<Integer> parse(String value) {
        IntegerCursor cursor = new IntegerCursor();
        ValueCursor.parse(value, cursor, Cursors.INTEGER_DESERIALIZER);
        return cursor;
    }
    
}