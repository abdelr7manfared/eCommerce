package com.eCommerce.eCommerce.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Entity
@RequiredArgsConstructor
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private User customer;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private Status status = Status.PENDING;

    @Column(name = "created_at",insertable = false,updatable = false)
    private Date createdAt;

    @Column(name = "total_price")
    private BigDecimal totalPrice = BigDecimal.ZERO;

    @OneToMany(mappedBy = "order",cascade = CascadeType.ALL,orphanRemoval = true)
    private Set<OrderItem> orderItem = new HashSet<>();


    public static Order fromCart(Cart cart,User Customer) {
        var order = new Order();
        order.setStatus(Status.PENDING);
        order.setTotalPrice(cart.getTotalPrice());
        order.setCustomer(Customer);

        cart.getCartItems().forEach(item -> {
            var orderItems = new OrderItem(order,item.getProduct(),item.getQuantity());
            order.getOrderItem().add(orderItems);
        });
        return order;
    }

    public boolean isPlacedBy(User customer){
        return this.customer.equals(customer);
    }
}
