package com.example.demo.controllers;

import com.example.demo.TestUtils;
import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.ItemRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.ModifyCartRequest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)

public class CartControllerTest {
    @InjectMocks
    private CartController cartController;

    @Mock
    private UserRepository userRepository;

    @Mock
    private CartRepository cartRepository;

    @Mock
    private ItemRepository itemRepository;

    @Before
    public void setup(){
        cartController = new CartController();
        TestUtils.injectObjects(cartController, "userRepository", userRepository);
        TestUtils.injectObjects(cartController, "cartRepository", cartRepository);
        TestUtils.injectObjects(cartController, "itemRepository", itemRepository);
    }



    @Test
    public void testAddToCartWhenUserOrItemNotFoundThenReturnsNotFound() {

        //WhenUserNotFound
        ModifyCartRequest request = new ModifyCartRequest();
        request.setQuantity(1);
        request.setItemId(1);
        request.setUsername("not-found-user");
        ResponseEntity<Cart> response = cartController.addTocart(request);
        assertNotNull(response);
        HttpStatus status = response.getStatusCode();
        assertEquals(HttpStatus.NOT_FOUND, status);

        //WhenItemNotFound
        ModifyCartRequest request2 = new ModifyCartRequest();
        request2.setQuantity(3);
        request2.setItemId(100);
        request2.setUsername("lujain");

        ResponseEntity<Cart> response2 = cartController.addTocart(request2);
        assertNotNull(response2);
        HttpStatus status1 = response2.getStatusCode();
        assertEquals(HttpStatus.NOT_FOUND, status1);
    }



    @Test
    public void testRemoveFromCartWhenUserOrItemNotFoundYThenReturnsNotFound() {

        //WhenUserNotFound
        ModifyCartRequest request = new ModifyCartRequest();
        request.setQuantity(3);
        request.setItemId(1);
        request.setUsername("not-found-user");

        ResponseEntity<Cart> response = cartController.removeFromcart(request);
        assertNotNull(response);
        HttpStatus status = response.getStatusCode();
        assertEquals(HttpStatus.NOT_FOUND, status);

        //WhenItemNotFound
        ModifyCartRequest request2 = new ModifyCartRequest();
        request2.setQuantity(3);
        request2.setItemId(100);
        request2.setUsername("lujain");

        ResponseEntity<Cart> response2 = cartController.removeFromcart(request2);
        assertNotNull(response2);
        HttpStatus status1 = response2.getStatusCode();
        assertEquals(HttpStatus.NOT_FOUND, status1);
    }



}
