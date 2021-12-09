//package com.example.compound.entities;
//
//import org.junit.*;
//
//import static org.junit.Assert.*;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class GroupTest {
//    // emptyGroup and simpleGroup shouldn't be mutated.
//    Group emptyGroup;
//    Group simpleGroup;
//
//    @Before
//    public void setUp() {
//        emptyGroup = new Group("E", new ArrayList<>(), new ArrayList<>(), "An empty group");
//        simpleGroup = new Group("S", List.of("alice@example.com", "bob@example.com"), new ArrayList<>(), "A simple group");
//    }
//
//    @Test
//    public void testGetGUID() {
//        assertEquals(emptyGroup.getGUID(), "");
//        assertEquals(simpleGroup.getGUID(), "");
//    }
//
////    @Test
////    public void testGetGroupName() {
////        assertEquals(emptyGroup.getGroupName(), "E");
////        assertEquals(simpleGroup.getGroupName(), "S");
////    }
//
//    @Test
//    public void testGetGroupMembers() {
//        assertTrue(emptyGroup.getGroupMembers().isEmpty());
//        assertFalse(simpleGroup.getGroupMembers().isEmpty());
//    }
//
////    @Test
////    public void testGetExpenseList() {
////        assertTrue(emptyGroup.getExpenseList().isEmpty());
////        assertTrue(simpleGroup.getExpenseList().isEmpty());
////    }
//}
