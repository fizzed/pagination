package com.fizzed.pagination.cursor;

public class OffsetCursor extends ValueCursor<Long> {
    
    public OffsetCursor() {
        super(Cursors.LONG_SERIALIZER);
    }
    
    public OffsetCursor next(Long limit, Long knownTotalCount) {
        Long nextOffset = Cursors.nextLongOffset(this.getValue().orElse(null), limit, knownTotalCount);
        return OffsetCursor.of(nextOffset);
    }
    
    public OffsetCursor previous(Long limit) {
        Long previousOffset = Cursors.previousLongOffset(this.getValue().orElse(null), limit);
        return OffsetCursor.of(previousOffset);
    }
    
    static public OffsetCursor empty() {
        return of(0L);
    }
    
    static public OffsetCursor of(Long value) {
        OffsetCursor cursor = new OffsetCursor();
        cursor.setValue(value);
        return cursor;
    }
    
    static public OffsetCursor parse(String value) {
        OffsetCursor cursor = new OffsetCursor();
        ValueCursor.parse(value, cursor, Cursors.LONG_DESERIALIZER);
        return cursor;
    }
    
}