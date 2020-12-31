package com.fizzed.pagination;

public interface PaginateMethod<U, R> {

    R apply(U u) throws Exception;
    
}