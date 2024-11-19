package com.gsmarin.orderms.Entity;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public record ApiResponse<T>(Map<String, Object> summary, List<T> list, PaginationResponse pageResponse) {
}
