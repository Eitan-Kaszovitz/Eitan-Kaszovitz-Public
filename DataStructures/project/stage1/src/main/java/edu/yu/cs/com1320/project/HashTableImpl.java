package edu.yu.cs.com1320.project;

public class HashTableImpl<Key, Value> implements HashTable<Key, Value> {
    Object[] table;
    int n;

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


    public Value get(Key k) {
        if (k == null) {
            throw new IllegalArgumentException();
        }
        int hash = (k.hashCode() % table.length);
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
        int hash = (k.hashCode() % table.length);
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

    protected void printTable(){
        for (int i = 0; i < table.length; i++) {
            Node current = (Node) table[i];
            System.out.print(current);
            if (current != null) {
                current = current.next;
            }
            else {
                System.out.println();
                continue;
            }
            while (current != null) {
                System.out.print(", " + current);
                current = current.next;
            }
            System.out.println();
        }
    }
}

