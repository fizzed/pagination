package com.fizzed.pagination;

import org.junit.Test;

import java.io.Serializable;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

public class PaginatedListsTest {

    static public class TestQuery implements Serializable, CursorLimit {

        private String a;
        private String cursor;
        private Integer limit;

        public String getA() {
            return a;
        }

        public TestQuery setA(String a) {
            this.a = a;
            return this;
        }

        @Override
        public String getCursor() {
            return cursor;
        }

        public TestQuery setCursor(String cursor) {
            this.cursor = cursor;
            return this;
        }

        @Override
        public Integer getLimit() {
            return limit;
        }

        public TestQuery setLimit(Integer limit) {
            this.limit = limit;
            return this;
        }

        @Override
        public void updateCursor(String cursor) {
            this.cursor = cursor;
        }

    }

    @Test
    public void cloneForNext() {
        TestQuery newQuery;

        newQuery = PaginatedLists.cloneForNext(null, null, TestQuery.class);

        assertThat(newQuery, is(not(nullValue())));
        assertThat(newQuery.getCursor(), is(nullValue()));
        assertThat(newQuery.getLimit(), is(nullValue()));

        newQuery = PaginatedLists.cloneForNext(new Pagination()
            .setNext("test"), null, TestQuery.class);

        assertThat(newQuery, is(not(nullValue())));
        assertThat(newQuery.getCursor(), is("test"));
        assertThat(newQuery.getLimit(), is(nullValue()));

        newQuery = PaginatedLists.cloneForNext(new Pagination()
            .setNext("test"), null, TestQuery.class);

        assertThat(newQuery, is(not(nullValue())));
        assertThat(newQuery.getCursor(), is("test"));
        assertThat(newQuery.getLimit(), is(nullValue()));

        newQuery = PaginatedLists.cloneForNext(
            new Pagination()
                .setNext("test"),
            new TestQuery()
                .setA("b")
                .setCursor("z")
                .setLimit(10),
            TestQuery.class);

        assertThat(newQuery, is(not(nullValue())));
        assertThat(newQuery.getA(), is("b"));
        assertThat(newQuery.getCursor(), is("test"));
        assertThat(newQuery.getLimit(), is(10));
    }

}