package com.example.canteenmgmtsys.order.service;


import com.example.canteenmgmtsys.order.dto.OrderByCustomerIdResponseDto;
import com.example.canteenmgmtsys.order.dto.OrderRequestDto;
import com.example.canteenmgmtsys.order.dto.OrderResponseDto;

import java.util.List;

public interface OrderService {
    void placeNewOrder(OrderRequestDto orderRequestDto);

    OrderResponseDto getOrderInfoById(Long orderId);

    List<OrderByCustomerIdResponseDto> getOrderInfoByCustomerId(String customerId);
}
