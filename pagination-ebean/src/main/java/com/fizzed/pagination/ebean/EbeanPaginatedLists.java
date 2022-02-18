package com.fizzed.pagination.ebean;

import static com.fizzed.crux.util.Maybe.maybe;
import static com.fizzed.crux.util.MoreObjects.size;
import static java.util.Optional.ofNullable;

import com.fizzed.pagination.PaginatedList;
import com.fizzed.pagination.Pagination;
import com.fizzed.pagination.cursor.OffsetCursor;
import io.ebean.ExpressionList;
import io.ebean.PagedList;
import java.util.List;
import java.util.Objects;

public class EbeanPaginatedLists {

    static public <T> PaginatedList<T> findPaged(
            ExpressionList<T> ebeanQuery,
            OffsetCursor cursor,
            Integer limit,
            boolean includeTotalCount) {

        return findPaged(
            ebeanQuery,
            cursor,
            ofNullable(limit).map(v -> v.longValue()).orElse(null),
            includeTotalCount);
    }

    static public <T> PaginatedList<T> findPaged(
            ExpressionList<T> ebeanQuery,
            OffsetCursor cursor,
            Long limit,
            boolean includeTotalCount) {

        fillPaginatedQuery(ebeanQuery, cursor, limit);

        final PagedList<T> pagedValues = ebeanQuery.findPagedList();

        return toPaginatedList(pagedValues, cursor, includeTotalCount);
    }

    static public <T> void fillPaginatedQuery(
            ExpressionList<T> query,
            OffsetCursor cursor,
            Integer limit) {

        fillPaginatedQuery(
            query,
            cursor,
            maybe(limit).map(v -> v.longValue()).orNull());
    }

    static public <T> void fillPaginatedQuery(
            ExpressionList<T> query,
            OffsetCursor cursor,
            Long limit) {
        
        final Long offset = maybe(cursor)
            .flatMap(v -> v.getValue())
            .orNull();
        
        fillPaginatedQuery(query, offset, limit);
    }

    static public <T> void fillPaginatedQuery(
            ExpressionList<T> query,
            Integer offset,
            Integer limit) {

        fillPaginatedQuery(
            query,
            maybe(offset).map(v -> v.longValue()).orNull(),
            maybe(limit).map(v -> v.longValue()).orNull());
    }

    static public <T> void fillPaginatedQuery(
            ExpressionList<T> query,
            Long offset,
            Long limit) {
        
        if (offset != null && offset > 0L) {
            query.setFirstRow(offset.intValue());
        }
        
        if (limit != null && limit > 0L) {
            query.setMaxRows(limit.intValue());
        } else {
            // max rows MUST be set
            query.setMaxRows(50);
        }
    }
    
    static public <T> PaginatedList<T> toPaginatedList(
            PagedList<T> pagedList,
            long offset) {
        
        return toPaginatedList(pagedList, offset, false);
    }
 
    static public <T> PaginatedList<T> toPaginatedList(
            PagedList<T> pagedList,
            long offset,
            boolean includeTotalCount) {
        
        final OffsetCursor cursor = OffsetCursor.of(offset);
        
        return toPaginatedList(pagedList, cursor, includeTotalCount);
    }
    
    static public <T> PaginatedList<T> toPaginatedList(
            PagedList<T> pagedList,
            OffsetCursor cursor,
            boolean includeTotalCount) {
        
        final PaginatedList<T> paginatedList = new PaginatedList<>();
        
        fillPaginatedList(paginatedList, pagedList, cursor, includeTotalCount);
        
        return paginatedList;
    }
 
    static public <T> void fillPaginatedList(
            PaginatedList<T> paginatedList,
            PagedList<T> pagedList,
            OffsetCursor cursor,
            boolean includeTotalCount) {
        
        Objects.requireNonNull(paginatedList, "paginatedList was null");
        Objects.requireNonNull(pagedList, "pagedList was null");
        
        final OffsetCursor _cursor = maybe(cursor).orGet(() -> OffsetCursor.empty());
        
        final List<T> values = pagedList.getList();
        
        final Pagination pagination = new Pagination();
        pagination.setCount((long)size(values));
        pagination.setLimit((long)pagedList.getPageSize());
        
        if (includeTotalCount) {
            pagination.setTotalCount((long)pagedList.getTotalCount());
        }
        
        if (!_cursor.isEmpty()) {
            pagination.setCurrent(_cursor.toString());
        }
        
        if (pagedList.hasPrev()) {
            OffsetCursor prevCursor = _cursor.previous((long)pagedList.getPageSize());
            if (prevCursor != null) {
                pagination.setPrevious(prevCursor.toString());
            }
        }
        
        if (pagedList.hasNext()) {
            OffsetCursor nextCursor = _cursor.next((long)pagedList.getPageSize(), null);
            if (nextCursor != null) {
                pagination.setNext(nextCursor.toString());
            }
        }
        
        paginatedList.setValues(values);
        paginatedList.setPagination(pagination);
    }
    
}