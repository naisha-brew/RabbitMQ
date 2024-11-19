package com.gsmarin.orderms.controller;

import com.gsmarin.orderms.Entity.ApiResponse;
import com.gsmarin.orderms.Entity.OrderResponse;
import com.gsmarin.orderms.Entity.PaginationResponse;
import com.gsmarin.orderms.service.OrderService;
import lombok.AllArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping
@AllArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping("/customers/{customerId}/orders")
    public ResponseEntity<ApiResponse<OrderResponse>> listOrders(
            @RequestParam(name = "page",defaultValue = "0") Integer page,
            @RequestParam(name = "pageSze",defaultValue = "10") Integer pageSize,
            @PathVariable("customerId") Long customerId){
        var pageResponse = orderService.findAllByCustomerId(customerId, PageRequest.of(page, pageSize));
        var totalOrders = orderService.findTotalPriceByCustomerId(customerId);
        return ResponseEntity.ok(new ApiResponse<>(
                Map.of("totalOrders",totalOrders),
                pageResponse.getContent(),
                PaginationResponse.fromPage(pageResponse)
        ));
    }
}
