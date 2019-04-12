package edu.yu.cs.com1320.project.Impl;

import edu.yu.cs.com1320.project.Trie;

import java.util.*;

public class TrieImpl<Value> implements Trie<Value> {

    private static final int alphabetSize = 256;
    private Node root;
    private Comparator comparator;

    public TrieImpl() {
        this.root = new Node();
        this.comparator = null;
    }

    protected class Node<Value>
    {
        protected List<Value> valList;
        protected Node[] links;

        protected Node() {
            links = new Node[alphabetSize];
            for (int i = 0; i < links.length; i++) {
                links[i] = null;
            }
            valList = null;
        }
        protected Node(Value v) {
            valList = new ArrayList<>{v};
            links = new Node[alphabetSize];
            for (int i = 0; i < links.length; i++) {
                links[i] = null;
            }
        }

        protected void addValue(Value e) {
            if (!valList.contains(e)) {
                valList.add(e);
            }
        }

        protected void removeValue(Value k) {
            if (valList.contains(k)) {
                valList.remove(k);
            }
        }
    }


    public List<Value> getAllSorted(String key) {
        List<Value> finalList =  this.get(this.root, key, 0);
        Collections.sort(finalList, this.comparator);
        return finalList;
    }

    private List<Value> get(Node x, String key, int d)
    {
        if (x == null)
        {
            return null;
        }
        if (d == key.length())
        {
            return x.valList;
        }

        char c = key.charAt(d);
        return this.get(x.links[c], key, d + 1);
    }


    public void put(String key, Value val) {
        if (val == null)
        {
            this.deleteAll(key);
        }
        else
        {
            this.root = put(this.root, key, val, 0);
        }
    }

    private Node put(Node x, String key, Value val, int d)
    {
        if (x == null)
        {
            x = new Node(val);
        }
        if (d == key.length())
        {
            x.addValue(val);
            return x;
        }

        char c = key.charAt(d);
        x.links[c] = this.put(x.links[c], key, val, d + 1);
        return x;
    }


    public void deleteAll(String key) {
        this.root = deleteAll(this.root, key, 0);
    }

    private Node deleteAll(Node x, String key, int d)
    {
        if (x == null)
        {
            return null;
        }
        if (d == key.length())
        {
            x.valList.clear();
        }
        else
        {
            char c = key.charAt(d);
            x.links[c] = this.deleteAll(x.links[c], key, d + 1);
        }
        if (!x.valList.isEmpty())
        {
            return x;
        }
        for (int c = 0; c <TrieImpl.alphabetSize; c++)
        {
            if (x.links[c] != null)
            {
                return x;
            }
        }
        return null;
    }


    public void delete(String key, Value val) {
        this.root = delete(this.root, key, val, 0);
    }

    private Node delete(Node x, String key, Value val, int d) {
        if (x == null)
        {
            return null;
        }
        if (d == key.length())
        {
            x.removeValue(val);
        }
        else
        {
            char c = key.charAt(d);
            x.links[c] = this.delete(x.links[c], key, d + 1);
        }
        if (x.valList.equals(null)) { //root node - the only node with a null value list
            return x;
        }
        if (!x.valList.isEmpty())     // return node that has documents attached to it
        {
            return x;
        }
        for (int c = 0; c <TrieImpl.alphabetSize; c++)
        {
            if (x.links[c] != null)  // return node that has letters attached to it
            {
                return x;
            }
        }
        return null;
    }

    protected void setComparator(Comparator comparator) {
        this.comparator = comparator;
    }

}
