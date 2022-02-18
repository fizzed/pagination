package com.fizzed.pagination.cursor;

import com.fizzed.pagination.CursorLimit;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class OffsetCursorTest {

    @Test
    public void parse() {
        OffsetCursor cursor;

        cursor = OffsetCursor.parse((String)null);

        assertThat(cursor, is(not(nullValue())));
        assertThat(cursor.isEmpty(), is(true));
        assertThat(cursor.getValue().orNull(), is(nullValue()));

        cursor = OffsetCursor.parse("");

        assertThat(cursor, is(not(nullValue())));
        assertThat(cursor.isEmpty(), is(true));
        assertThat(cursor.getValue().orNull(), is(nullValue()));

        cursor = OffsetCursor.parse("  ");

        assertThat(cursor, is(not(nullValue())));
        assertThat(cursor.isEmpty(), is(true));
        assertThat(cursor.getValue().orNull(), is(nullValue()));

        cursor = OffsetCursor.parse("0");

        assertThat(cursor, is(not(nullValue())));
        assertThat(cursor.isEmpty(), is(true));     // zero or less than zero is empty
        assertThat(cursor.getValue().orNull(), is(0L));

        cursor = OffsetCursor.parse("1");

        assertThat(cursor, is(not(nullValue())));
        assertThat(cursor.isEmpty(), is(false));     // zero or less than zero is empty
        assertThat(cursor.getValue().orNull(), is(1L));

        cursor = OffsetCursor.parse((CursorLimit)null);

        assertThat(cursor, is(not(nullValue())));
        assertThat(cursor.isEmpty(), is(true));     // zero or less than zero is empty
        assertThat(cursor.getValue().orNull(), is(nullValue()));

        cursor = OffsetCursor.parse(new CursorLimit() {
            @Override
            public String getCursor() {
                return null;
            }
            @Override
            public Integer getLimit() {
                return null;
            }
            @Override
            public void updateCursor(String cursor) {
            }
        });

        assertThat(cursor, is(not(nullValue())));
        assertThat(cursor.isEmpty(), is(true));     // zero or less than zero is empty
        assertThat(cursor.getValue().orNull(), is(nullValue()));

        cursor = OffsetCursor.parse(new CursorLimit() {
            @Override
            public String getCursor() {
                return "1";
            }
            @Override
            public Integer getLimit() {
                return null;
            }
            @Override
            public void updateCursor(String cursor) {
            }
        });

        assertThat(cursor, is(not(nullValue())));
        assertThat(cursor.isEmpty(), is(false));     // zero or less than zero is empty
        assertThat(cursor.getValue().orNull(), is(1L));
    }
}