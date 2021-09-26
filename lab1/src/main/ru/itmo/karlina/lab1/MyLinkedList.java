package ru.itmo.karlina.lab1;

public class MyLinkedList<T> {
    private Node<T> head;
    private Node<T> tail;

    Node<T> add(T value) {
        Node<T> node = new Node<>(value);
        if (head == null) { // means list is empty
            head = node;
        } else {
            node.prev = tail;
            tail.next = node;
        }
        tail = node;
        assert tail.value == value;
        return tail;
    }

    Node<T> remove () {
        assert tail != null;
        Node<T> node = tail;
        tail = tail.prev;
        if (tail == null) {
            head = null;
        } else {
            tail.next = null;
        }
        assert tail == null || !tail.equals(node);
        return node;
    }

    void remove (Node<T> node) {
        assert node != null;
        Node<T> prev = node.prev, next = node.next;
        if (prev == null) {
            head = next;
        } else {
            prev.next = next;
        }
        if (next == null) {
            tail = prev;
        } else {
            next.prev = prev;
        }
        assert ((node.prev == null) || (node.prev.next == node.next)) && ((node.next == null) || (node.next.prev == node.prev));
    }

    static class Node<T> {
        private Node<T> next, prev;
        T value;

        Node(T value) {
            this.value = value;
        }
    }
}
