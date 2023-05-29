package com.example.canteenmgmtsys.order.controller;

import com.example.canteenmgmtsys.order.dto.OrderByCustomerIdResponseDto;
import com.example.canteenmgmtsys.order.dto.OrderRequestDto;
import com.example.canteenmgmtsys.order.dto.OrderResponseDto;
import com.example.canteenmgmtsys.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/order")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/place_new_order")
    public String placeNewOrder(@RequestBody OrderRequestDto orderRequestDto){
        orderService.placeNewOrder(orderRequestDto);
        return"Order placed successfully";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/get_orderInfo_by_id/{orderId}")
    public OrderResponseDto getOrderInfoById(@PathVariable("orderId") Long orderId){
        return orderService.getOrderInfoById(orderId);
    }

    @GetMapping("/get_orderInfo_by_customer_id/{customerId}")
    public List<OrderByCustomerIdResponseDto> getOrderInfoByCustomerId(@PathVariable("customerId") String customerId){
        return orderService.getOrderInfoByCustomerId(customerId);
    }
}
