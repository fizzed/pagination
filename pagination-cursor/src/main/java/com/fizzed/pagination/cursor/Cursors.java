package com.fizzed.pagination.cursor;

import static com.fizzed.crux.util.MoreObjects.isBlank;
import java.util.function.Function;

public class Cursors {
 
    static public final Function<String,String> STRING_DESERIALIZER = value -> {
        if (isBlank(value)) {
            return null;
        }
        return value;
    };
    
    static public final Function<String,String> STRING_SERIALIZER = value -> {
        return !isBlank(value) ? value : null;
    };
    
    static public final Function<String,Integer> INTEGER_DESERIALIZER = value -> {
        if (isBlank(value)) {
            return null;
        }
        try {
            return Integer.valueOf(value);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(e);
        }
    };
    
    static public final Function<Integer,String> INTEGER_SERIALIZER = value -> {
        return value != null ? value.toString() : null;
    };
    
    static public final Function<String,Long> LONG_DESERIALIZER = value -> {
        if (isBlank(value)) {
            return null;
        }
        try {
            return Long.valueOf(value);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(e);
        }
    };
    
    static public final Function<Long,String> LONG_SERIALIZER = value -> {
        return value != null ? value.toString() : null;
    };
    
    /**
     * Calculates the next cursor based on adding the limit to the current 
     * offset.  If the knownCount is supplied then only a cursor will be returned
     * if the next offset does not exceed the size.  Please note this is calculated
     * with a zero-based index in mind.
     * @param knownCount If the total count is known otherwise supply null to
     *      skip this check.
     * @return Null if next cursor cannot be calculated or does not exist.
     */
    static public Long nextLongOffset(Long offset, Long limit, Long knownCount) {
        if (limit == null) {
            return null;
        }

        if (offset == null) {
            offset = 0L;        // assume zero
        }
        
        Long nextOffset = offset + limit;
        
        // we know for a fact there's not next offset
        if (knownCount != null && nextOffset >= knownCount) {
            return null;
        }
        
        return nextOffset;
    }
    
    static public ValueCursor<Long> nextLongOffsetCursor(ValueCursor<Long> current, Long limit, Long knownCount) {
        if (current == null) {
            return null;
        }
        Long nextOffset = nextLongOffset(current.getValue().orElse(null), limit, knownCount);
        return LongCursor.of(nextOffset);
    }
    
    static public Long previousLongOffset(Long offset, Long limit) {
        if (limit == null) {
            return null;
        }

        // no previous in this case
        if (offset == null || offset == 0L) {
            return null;
        }
        
        Long previousOffset = offset - limit;
        
        if (previousOffset < 0L) {
            previousOffset = 0L;
        }

        return previousOffset;
    }
    
    static public ValueCursor<Long> previousLongOffsetCursor(ValueCursor<Long> current, Long limit) {
        if (current == null) {
            return null;
        }
        Long previousOffset = previousLongOffset(current.getValue().orElse(null), limit);
        return LongCursor.of(previousOffset);
    }
    
}