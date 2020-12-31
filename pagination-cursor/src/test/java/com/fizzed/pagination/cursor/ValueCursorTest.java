package com.fizzed.pagination.cursor;

import com.fizzed.pagination.cursor.LongCursor;
import com.fizzed.pagination.cursor.ValueCursor;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import org.junit.Test;

public class ValueCursorTest {
 
    @Test
    public void parse() {
        ValueCursor<Long> cursor = LongCursor.parse("10");
        
        assertThat(cursor.getValue().get(), is(10L));
        assertThat(cursor.toString(), is("10"));
        
        cursor = LongCursor.parse("9871729100128981");
        assertThat(cursor.getValue().get(), is(9871729100128981L));
        assertThat(cursor.toString(), is("9871729100128981"));
    }
    
}