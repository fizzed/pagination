package com.fizzed.pagination;

import com.fizzed.pagination.Pagination;
import com.fizzed.pagination.PaginatedList;
import java.util.Arrays;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.fail;
import org.junit.Test;

public class PaginatedListTest {

    @Test
    public void nextPageTest() throws Exception {
        PaginatedList<?> paginatedList = new PaginatedList<>();
        
        assertThat(paginatedList.nextPage(), is(nullValue()));
        
        paginatedList.setPagination(new Pagination()
            .setNext("1"));
        
        try {
            paginatedList.nextPage();
            fail();
        } catch (UnsupportedOperationException e) {
            // expected
        }
        
        paginatedList.nextMethod(p -> new PaginatedList<>());
        
        assertThat(paginatedList.nextPage(), is(not(nullValue())));
    }
    
    @Test
    public void previousPageTest() throws Exception {
        PaginatedList<?> paginatedList = new PaginatedList<>();
        
        assertThat(paginatedList.previousPage(), is(nullValue()));
        
        paginatedList.setPagination(new Pagination()
            .setPrevious("1"));
        
        try {
            paginatedList.previousPage();
            fail();
        } catch (UnsupportedOperationException e) {
            // expected
        }
        
        paginatedList.previousMethod(p -> new PaginatedList<>());
        
        assertThat(paginatedList.previousPage(), is(not(nullValue())));
    }
    
    @Test
    public void iterator() throws Exception {
        final PaginatedList<String> paginatedList2 = new PaginatedList<>();
        paginatedList2.setValues(Arrays.asList("b"));
        
        final PaginatedList<String> paginatedList1 = new PaginatedList<>();
        paginatedList1.setPagination(new Pagination()
            .setNext("1"));
        paginatedList1.nextMethod(p -> { return paginatedList2; });
        paginatedList1.setValues(Arrays.asList("a"));
        
        long count = paginatedList1.stream().count();
        
        assertThat(count, is(2L));
        
        int count2 = 0;
        for (String s : paginatedList1) {
            count2++;
        }
        
        assertThat(count2, is(2));
    }
    
    @Test(expected=UnsupportedOperationException.class)
    public void iteratorUnsupported() throws Exception {
        final PaginatedList<String> paginatedList2 = new PaginatedList<>();
        paginatedList2.setValues(Arrays.asList("b"));
        
        long count = paginatedList2.stream().count();
    }
    
}