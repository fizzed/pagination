package com.fizzed.pagination;

public interface CursorLimit {

    String getCursor();

    void updateCursor(String cursor);

    Integer getLimit();

}