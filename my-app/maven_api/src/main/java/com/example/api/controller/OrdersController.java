package com.example.api.controller;

import com.example.api.model.Order;
import com.example.api.service.OrdersService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@AllArgsConstructor
@RequestMapping("api/orders")
@CrossOrigin(origins = "http://localhost:3000", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class OrdersController {

    private OrdersService ordersService;

    @PostMapping("/user/{userId}")
    public ResponseEntity<Order> createOrder(@PathVariable int userId) {
        long userIdLong = userId;
        Order order = ordersService.createOrder(userIdLong);
        return new ResponseEntity<>(order, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable Long id) {
        Order order = ordersService.getOrderById(id);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    @GetMapping("/user/{userId}")
    public List<Order> getOrderByUserId(@PathVariable Long userId) {
        return ordersService.getOrdersByUserId(userId);
    }

    @GetMapping
    public ResponseEntity<Order>getAllOrders() {
        Order order = ordersService.GetOrder();
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable long id) {
        ordersService.deleteOrder(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
