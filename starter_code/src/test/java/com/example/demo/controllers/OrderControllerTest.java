package com.example.demo.controllers;


import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.UserOrder;
import com.example.demo.model.persistence.repositories.OrderRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class OrderControllerTest {
    @InjectMocks
    private OrderController orderController;

    @Mock
    private UserRepository userRepository;

    @Mock
    private OrderRepository orderRepository;
    User user = new User();
    Item item = new Item();
    Cart cart = new Cart();
    @Before
    public void setUp(){
        //set user
        user.setId(0);
        user.setUsername("lujain");
        user.setPassword("12345678");
        //set item
        item.setId(1L);
        item.setName("Item " + item.getId());
        item.setPrice(BigDecimal.valueOf(1.99));
        item.setDescription("Description of item "+ item.getId());
        List<Item> items = Collections.singletonList(item);
        //set cart
        cart.setId(1L);
        cart.setUser(user);
        cart.setItems(items);
        BigDecimal total = BigDecimal.valueOf(1.99);
        cart.setTotal(total);

        user.setCart(cart);

        when(userRepository.findByUsername("lujain")).thenReturn(user);
        when(userRepository.findByUsername("notFound")).thenReturn(null);
    }

    @Test
    public void testSubmitOrderHappyPath() {
        ResponseEntity<UserOrder> response = orderController.submit("lujain");
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        UserOrder order = response.getBody();
        assertNotNull(order);
        assertEquals(1, order.getItems().size());
    }
    @Test
    public void testSubmitOrderUnHappyPath() {

        ResponseEntity<UserOrder> response = orderController.submit("notFound");
        assertNotNull(response);
        assertEquals(404, response.getStatusCodeValue());
    }
    @Test
    public void verifySubmitInvalid(){

        ResponseEntity<UserOrder> response = orderController.submit("invalid username");
        assertNotNull(response);
        assertEquals(404, response.getStatusCodeValue());

        assertNull( response.getBody());

        verify(userRepository, times(1)).findByUsername("invalid username");
    }

    @Test
    public void testGetOrdersForUserHappyPath() {
        ResponseEntity<List<UserOrder>> response = orderController.getOrdersForUser("lujain");
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        List<UserOrder> orders = response.getBody();
        assert orders != null;
        assertNotNull(orders);
    }
    @Test
    public void testGetOrdersForUserUnHappyPath() {
        ResponseEntity<List<UserOrder>> response = orderController.getOrdersForUser("notFound");
        assertNotNull(response);
        assertEquals(404, response.getStatusCodeValue());
    }
}