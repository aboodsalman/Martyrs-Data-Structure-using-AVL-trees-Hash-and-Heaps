package com.phase3.phase3;

public class TNode implements Comparable<Object>{
    private TNode left, right;
    private Object data;
    int height;

    public TNode(Object data) {
        this.data = data;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public TNode getLeft() {
        return left;
    }

    public void setLeft(TNode left) {
        this.left = left;
    }

    public TNode getRight() {
        return right;
    }

    public void setRight(TNode right) {
        this.right = right;
    }
    public int compareTo(Object o) {
        return ((Martyr)data).compareTo((Martyr)o);
    }
    public int height() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

}