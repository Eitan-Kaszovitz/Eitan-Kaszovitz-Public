package edu.yu.cs.com1320.project.Impl;

import edu.yu.cs.com1320.project.Stack;

public class StackImpl implements Stack {

    private Object[] myStack;
    private int top;
    private int max;

    public StackImpl(int max) {
        this.max = max;
        this.myStack = new Object[max];
        this.top = -1;
    }

    public void push(Object element) {
        if (top >= (max - 1)) {
            resize();
            push(element);
        }
        else
        {
            this.top++;
            myStack[top] = element;
        }
    }

    public Object pop() {
        if (top < 0)
        {
            return null;
        }
        else
        {
            Object oldTop = myStack[top];
            top--;
            return oldTop;
        }
    }

    public Object peek() {
        if (top < 0)
        {
            return null;
        }
        else {
            return myStack[top];
        }
    }

    public int size() {
        return top + 1;
    }

    protected void resize() {
        Object[] oldStack = this.myStack;
        int oldTop = top;
        this.top = -1;
        this.myStack = new Object[max * 2];
        for (int i = 0; i <= oldTop; i++) {
            this.push(oldStack[i]);
        }
    }

    protected int getMax () {
        return this.max;
    }
}
