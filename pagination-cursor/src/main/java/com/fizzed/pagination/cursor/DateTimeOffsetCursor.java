package com.fizzed.pagination.cursor;

import com.fizzed.crux.util.Maybe;
import static com.fizzed.crux.util.Maybe.maybe;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

/**
 * A cursor that helps with deep scrolling where data is partitioned by a relatively
 * unique datetime.
 * 
 * @author jjlauer
 */
public class DateTimeOffsetCursor extends StringMapCursor<Long> {
    
    static public final String KEY_DATETIME = "dt";
    static public final String KEY_OFFSET = "o";
    static public final String KEY_TOTAL_COUNT = "tc";
    
    public DateTimeOffsetCursor() {
        super(Cursors.LONG_SERIALIZER);
    }
    
    static public DateTimeOffsetCursor parse(String value) {
        DateTimeOffsetCursor cursor = new DateTimeOffsetCursor();
        StringMapCursor.parse(value, cursor, Cursors.LONG_DESERIALIZER);
        return cursor;
    }
    
    static public DateTimeOffsetCursor empty() {
        return new DateTimeOffsetCursor();
    }
    
//    public OffsetCursor next(Long limit, Long knownTotalCount) {
//        Long nextOffset = Cursors.nextLongOffset(this.getValue().orElse(null), limit, knownTotalCount);
//        return OffsetCursor.of(nextOffset);
//    }
//    
//    public OffsetCursor previous(Long limit) {
//        Long previousOffset = Cursors.previousLongOffset(this.getValue().orElse(null), limit);
//        return OffsetCursor.of(previousOffset);
//    }
    
    private Maybe<Long> coalesceTotalCount(Long knownTotalCount) {
        return knownTotalCount != null ? maybe(knownTotalCount) : this.getTotalCount();
    }
    
    // NOTE: the one flaw with using offsets only & then dates + offsets
    // is if the dates are unique when switching between the methods then
    // there is no way of knowing that -- one method of fixing it is potentially
    // to keep track of the date uniqueness -- but its an edge case I don't feel
    // like solving right now...
    
    public <T> DateTimeOffsetCursor next(Long limit, Long knownTotalCount, List<T> items, Function<T,DateTime> dateTimeMethod, Long maxAllowedOffset) {
        Long offset = this.getOffset().orElse(null);
        
        // can we simple offsets for pagination?
        if (limit != null && (offset == null || (offset+limit) <= maxAllowedOffset)) {
            Long nextOffset = Cursors.nextLongOffset(offset, limit, knownTotalCount);
            
            if (nextOffset == null) {
                return null;
            }
            
            // build the next cursor
            DateTimeOffsetCursor next = new DateTimeOffsetCursor();
            this.getDateTime().ifPresent(next::setDateTime);
            next.setOffset(nextOffset);
            this.coalesceTotalCount(knownTotalCount).ifPresent(next::setTotalCount);
            return next;
        }
        
        return this.next(limit, knownTotalCount, items, dateTimeMethod);
    }
    
    public <T> DateTimeOffsetCursor next(Long limit, Long knownTotalCount, List<T> items, Function<T,DateTime> dateTimeMethod) {
        if (items == null || items.isEmpty()) {
            return null;
        }
        
        // are we at the limit (then no more can be queried)
        if (limit != null && items.size() < limit) {
            return null;
        }
        
        // check the last item in list to see if its a continuation of the
        // current cursor or not
        long offset = 1L;
        T lastItem = items.get(items.size()-1);
        DateTime lastDateTime = dateTimeMethod.apply(lastItem);
        
        if (lastDateTime.equals(this.getDateTime().orElse(null))) {
            // wow this means the next cursor cannot move the datetime forward
            // we can only simply bump up the offset again
            offset = this.getOffset().get() + items.size();
        } else {
            // we can move the datetime forward - let's see by how much
            // start at the 2nd to last and see if its the same datetime
            for (int i = items.size()-2; i >= 0; i--) {
                T item = items.get(i);
                DateTime dt = dateTimeMethod.apply(item);
                if (lastDateTime.isEqual(dt)) {
                    offset++;
                }
            }
        }
        
        // build the next cursor
        DateTimeOffsetCursor next = new DateTimeOffsetCursor();
        next.setDateTime(lastDateTime);
        next.setOffset(offset);
        this.coalesceTotalCount(knownTotalCount).ifPresent(next::setTotalCount);

        return next;
    }
    
    public DateTimeOffsetCursor previous(Long limit) {
        // if we're offset only we can do a standard previous
        if (this.getDateTime().isPresent()) {
            return null;
        }
        
        Long previousOffset = Cursors.previousLongOffset(this.getOffset().orElse(null), limit);
        
        if (previousOffset == null) {
            return null;
        }
        
        DateTimeOffsetCursor previous = new DateTimeOffsetCursor();
        this.getDateTime().ifPresent(previous::setDateTime);
        previous.setOffset(previousOffset);
        this.coalesceTotalCount(null).ifPresent(previous::setTotalCount);
        return previous;
    }
    
    public Maybe<DateTime> getDateTime() {
        return this.get(KEY_DATETIME)
            .map(v -> new DateTime(v, DateTimeZone.UTC));
    }
    
    public void setDateTime(DateTime value) {
        Long l = maybe(value).map(v -> v.getMillis()).orElse(null);
        this.put(KEY_DATETIME, l);
    }
    
    public Maybe<Long> getOffset() {
        return this.get(KEY_OFFSET);
    }
    
    public void setOffset(Long value) {
        this.put(KEY_OFFSET, value);
    }
    
    public Maybe<Long> getTotalCount() {
        return this.get(KEY_TOTAL_COUNT);
    }
    
    public void setTotalCount(Long value) {
        this.put(KEY_TOTAL_COUNT, value);
    }
    
}