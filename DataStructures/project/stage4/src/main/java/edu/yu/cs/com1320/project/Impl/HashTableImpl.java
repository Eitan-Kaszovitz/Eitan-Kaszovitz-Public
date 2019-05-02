package edu.yu.cs.com1320.project.Impl;

import edu.yu.cs.com1320.project.HashTable;

public class HashTableImpl<Key, Value> implements HashTable<Key, Value> {
    private Object[] table;
    private int n;

    public HashTableImpl(int size) {
        this.n = 0;
        this.table = new Object[size];
        for (int i = 0; i < table.length; i++) {
            table[i] = null;
        }
    }

    public HashTableImpl() {
        this(101);
    }


    private class Node {
        Key key;
        Value value;
        Node next;

        private Node(Key key, Value value) {
            this.key = key;
            this.value = value;
            this.next = null;
        }

        @Override
        public String toString() {
            return String.format("%s", value);
        }


    }

    protected boolean deleteObject(Key uri) {
        int hash = Math.abs(uri.hashCode() % table.length);
        if (table[hash] == null) {
            return false;
        }
        Node current = (Node) table[hash];
        if (current.key.equals(uri)) {
            table[hash] = current.next;
            this.n--;
            return true;
        }
        else {
            Node previous = null;
            while (current != null && !current.key.equals(uri)) {
                previous = (Node) current;
                current = current.next;
            }
            if (current == null) {
                return false;
            } else {
                previous.next = current.next;
                this.n--;
                return true;
            }
        }
    }

    public Value get(Key k) {
        if (k == null) {
            throw new IllegalArgumentException();
        }
        int hash = Math.abs(k.hashCode() % table.length);
        if (table[hash] == null) {
            return null;
        } else {
            Node current = (Node) table[hash];
            while (current != null && !current.key.equals(k)) {
                current = current.next;
            }
            if (current == null) {
                return null;
            } else {
                return current.value;
            }
        }
    }


    public Value put(Key k, Value v) {
        if (k == null) {
            throw new IllegalArgumentException();
        }
        if (table.length <= (.25 * n)) {
            this.resize();
        }
        int hash = Math.abs(k.hashCode() % table.length);
        if (table[hash] == null) {
            table[hash] = new Node (k, v);
            this.n++;
            return null;
        }
        else {
            Node current = (Node) table[hash];
            while (current.next != null && !current.key.equals(k)) {
                current = current.next;
            }
            if (current.key.equals(k)) {
                Value oldValue = current.value;
                current.value = v;
                return oldValue;
            }
            else {
                current.next = new Node (k, v);
                this.n++;
                return null;
            }
        }
    }

    protected void resize() {
        Object[] oldvalues = table;
        this.table = new Object[table.length * 2];
        this.n = 0;
        for (int i = 0; i < oldvalues.length; i++) {
            Node current = (Node) oldvalues[i];
            while (current != null) {
                this.put(current.key, current.value);
                current = current.next;
            }
        }
    }

    protected int getN() {
        return this.n;
    }

    protected void printTable(){
        for (int i = 0; i < table.length; i++) {
            Node current = (Node) table[i];
            if (current != null) {
                System.out.print(current.toString());
            }
            else {
                System.out.print(current);
            }
            if (current != null) {
                current = current.next;
            }
            else {
                System.out.println();
                continue;
            }
            while (current != null) {
                System.out.print(", " + current.toString());
                current = current.next;
            }
            System.out.println();
        }
    }
}

