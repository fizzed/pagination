package com.fizzed.pagination.cursor;

public class StringLongMapCursor extends StringMapCursor<Long> {
    
    public StringLongMapCursor() {
        super(Cursors.LONG_SERIALIZER);
    }
    
    static public StringLongMapCursor parse(String value) {
        StringLongMapCursor cursor = new StringLongMapCursor();
        StringMapCursor.parse(value, cursor, Cursors.LONG_DESERIALIZER);
        return cursor;
    }
    
}