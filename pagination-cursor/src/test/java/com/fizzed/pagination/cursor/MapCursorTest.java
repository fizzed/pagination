package com.fizzed.pagination.cursor;

import com.fizzed.pagination.cursor.StringLongMapCursor;
import com.fizzed.pagination.cursor.MapCursor;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasEntry;
import org.junit.Test;

public class MapCursorTest {
 
    @Test
    public void parse() {
        MapCursor<String,Long> cursor = StringLongMapCursor.parse("v-10,b-1");
        
        assertThat(cursor.getValues(), hasEntry("v", 10L));
        assertThat(cursor.getValues(), hasEntry("b", 1L));
        
        assertThat(cursor.toString(), is("v-10,b-1"));
        cursor.put("i", 9L);
        assertThat(cursor.toString(), is("v-10,b-1,i-9"));
    }
    
}