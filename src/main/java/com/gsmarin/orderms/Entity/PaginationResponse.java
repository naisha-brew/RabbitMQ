package com.gsmarin.orderms.Entity;

import org.springframework.data.domain.Page;

public record PaginationResponse(Integer page,
                                 Integer size,
                                 Integer totalElement,
                                 Integer totalPage) {

    public static PaginationResponse fromPage(Page page) {
        return new PaginationResponse(page.getNumber(),
                page.getSize(),
                page.getNumberOfElements(),
                page.getTotalPages());
    }
}
