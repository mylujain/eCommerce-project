package com.example.demo.controllers;

import com.example.demo.TestUtils;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.repositories.ItemRepository;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ItemControllerTest {
    private ItemController itemController;
    private ItemRepository itemRepository = mock(ItemRepository.class);


    @Before
    public void setUp(){
        itemController = new ItemController();
        TestUtils.injectObjects(itemController, "itemRepository", itemRepository);
        Item item1 = new Item();
        item1.setId(1L);
        item1.setName("Item 1");
        item1.setDescription("Description of Item 1");
        item1.setPrice(BigDecimal.valueOf(10.00));

        Item item2 = new Item();
        item2.setId(2L);
        item2.setName("Item 2");
        item2.setDescription("Description of Item 2");
        item2.setPrice(BigDecimal.valueOf(20.00));

        List<Item> items = new ArrayList<>();
        items.add(item1);
        items.add(item2);
        when(itemRepository.findAll()).thenReturn(items);
        when(itemRepository.findById(1L)).thenReturn(Optional.of(item1));
        when(itemRepository.findById(2L)).thenReturn(Optional.of(item2));
        when(itemRepository.findByName("item")).thenReturn(items);

    }
    @Test
    public void testGetItems() {

        ResponseEntity<List<Item>> responseEntity = itemController.getItems();
        List<Item> responseItems = responseEntity.getBody();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseItems);
        assertEquals(2, responseItems.size());

        ResponseEntity<Item> responseEntity0 = itemController.getItemById(1L);
        Item responseItem0 = responseEntity0.getBody();
        assertEquals(responseItem0, responseItems.get(0));


        ResponseEntity<Item> responseEntity1 = itemController.getItemById(2L);
        Item responseItem1 = responseEntity1.getBody();
        assertEquals(responseItem1,responseItems.get(1));

    }

    @Test
    public void testGetItemById() {

        ResponseEntity<Item> response = itemController.getItemById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(itemRepository.findById(1L), Optional.of(response.getBody()));
    }
    @Test
    public void testGetItemByName() {

        ResponseEntity<List<Item>> response = itemController.getItemsByName("item");
        List<Item> responseItems = response.getBody();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(responseItems);
        assertEquals(2, responseItems.size());
    }

}
