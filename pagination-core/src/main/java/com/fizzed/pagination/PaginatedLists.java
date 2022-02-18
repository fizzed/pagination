package com.fizzed.pagination;

import java.util.Collections;
import java.util.List;

import static com.fizzed.crux.util.MoreObjects.size;
import static java.util.Optional.ofNullable;

public class PaginatedLists {

    static public <T> PaginatedList<T> ofList(List<T> values) {
        PaginatedList<T> pl = new PaginatedList<>();
        Pagination p = new Pagination();
        p.setCount((long)size(values));
        pl.setPagination(p);
        pl.setValues(values);
        return pl;
    }

    static public <T> PaginatedList<T> empty() {
        PaginatedList<T> pl = ofList(Collections.emptyList());
        pl.nextMethod(p -> null);
        pl.previousMethod(p -> null);
        return pl;
    }

    /**
     * Gets the total count of paginated values or -1 if the value is not set.
     * @param paginatedList
     * @return
     */
    static public long limit(PaginatedList<?> paginatedList) {
        return ofNullable(paginatedList)
            .map(v -> v.getPagination())
            .map(v -> v.getLimit())
            .orElse(-1L);
    }

    /**
     * Gets the total count of paginated values or -1 if the value is not set.
     * @param paginatedList
     * @return
     */
    static public long totalCount(PaginatedList<?> paginatedList) {
        return ofNullable(paginatedList)
            .map(v -> v.getPagination())
            .map(v -> v.getTotalCount())
            .orElse(-1L);
    }

    static public <T> List<T> values(PaginatedList<T> paginatedList) {
        return ofNullable(paginatedList)
            .map(v -> v.getValues())
            .orElse(null);
    }

    static public boolean hasNext(PaginatedList<?> paginatedList) {
        return paginatedList != null
            && hasNext(paginatedList.getPagination());
    }

    static public boolean hasNext(Pagination pagination) {
        return ofNullable(pagination)
            .map(v -> v.getNext())
            .map(v -> v != null && v.length() > 0)
            .orElse(false);
    }

    static public boolean isEmpty(PaginatedList<?> paginatedList) {
        return paginatedList == null
            || paginatedList.getValues() == null
            || paginatedList.getValues().isEmpty();
    }

}