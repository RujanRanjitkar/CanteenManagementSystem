package com.example.canteenmgmtsys.order.service;

import com.example.canteenmgmtsys.category.model.Category;
import com.example.canteenmgmtsys.category.repo.CategoryRepo;
import com.example.canteenmgmtsys.customException.exceptions.InsufficientResourceException;
import com.example.canteenmgmtsys.customException.exceptions.ResourceNotFoundException;
import com.example.canteenmgmtsys.order.dto.OrderByCustomerIdResponseDto;
import com.example.canteenmgmtsys.customer.model.Customer;
import com.example.canteenmgmtsys.customer.repo.CustomerRepo;
import com.example.canteenmgmtsys.foodItem.enums.FoodItemStatus;
import com.example.canteenmgmtsys.foodItem.model.FoodItem;
import com.example.canteenmgmtsys.foodItem.repo.FoodItemRepo;
import com.example.canteenmgmtsys.order.dto.OrderRequestDto;
import com.example.canteenmgmtsys.order.dto.OrderResponseDto;
import com.example.canteenmgmtsys.order.model.Order;
import com.example.canteenmgmtsys.order.repo.OrderRepo;
import com.example.canteenmgmtsys.order.util.Util;
import com.example.canteenmgmtsys.order_foodItem.dto.FoodItemOrderedRequestDto;
import com.example.canteenmgmtsys.order_foodItem.dto.FoodItemOrderedResponseDto;
import com.example.canteenmgmtsys.order_foodItem.model.OrderedFoodItem;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepo orderRepo;
    private final FoodItemRepo foodItemRepo;
    private final CategoryRepo categoryRepo;
    private final CustomerRepo customerRepo;
    private final Util util;
    Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Override
    public void placeNewOrder(OrderRequestDto orderRequestDto) {

        Order order = new Order();

        logger.info("Getting customer information according to customerId");
        Optional<Customer> customer = customerRepo.findById(orderRequestDto.getCustomerId());

        if (!customer.isPresent()) {

            logger.error("Customer does not exists");
            throw new ResourceNotFoundException("Customer with id " + orderRequestDto.getCustomerId() + " does not exists");
        }

        List<OrderedFoodItem> orderedFoodItemList = new ArrayList<>();

        logger.info("Getting foodItem list from orderRequestDto");
        List<FoodItemOrderedRequestDto> foodItemOrderedRequestDtoList = orderRequestDto.getFoodItems();

        double totalPrice = 0.0;

        for (FoodItemOrderedRequestDto foodItemOrderedRequestDto : foodItemOrderedRequestDtoList) {

            logger.info("Getting category by name from database table");
            Category category = categoryRepo.findByCategoryName(foodItemOrderedRequestDto.getCategoryName().toLowerCase());

            if (category == null) {

                logger.error("Category does not exists");
                throw new ResourceNotFoundException("Category " + foodItemOrderedRequestDto.getCategoryName() + " does not exists");
            }

            Long categoryId = category.getCategoryId();

            logger.info("Getting foodItem from food_item database table according to foodItem name");
            FoodItem foodItem = foodItemRepo.findByFoodItemNameAndCategoryId(foodItemOrderedRequestDto.getFoodItemName(), categoryId);

            if (foodItem == null) {

                logger.error("FoodItem does not exists");
                throw new ResourceNotFoundException(foodItemOrderedRequestDto.getFoodItemName() + " of category " + foodItemOrderedRequestDto.getCategoryName() + " does not exists");
            }

            if (foodItem.getStock() < foodItemOrderedRequestDto.getQuantity()) {

                logger.error("Currently the foodItem is out-of-stock");
                throw new InsufficientResourceException("Currently " + foodItemOrderedRequestDto.getFoodItemName() + " of category " + foodItemOrderedRequestDto.getCategoryName() + " is insufficient as per your request");
            }

            logger.info("Calling util method to calculate total price of each foodItem according to quantity");
            double itemTotalPrice = util.calculateTotalPriceOfOrderList(foodItem, foodItemOrderedRequestDto.getQuantity());
            totalPrice += itemTotalPrice;

            OrderedFoodItem orderFoodItem = new OrderedFoodItem();
            orderFoodItem.setOrder(order);
            orderFoodItem.setFoodItem(foodItem);
            orderFoodItem.setQuantity(foodItemOrderedRequestDto.getQuantity());
            orderFoodItem.setCategory(category);

            logger.info("Updating foodItem stock after deducting quantity ordered by customer");
            foodItem.setStock(foodItem.getStock() - foodItemOrderedRequestDto.getQuantity());

            if (foodItem.getStock() == 0) {

                logger.info("Setting status to out-of-stock if stock is 0");
                foodItem.setFoodItemStatus(FoodItemStatus.OutOfStock);
            }

            logger.info("Adding orderFoodItem in orderedFoodItemList");
            orderedFoodItemList.add(orderFoodItem);
        }

        order.setOrderFoodItems(orderedFoodItemList);
        order.setTotalPrice(totalPrice);
        order.setCustomer(customer.get());

        logger.info("Saving order into database table");
        orderRepo.save(order);
    }

    @Override
    public OrderResponseDto getOrderInfoById(Long orderId) {

        logger.info("Getting order information according to orderId");
        Optional<Order> order = orderRepo.findById(orderId);

        if (!order.isPresent()) {

            logger.error("Order does not exists");
            throw new ResourceNotFoundException("Order with id " + orderId + " does not exists");
        }

        OrderResponseDto orderResponseDto = new OrderResponseDto();

        logger.info("Setting value of order in orderResponseDto");
        orderResponseDto.setOrderDate(order.get().getOrderDate());
        orderResponseDto.setTotalPrice(order.get().getTotalPrice());
        orderResponseDto.setCustomerId(order.get().getCustomer().getCustomerId());

        logger.info("Getting foodItem list from order");
        List<OrderedFoodItem> orderedFoodItemList = order.get().getOrderFoodItems();

        List<FoodItemOrderedResponseDto> foodItemOrderedResponseDtoList = new ArrayList<>();

        for (OrderedFoodItem foodItem : orderedFoodItemList) {
            FoodItemOrderedResponseDto foodItemOrderedResponseDto = new FoodItemOrderedResponseDto();

            logger.info("Setting value of ordered foodItem in foodItemOrderedResponseDto");
            foodItemOrderedResponseDto.setFoodItemName(foodItem.getFoodItem().getFoodItemName());
            foodItemOrderedResponseDto.setQuantity(foodItem.getQuantity());
            foodItemOrderedResponseDto.setPricePerQuantity(foodItem.getFoodItem().getPrice());
            foodItemOrderedResponseDto.setCategoryName(foodItem.getCategory().getCategoryName());

            logger.info("Adding value of foodItemOrderedResponseDto in foodItemOrderedResponseDtoList");
            foodItemOrderedResponseDtoList.add(foodItemOrderedResponseDto);
        }

        logger.info("Setting foodItemOrderedResponseDtoList in orderResponseDto");
        orderResponseDto.setFoodItems(foodItemOrderedResponseDtoList);

        logger.info("Returning orderResponseDto");
        return orderResponseDto;
    }

    @Override
    public List<OrderByCustomerIdResponseDto> getOrderInfoByCustomerId(String customerId) {

        logger.info("Getting order information according to customerId");
        List<Order> orderList = orderRepo.findByCustomerCustomerId(customerId);

        if (orderList.isEmpty()) {

            logger.error("Order does not exists");
            throw new ResourceNotFoundException("Order with customer id " + customerId + " does not exists");
        }

        List<OrderByCustomerIdResponseDto> orderByCustomerIdResponseDtoList = new ArrayList<>();

        for (Order order : orderList) {
            OrderByCustomerIdResponseDto orderByCustomerIdResponseDto = new OrderByCustomerIdResponseDto();

            logger.info("Setting value of order in orderByCustomerIdResponseDto");
            orderByCustomerIdResponseDto.setOrderDate(order.getOrderDate());
            orderByCustomerIdResponseDto.setTotalPrice(order.getTotalPrice());
            orderByCustomerIdResponseDtoList.add(orderByCustomerIdResponseDto);

            logger.info("Getting foodItem list from order");
            List<OrderedFoodItem> orderedFoodItemList = order.getOrderFoodItems();

            List<FoodItemOrderedResponseDto> foodItemOrderedResponseDtoList = new ArrayList<>();

            for (OrderedFoodItem foodItem : orderedFoodItemList) {
                FoodItemOrderedResponseDto foodItemOrderedResponseDto = new FoodItemOrderedResponseDto();

                logger.info("Setting value of ordered foodItem in foodItemOrderedResponseDto");
                foodItemOrderedResponseDto.setFoodItemName(foodItem.getFoodItem().getFoodItemName());
                foodItemOrderedResponseDto.setQuantity(foodItem.getQuantity());
                foodItemOrderedResponseDto.setPricePerQuantity(foodItem.getFoodItem().getPrice());
                foodItemOrderedResponseDto.setCategoryName(foodItem.getCategory().getCategoryName());

                logger.info("Adding value of foodItemOrderedResponseDto in foodItemOrderedResponseDtoList");
                foodItemOrderedResponseDtoList.add(foodItemOrderedResponseDto);
            }

            logger.info("Setting foodItemOrderedResponseDtoList in orderByCustomerIdResponseDto");
            orderByCustomerIdResponseDto.setFoodItems(foodItemOrderedResponseDtoList);
        }

        logger.info("Returning orderByCustomerIdResponseDtoList");
        return orderByCustomerIdResponseDtoList;
    }
}
