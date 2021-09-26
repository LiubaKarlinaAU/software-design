package ru.itmo.karlina.lab1;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class FastLinkedListTest {
    @Test
    public void addRemoveOne() {
        FastLinkedList<Integer> list = new FastLinkedList<>();
        list.add(1);
        // assert there is one node in list, and it has expected value
        assertEquals(1, (int) list.head.value);
        assertEquals(1, (int) list.tail.value);
        list.remove();
        // assert list is empty
        assertNull(list.head);
        assertNull(list.tail);
    }

    @Test
    public void addRemoveMany() {
        FastLinkedList<Integer> list = new FastLinkedList<>();
        for (int i = 1; i < 5; i++) {
            list.add(i);
            assertEquals(i, (int) list.head.value);
        }
        for (int i = 1; i < 5; i++) {
            FastLinkedList.Node<Integer> node = list.remove();
            assertEquals(i, (int) node.value);
        }
        // assert list is empty
        assertNull(list.head);
        assertNull(list.tail);
    }

    @Test
    public void removeNode() {
        FastLinkedList<Integer> list = new FastLinkedList<>();
        list.add(1);
        FastLinkedList.Node<Integer> node = list.add(3);
        list.remove(node);
        // assert there is one node in list, and it has expected value
        assertEquals(1, (int) list.head.value);
        assertEquals(1, (int) list.tail.value);
    }

    @Test
    public void removeNodeMany() {
        FastLinkedList<Integer> list = new FastLinkedList<>();
        List<FastLinkedList.Node<Integer>> nodeToRemove = new ArrayList<>();
        list.add(1);
        list.add(2);
        nodeToRemove.add(list.add(143));
        list.add(3);
        list.add(4);
        list.add(5);
        nodeToRemove.add(list.add(345));
        nodeToRemove.add(list.add(296));
        list.add(6);

        for (FastLinkedList.Node<Integer> node : nodeToRemove) {
            list.remove(node);
        }
        for (int i = 1; i < 7; i++) {
            FastLinkedList.Node<Integer> node = list.remove();
            assertEquals(i, (int) node.value);
        }
        // assert list is empty
        assertNull(list.head);
        assertNull(list.tail);
    }
}
