package com.fizzed.pagination;

import java.util.Iterator;

public class PaginatedListIterator<T> implements Iterator<T> {

    private PaginatedList<T> paginatedList;
    private int index;
    private T next;

    public PaginatedListIterator(PaginatedList<T> paginatedList) {
        if (paginatedList != null && !paginatedList.nextSupported()) {
            throw new UnsupportedOperationException("Next page method not implemented on this paginated list");
        }
        this.paginatedList = paginatedList;
        this.index = 0;
        this.next = this.fetchNext();
    }

    private T fetchNext() {
        // empty?
        if (PaginatedList.isEmpty(paginatedList)) {
            return null;
        }
        
        // try to move onto next page?
        if (this.index >= this.paginatedList.getValues().size()) {
            try {
                paginatedList = paginatedList.nextPage();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            this.index = 0;
        }
        
        // empty?
        if (PaginatedList.isEmpty(paginatedList)) {
            return null;
        }
        
        T value = this.paginatedList.getValues().get(this.index);
        
        this.index++;
        
        return value;
    }
    
    @Override
    public boolean hasNext() {
        return this.next != null;
    }

    @Override
    public T next() {
        T item = this.next;
        this.next = this.fetchNext();
        return item;
    }
    
}
