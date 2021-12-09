//package com.example.compound.entities;
//
//import org.junit.*;
//
//import java.beans.PropertyChangeEvent;
//import java.beans.PropertyChangeListener;
//import java.beans.PropertyVetoException;
//import java.beans.VetoableChangeListener;
//
//import static org.junit.Assert.*;
//
//public class ItemTest {
//    Item i;
//    VetoableChangeListener vetoableChangeListener;
//    PropertyChangeListener propertyChangeListener;
//
//    @Before
//    public void setUp() {
//        i = new Item("", "Carrot", 2.00, 1);
//    }
//
//    @Test
//    public void testGetIUID() {
//        assertEquals("", i.getIUID());
//    }
//
//    @Test
//    public void testGetCost() {
//        assertEquals(2.00, i.getCost(), 0.01);
//    }
//
//    @Test
//    public void testGetName() {
//        assertEquals("Carrot", i.getName());
//    }
//
//    @Test
//    public void getQuantity() {
//        assertEquals(1, i.getQuantity());
//    }
//
//    @Test
//    public void testAddObserverPropertyChangeListener() {
//        propertyChangeListener = evt -> {
//            throw new RuntimeException("");
//        };
//        i.addObserver(propertyChangeListener);
//        try {
//            i.setName("");
//        } catch (RuntimeException e) {
//            return;
//        }
//        fail();
//    }
//
//    @Test
//    public void testAddObserverVetoableChangeListener() {
//        vetoableChangeListener = evt -> {
//            throw new PropertyVetoException("", evt);
//        };
//        i.addObserver(vetoableChangeListener);
//        assertFalse(i.setQuantity(5));
//    }
//
//    @Test
//    public void testSetName() {
//        i.setName("Pickle");
//        assertEquals("Pickle", i.getName());
//    }
//
//    @Test
//    public void testSetCost() {
//        assertTrue(i.setCost(600.0));
//        assertEquals(600.0, i.getCost(), 0.01);
//    }
//
//    @Test
//    public void testSetQuantity() {
//        assertTrue(i.setQuantity(500));
//        assertEquals(500, i.getQuantity());
//    }
//}