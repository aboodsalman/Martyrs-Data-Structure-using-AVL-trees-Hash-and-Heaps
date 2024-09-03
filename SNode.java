package com.phase3.phase3;

public class SNode {
    private Object key;
    private SNode next;

    public SNode(Object key) {
        this.key = key;
    }

    public Object getKey() {
        return key;
    }

    public void setKey(Object key) {
        this.key = key;
    }

    public SNode getNext() {
        return next;
    }

    public void setNext(SNode next) {
        this.next = next;
    }

    public String toString() {
        return ((Node)key).getDate();
    }

    public boolean equals(Object o) {
        return ((Node)key).equals((Node)o);
    }

}
