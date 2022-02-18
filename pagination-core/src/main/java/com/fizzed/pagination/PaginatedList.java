package com.fizzed.pagination;

import static com.fizzed.crux.util.MoreObjects.size;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class PaginatedList<T> implements Iterable<T> {
     
    private Pagination pagination;
    private List<T> values;
    private PaginateMethod<Pagination,PaginatedList<T>> nextMethod;
    private PaginateMethod<Pagination,PaginatedList<T>> previousMethod;

    public Pagination getPagination() {
        return pagination;
    }

    public PaginatedList<T> setPagination(Pagination pagination) {
        this.pagination = pagination;
        return this;
    }

    public List<T> getValues() {
        return values;
    }

    public PaginatedList<T> setValues(List<T> values) {
        this.values = values;
        return this;
    }

    
    public PaginateMethod<Pagination, PaginatedList<T>> nextMethod() {
        return this.nextMethod;
    }

    public PaginatedList<T> nextMethod(PaginateMethod<Pagination, PaginatedList<T>> nextMethod) {
        this.nextMethod = nextMethod;
        return this;
    }
    
    public PaginateMethod<Pagination, PaginatedList<T>> previousMethod() {
        return this.previousMethod;
    }

    public PaginatedList<T> previousMethod(PaginateMethod<Pagination, PaginatedList<T>> previousMethod) {
        this.previousMethod = previousMethod;
        return this;
    }

    @Override
    public Iterator<T> iterator() {
        return new PaginatedListIterator<>(this);
    }
    
    public Stream<T> stream() {
        final Iterator<T> iterator = this.iterator();
        return StreamSupport.stream(
            Spliterators.spliteratorUnknownSize(iterator, Spliterator.ORDERED),
            false);
    }
    
    public boolean nextSupported() {
        return this.nextMethod != null;
    }
    
    public PaginatedList<T> nextPage() throws Exception {
        if (this.pagination == null || this.pagination.getNext() == null) {
            return null;
        }
        if (this.nextMethod == null) {
            throw new UnsupportedOperationException("Next page not supported");
        }
        return this.nextMethod.apply(this.pagination);
    }
    
    public boolean previousSupported() {
        return this.previousMethod != null;
    }
    
    public PaginatedList<T> previousPage() throws Exception {
        if (this.pagination == null || this.pagination.getPrevious() == null) {
            return null;
        }
        if (this.previousMethod == null) {
            throw new UnsupportedOperationException("Previous page not supported");
        }
        return this.previousMethod.apply(this.pagination);
    }

}