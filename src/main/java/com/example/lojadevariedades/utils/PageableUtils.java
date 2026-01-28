package com.example.lojadevariedades.utils;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class PageableUtils {
    private static final int DEFAULT_PAGE = 0;
    private static final int DEFAULT_SIZE = 20;
    private static final int MAX_SIZE = 100;

    public static Pageable sanitize(Pageable pageable) {
        int page = pageable == null ? DEFAULT_PAGE : Math.max(0, pageable.getPageNumber());
        int size = pageable == null ? DEFAULT_SIZE : pageable.getPageSize();
        size = Math.max(1, Math.min(size, MAX_SIZE));
        Sort sort = pageable == null ? Sort.unsorted() : pageable.getSort();
        return PageRequest.of(page, size, sort);
    }

    public static Pageable of(Integer page, Integer size, Sort sort) {
        int p = page == null ? DEFAULT_PAGE : Math.max(0, page);
        int s = size == null ? DEFAULT_SIZE : Math.max(1, Math.min(size, MAX_SIZE));
        Sort so = sort == null ? Sort.unsorted() : sort;
        return PageRequest.of(p, s, so);
    }
}
