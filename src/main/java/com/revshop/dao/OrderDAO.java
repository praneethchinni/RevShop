package com.revshop.dao;

import com.revshop.model.Order;
import com.revshop.model.OrderItem;
import com.revshop.util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderDAO {

    // Method to insert an order and return the generated order ID
    public int insertOrder(Order order) {
        String sql = "INSERT INTO orders (user_id, order_date, total_amount, shipping_address, status) VALUES (?, ?, ?, ?, ?)";
        int orderId = 0;

        try (Connection connection = DBUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            statement.setInt(1, order.getUserId());
            statement.setTimestamp(2, new Timestamp(order.getOrderDate().getTime()));
            statement.setBigDecimal(3, order.getTotalAmount());
            statement.setString(4, order.getShippingAddress());
            statement.setString(5, order.getStatus());

            int affectedRows = statement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating order failed, no rows affected.");
            }

            // Retrieve the generated order ID
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    orderId = generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Creating order failed, no ID obtained.");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return orderId;
    }
    
    
    // Method to insert order items
    public void insertOrderItem(OrderItem orderItem) {
        String sql = "INSERT INTO order_items (order_id, product_id, quantity, price) VALUES (?, ?, ?, ?)";

        try (Connection connection = DBUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, orderItem.getOrderId());
            statement.setInt(2, orderItem.getProductId());
            statement.setInt(3, orderItem.getQuantity());
            statement.setBigDecimal(4, orderItem.getPrice());

            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to get orders by user ID
    public List<Order> getOrdersByUserId(int userId) {
        List<Order> orders = new ArrayList<>();
        
        String query = "SELECT o.*, oi.order_item_id, oi.product_id, oi.quantity, oi.price, p.name FROM orders o LEFT JOIN order_items oi ON o.order_id = oi.order_id LEFT JOIN products p ON oi.product_id = p.id WHERE o.user_id = ?;";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            Map<Integer, Order> orderMap = new HashMap<>(); // Use a map to track orders by order ID

            while (rs.next()) {
                int orderId = rs.getInt("order_id");
                Order order = orderMap.get(orderId);

                // If this is a new order, create and add it to the list
                if (order == null) {
                    order = new Order();
                    order.setOrderId(orderId);
                    order.setUserId(rs.getInt("user_id"));
                    order.setOrderDate(rs.getDate("order_date"));
                    order.setTotalAmount(rs.getBigDecimal("total_amount"));
                    order.setShippingAddress(rs.getString("shipping_address"));
                    order.setStatus(rs.getString("status"));

                    // Add this new order to the map
                    orderMap.put(orderId, order);
                    orders.add(order);
                }

                // Now create the OrderItem and add it to the order
                OrderItem orderItem = new OrderItem();
                orderItem.setOrderItemId(rs.getInt("order_item_id"));
                orderItem.setOrderId(orderId);
                orderItem.setProductId(rs.getInt("product_id"));
                orderItem.setQuantity(rs.getInt("quantity"));
                orderItem.setPrice(rs.getBigDecimal("price"));
                orderItem.setProductName(rs.getString("name"));

                // Add OrderItem to the Order's list of items
                order.getOrderItems().add(orderItem);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return orders;
    }



    // Method to get order items by order ID
    public List<OrderItem> getOrderItemsByOrderId(int orderId) {
        List<OrderItem> orderItems = new ArrayList<>();
        String sql = "SELECT oi.*, p.name as product_name " +
                     "FROM order_items oi " +
                     "INNER JOIN products p ON oi.product_id = p.id " +
                     "WHERE oi.order_id = ?";

        try (Connection connection = DBUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, orderId);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                OrderItem item = new OrderItem();
                item.setOrderItemId(rs.getInt("order_item_id"));  // Use the correct column name here
                item.setOrderId(rs.getInt("order_id"));
                item.setProductId(rs.getInt("product_id"));
                item.setQuantity(rs.getInt("quantity"));
                item.setPrice(rs.getBigDecimal("price"));
                item.setProductName(rs.getString("product_name"));

                orderItems.add(item);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return orderItems;
    }

}