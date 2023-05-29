package com.example.canteenmgmtsys.order.model;

import com.example.canteenmgmtsys.customer.model.Customer;
import com.example.canteenmgmtsys.foodItem.model.FoodItem;
import com.example.canteenmgmtsys.order_foodItem.model.OrderedFoodItem;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;
    private double totalPrice;

    @CreationTimestamp
    private LocalDateTime orderDate;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderedFoodItem> orderFoodItems;

    @ManyToOne()
    @JoinColumn(name = "customer_id")
    private Customer customer;
}
