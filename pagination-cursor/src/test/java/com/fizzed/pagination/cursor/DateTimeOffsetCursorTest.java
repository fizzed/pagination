package com.fizzed.pagination.cursor;

import com.fizzed.pagination.cursor.DateTimeOffsetCursor;
import java.util.ArrayList;
import java.util.List;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasEntry;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.junit.Test;

public class DateTimeOffsetCursorTest {
 
    @Test
    public void parse() {
        DateTimeOffsetCursor cursor = DateTimeOffsetCursor.parse("dt-1531164721899,o-10");
        
        assertThat(cursor.getValues(), hasEntry("dt", 1531164721899L));
        assertThat(cursor.getDateTime().orElse(null), is(new DateTime(1531164721899L, DateTimeZone.UTC)));
        assertThat(cursor.getValues(), hasEntry("o", 10L));
        
        assertThat(cursor.toString(), is("dt-1531164721899,o-10"));
        
        cursor.setOffset(null);
        
        assertThat(cursor.toString(), is("dt-1531164721899,o"));
        
        cursor.remove("o");
        
        assertThat(cursor.toString(), is("dt-1531164721899"));
    }
    
    
    static public class TestRecord {
        
        private final DateTime dt;
        private final String a;

        public TestRecord(DateTime dt, String a) {
            this.dt = dt;
            this.a = a;
        }

        public DateTime getDt() {
            return dt;
        }

        public String getA() {
            return a;
        }

    }
    
    @Test
    public void next() {
        DateTimeOffsetCursor cursor = new DateTimeOffsetCursor();
        DateTimeOffsetCursor next = null;
        
        List<TestRecord> records = new ArrayList<>();
        
        next = cursor.next(null, null, records, TestRecord::getDt);
        
        assertThat(next, is(nullValue()));
        
        records.add(new TestRecord(DateTime.parse("2018-07-09T23:59:59.999Z"), "a1"));
        
        next = cursor.next(null, null, records, TestRecord::getDt);
        
        assertThat(next.getTotalCount().orElse(null), is(nullValue()));
        assertThat(next.getOffset().orElse(null), is(1L));
        assertThat(next.getDateTime().orElse(null), is(DateTime.parse("2018-07-09T23:59:59.999Z")));
        assertThat(next.toString(), is("dt-1531180799999,o-1"));
        
        // add another record with EXACT same datetime
        records.add(new TestRecord(DateTime.parse("2018-07-09T23:59:59.999Z"), "a2"));
        
        next = cursor.next(null, null, records, TestRecord::getDt);
        
        assertThat(next.getOffset().orElse(null), is(2L));
        assertThat(next.getDateTime().orElse(null), is(DateTime.parse("2018-07-09T23:59:59.999Z")));
        
        // add another record with different datetime
        records.add(new TestRecord(DateTime.parse("2018-07-09T22:59:59.999Z"), "b1"));
        
        next = cursor.next(null, null, records, TestRecord::getDt);
        
        assertThat(next.getOffset().orElse(null), is(1L));
        assertThat(next.getDateTime().orElse(null), is(DateTime.parse("2018-07-09T22:59:59.999Z")));
        
        // what if a total count was provided?
        next = cursor.next(null, 1201L, records, TestRecord::getDt);
        
        assertThat(next.getTotalCount().orElse(null), is(1201L));
        assertThat(next.getOffset().orElse(null), is(1L));
        assertThat(next.getDateTime().orElse(null), is(DateTime.parse("2018-07-09T22:59:59.999Z")));
        assertThat(next.toString(), is("dt-1531177199999,o-1,tc-1201"));
    }
    
    @Test
    public void nextEmptyResult() {
        DateTimeOffsetCursor cursor = DateTimeOffsetCursor.empty();
        
        DateTimeOffsetCursor next;
        
        next = cursor.next(2L, 0L, new ArrayList<>(), TestRecord::getDt);
        
        assertThat(next, is(nullValue()));
        
        next = cursor.next(2L, 0L, new ArrayList<>(), TestRecord::getDt, 1000L);
        
        assertThat(next, is(nullValue()));
        
        
        next = cursor.next(2L, 0L, null, TestRecord::getDt);
        
        assertThat(next, is(nullValue()));
        
        next = cursor.next(2L, 0L, null, TestRecord::getDt, 1000L);
        
        assertThat(next, is(nullValue()));
    }
    
    @Test
    public void nextWithManySimilarDateTimes() {
        DateTimeOffsetCursor cursor = DateTimeOffsetCursor.empty();
        cursor.setTotalCount(5L);
        DateTimeOffsetCursor next = null;
        DateTimeOffsetCursor previous = null;
        
        List<TestRecord> records = new ArrayList<>();
        
        records.clear();
        records.add(new TestRecord(DateTime.parse("2018-07-09T23:59:59.999Z"), "a1"));
        records.add(new TestRecord(DateTime.parse("2018-07-09T23:59:59.999Z"), "a2"));

        next = cursor.next(2L, 5L, records, TestRecord::getDt);
        
        assertThat(previous, is(nullValue()));
        assertThat(next.getTotalCount().orElse(null), is(5L));
        assertThat(next.getOffset().orElse(null), is(2L));
        assertThat(next.getDateTime().orElse(null), is(DateTime.parse("2018-07-09T23:59:59.999Z")));
        assertThat(next.toString(), is("dt-1531180799999,o-2,tc-5"));
        
        records.clear();
        records.add(new TestRecord(DateTime.parse("2018-07-09T23:59:59.999Z"), "a3"));
        records.add(new TestRecord(DateTime.parse("2018-07-09T23:59:59.999Z"), "a4"));

        next = next.next(2L, null, records, TestRecord::getDt);
        
        assertThat(next.getTotalCount().orElse(null), is(5L));
        assertThat(next.getOffset().orElse(null), is(4L));
        assertThat(next.getDateTime().orElse(null), is(DateTime.parse("2018-07-09T23:59:59.999Z")));
        assertThat(next.toString(), is("dt-1531180799999,o-4,tc-5"));
        
        records.clear();
        records.add(new TestRecord(DateTime.parse("2018-07-09T23:59:59.999Z"), "a5"));
        
        next = next.next(2L, null, records, TestRecord::getDt);
        
        // at limit
        assertThat(next, is(nullValue()));
    }
    
}