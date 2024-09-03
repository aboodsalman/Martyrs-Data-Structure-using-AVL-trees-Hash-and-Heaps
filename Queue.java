package com.phase3.phase3;

public class Queue {
    private SNode first, last;
    private static Integer count=-1;

    public Object peek() {
        if(first==null) return null;
        return first.getKey();
    }

    public void enQueue(Object data) {
        SNode SNode = new SNode(data);
        if(last==null) first=last=SNode;
        else {
            last.setNext(SNode);
            last=SNode;
        }
        count++;
    }

    public Object deQueue() {
        SNode temp=first;
        if(isEmpty()) return null;
        if(first==last) first=last=null;
        else {
            first=first.getNext();
            temp.setNext(null);
        }
        count--;
        return temp.getKey();
    }
    public boolean isEmpty(){
        return first==null;
    }
    public int size(){
        return count+1;
    }
}
