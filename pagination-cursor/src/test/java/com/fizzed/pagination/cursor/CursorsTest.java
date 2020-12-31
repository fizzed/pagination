package com.fizzed.pagination.cursor;

import com.fizzed.pagination.cursor.Cursors;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import org.junit.Test;

public class CursorsTest {
 
    @Test
    public void nextLongOffset() {
        assertThat(Cursors.nextLongOffset(0L, 100L, 101L), is(100L));
        assertThat(Cursors.nextLongOffset(0L, 100L, 100L), is(nullValue()));
        // end is not known so next offset calculated
        assertThat(Cursors.nextLongOffset(0L, 100L, null), is(100L));
        // with no limit we cannot calculate next offset
        assertThat(Cursors.nextLongOffset(0L, null, 101L), is(nullValue()));
    }
    
    @Test
    public void previousLongOffset() {
        assertThat(Cursors.previousLongOffset(0L, 100L), is(nullValue()));
        assertThat(Cursors.previousLongOffset(1L, 100L), is(0L));
        // end is not known so next offset calculated
        assertThat(Cursors.previousLongOffset(100L, 100L), is(0L));
        // with no limit we cannot calculate next offset
        assertThat(Cursors.previousLongOffset(0L, null), is(nullValue()));
    }
    
}