package com.fizzed.pagination.cursor;

public class StringStringMapCursor extends StringMapCursor<String> {
    
    public StringStringMapCursor() {
        super(Cursors.STRING_SERIALIZER);
    }
    
    static public StringStringMapCursor parse(String value) {
        StringStringMapCursor cursor = new StringStringMapCursor();
        StringMapCursor.parse(value, cursor, Cursors.STRING_SERIALIZER);
        return cursor;
    }
    
}