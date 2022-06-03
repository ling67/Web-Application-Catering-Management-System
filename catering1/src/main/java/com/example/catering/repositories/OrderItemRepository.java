package com.example.catering.repositories;
import java.util.Set;

import com.example.catering.models.OrderItem;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface OrderItemRepository extends CrudRepository<OrderItem, Integer> {

    @Query(value = "SELECT * FROM order_item WHERE order_id = :orderId", nativeQuery = true)
    Set<OrderItem> findByOrderId(Integer orderId);

}

