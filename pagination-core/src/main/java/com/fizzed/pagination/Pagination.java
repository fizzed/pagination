package com.fizzed.pagination;

public class Pagination {
 
    private Long limit;
    private Long count;
    private Long totalCount;
    private String current;
    private String previous;
    private String next;

    public Long getLimit() {
        return limit;
    }

    public Pagination setLimit(Long limit) {
        this.limit = limit;
        return this;
    }
    
    public Long getCount() {
        return count;
    }

    public Pagination setCount(Long count) {
        this.count = count;
        return this;
    }

    public Long getTotalCount() {
        return totalCount;
    }

    public Pagination setTotalCount(Long totalCount) {
        this.totalCount = totalCount;
        return this;
    }

    public String getCurrent() {
        return current;
    }

    public Pagination setCurrent(String current) {
        this.current = current;
        return this;
    }

    public String getPrevious() {
        return previous;
    }

    public Pagination setPrevious(String previous) {
        this.previous = previous;
        return this;
    }

    public String getNext() {
        return next;
    }

    public Pagination setNext(String next) {
        this.next = next;
        return this;
    }

}