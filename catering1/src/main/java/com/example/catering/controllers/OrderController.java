package com.example.catering.controllers;

import com.example.catering.repositories.OrderRepository;

import com.example.catering.dto.MessageDetails;
import com.example.catering.dto.OrderInfo;
import com.example.catering.dto.OrderItemInfo;
import com.example.catering.models.Customer;
import com.example.catering.models.Menu;
import com.example.catering.models.Order;
import com.example.catering.models.OrderItem;
import com.example.catering.repositories.CustomerRepository;
import com.example.catering.repositories.MenuRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/order")
public class OrderController {

    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final MenuRepository menuRepository;

    private static final int tax = 10;   //代表10%的税

    public OrderController(OrderRepository orderRepository, CustomerRepository customerRepository, 
            MenuRepository menuRepository) {
        this.orderRepository = orderRepository;
        this.customerRepository = customerRepository;
        this.menuRepository = menuRepository;
    }

    @GetMapping
    public Iterable<Order> getOrders() {
        Iterable<Order> orders = this.orderRepository.findAll();
        return orders;
    }

    @PostMapping("/status")
    public Iterable<Order> getOrdersByStatus(@RequestParam Integer status) {
        Iterable<Order> orders = this.orderRepository.findByOrderStatus(status);
        return orders;
    }

    @GetMapping("/{customerId}")
    public Iterable<Order> getOrdersByCustomerId(@PathVariable Integer customerId) {
        Iterable<Order> orders = this.orderRepository.findByCustomerId(customerId);
        Customer customer = this.customerRepository.findById(customerId).get();
        for(Order order:orders){
            order.setCustomer(customer);
        }
        return orders;
    }

    // bookId + customerId exist and then generate order
    @PostMapping
    public ResponseEntity<MessageDetails> createOrder(@RequestBody OrderInfo orderInfo) {

        Order order = new Order();

        // check customer
        Customer customer = this.customerRepository.findById(orderInfo.getCustomerId()).get();
        if (customer == null) {
            MessageDetails failMsg = new MessageDetails(
                    "Customer could not be found. orderId = " + orderInfo.getCustomerId());
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(failMsg);
        }
        order.setCustomer(customer);

        List<OrderItemInfo> orderItemInfos = orderInfo.getOrderItemInfos();

        Set<OrderItem> orderItems = new HashSet<>();
        double subtotal = 0.0;

        // check menu
        for (OrderItemInfo o : orderItemInfos) {
            Menu menu = this.menuRepository.findById(o.getMenuId()).get();
            if (menu == null) {
                MessageDetails failMsg = new MessageDetails(
                        "Menu could not be found. bookId = " + o.getMenuId());
                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(failMsg);
            }
            OrderItem orderItem = new OrderItem();
            orderItem.setMenu(menu);
            orderItem.setItemQuantity(o.getItemQuantity());

            orderItems.add(orderItem);
            subtotal = subtotal + menu.getPrice().doubleValue() * o.getItemQuantity();
        }
        order.setOrderItems(orderItems);

        order.setStatus(1);
        order.setSubtotal(new BigDecimal(subtotal));
        order.setTax(new BigDecimal(subtotal * tax / 100.0));
        order.setTotal(new BigDecimal(subtotal).add(new BigDecimal(subtotal * tax / 100.0)));
        order.setCreated(LocalDateTime.now());
        this.orderRepository.save(order);
        MessageDetails msg = new MessageDetails("The new order was inserted successfully.", true);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(msg);
    }

    // delete order table and order_item table, others do not need
    @DeleteMapping("/{orderId}")
    public ResponseEntity<MessageDetails> removeOrder(@PathVariable Integer orderId) {
        Order order = this.orderRepository.findById(orderId).get();
        if (order != null) {
            this.orderRepository.deleteById(orderId);
            MessageDetails sucMsg = new MessageDetails("The order was removed successfully.", true);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(sucMsg);
        } else {
            MessageDetails failMsg = new MessageDetails("Could not be found. orderId = " + orderId);
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(failMsg);
        }
    }
}
