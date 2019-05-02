package edu.yu.cs.com1320.project.Impl;

import edu.yu.cs.com1320.project.MinHeap;

import java.util.HashMap;
import java.util.NoSuchElementException;

public class MinHeapImpl extends MinHeap<Comparable> {

    private int size;

    public MinHeapImpl () {
        this.elements = new Comparable[20];
        this.count = 0;
        this.elementsToArrayIndex = new HashMap<Comparable, Integer>();
        this.size = 20;
    }
    public MinHeapImpl (int s) {
        this.size = s;
        this.elements = new Comparable[s];
        this.count = 0;
        this.elementsToArrayIndex = new HashMap<Comparable, Integer>();
    }


    @Override
    public void reHeapify(Comparable element) {
        int elementIndex = getArrayIndex(element);
        if (elementIndex > 1) {
            if (isGreater(elementIndex, elementIndex / 2)) {
                downHeap(elementIndex);
            }
            if (!isGreater(elementIndex, elementIndex / 2)) {
                upHeap(elementIndex);
            }
        }
        else {
            downHeap(elementIndex);
        }

    }

    @Override
    protected int getArrayIndex(Comparable element) {
        return elementsToArrayIndex.get(element);
    }

    @Override
    protected void doubleArraySize() {
        Comparable[] old = this.elements;
        this.elements = new Comparable[this.size * 2];
        System.arraycopy(old, 0, this.elements, 0, old.length);
    }

    @Override
    public void insert(Comparable x)
    {
        // double size of array if necessary
        if (this.count >= this.elements.length - 1)
        {
            this.doubleArraySize();
        }
        //add x to the bottom of the heap
        this.elements[++this.count] = x;
        //percolate it up to maintain heap order property
        this.upHeap(this.count);
    }

    @Override
    protected void upHeap(int k)
    {
        while (k > 1 && this.isGreater(k / 2, k))
        {
            this.swap(k, k / 2);
            k = k / 2;
        }
        Comparable element = this.elements[k];
        this.elementsToArrayIndex.put(element, k);
    }

    @Override
    protected  void swap(int i, int j)
    {
        Comparable temp = this.elements[i];
        this.elements[i] = this.elements[j];
        this.elements[j] = temp;
        this.elementsToArrayIndex.put(this.elements[i], i);
        this.elementsToArrayIndex.put(this.elements[j], j);
    }

    protected void delete(Comparable element) {
        if (isEmpty())
        {
            throw new NoSuchElementException("Heap is empty");
        }
        int elementIndex = this.getArrayIndex(element);
        //swap element with last, decrement count
        this.swap(elementIndex, this.count--);
        //move new element down as needed
        Comparable newElement = this.elements[elementIndex];
        this.reHeapify(newElement);
        this.elements[this.count + 1] = null; //null it to prepare for GC
    }
}
