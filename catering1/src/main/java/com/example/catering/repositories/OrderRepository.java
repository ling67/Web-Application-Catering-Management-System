package com.example.catering.repositories;
import java.util.Set;

import com.example.catering.models.Order;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface OrderRepository extends CrudRepository<Order, Integer> {
    /*
     * @Query("SELECT DISTINCT o FROM Order o WHERE s.order_id = (:order_id)")
     * public Order retrieveByOrderId(@Param("order_id") String orderId);
     * 
     * @Query("DELETE FROM Order o WHERE s.order_id = (:order_id)")
     * public void deleteByOrderId(@Param("order_id") String orderId);
     */

    @Query(value = "SELECT * FROM order_detail WHERE order_status = :status", nativeQuery = true)
    Set<Order> findByOrderStatus(Integer status);

    @Query(value = "SELECT * FROM order_detail WHERE customer_id = :customerId", nativeQuery = true)
    Set<Order> findByCustomerId(Integer customerId);

}
