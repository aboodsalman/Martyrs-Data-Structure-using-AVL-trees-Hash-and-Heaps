package com.phase3.phase3;

public class SingleLinkedList {
    private SNode first, last;
    private int count=-1;


    public SNode getFirst() {
        if(first==null) return null;
        return first;
    }

    public SNode getLast() {
        if(last==null) return null;
        return last;
    }

    public boolean addFirst(Object data) {
        if(((Node)data).equals((Node)first.getKey())) return false;
        SNode node = new SNode(data);
        if(first==null) first=last=node;
        else {
            node.setNext(first);
            first=node;
        }
        count++;
        return true;
    }
    public boolean addFirstStack(Object data) {
        SNode node = new SNode(data);
        if(count==-1) first=last=node;
        else {
            node.setNext(first);
            first=node;
        }
        count++;
        return true;
    }

    public boolean addLast(Object data) {
        SNode node = new SNode(data);
        if(first==null) first=last=node;
        else {
            last.setNext(node);
            last=node;
        }
        count++;
        return true;
    }

    public void add(Object data, int index) {
        SNode SNode = new SNode(data);
        if(index<=0) addFirst(data);
        else if(index>count) addLast(data);
        else {
            SNode temp = first;
            for(int i=0; i<index-1; i++) temp=temp.getNext();
            SNode.setNext(temp.getNext());
            temp.setNext(SNode);
            count++;
        }
    }

    public boolean find(Node data) {
        SNode node = first;
        while(node!=null) {
            if(((Node)node.getKey()).getDate().equals(data.getDate())) return true;
            node=node.getNext();
        }
        return false;
    }


    public boolean removeFirst() {
        if(first==null) return false;
        if(first==last) first=last=null;
        else {
            SNode temp = first;
            first=first.getNext();
            temp.setNext(null);
        }
        count--;
        return true;
    }

    public boolean removeLast() {
        if(first==null) return false;
        if(first==last) first=last=null;
        else {
            SNode temp=first;
            for(int i=0; i<count-1; i++) temp=temp.getNext();
            temp.setNext(null);
            last=temp;
        }
        count--;
        return true;
    }

    public boolean remove(int index) {
        if(index<0 || index>count) return false;
        if(index==0) return removeFirst();
        if(index==count) return removeLast();
        else {
            SNode temp = first;
            for(int i=0; i<index-1; i++) temp=temp.getNext();
            SNode trash = temp.getNext();
            temp.setNext(temp.getNext().getNext());
            trash.setNext(null);
            count--;
            return true;
        }
    }

    public boolean remove(Object data) {
        if(first==null) return false;
        if(first.getKey().equals(data)) return removeFirst();
        if(last.getKey().equals(data)) return removeLast();
        else {
            SNode temp = first;
            for(int i=0; i<count; i++) {
                if(temp.getKey().equals(data)) return remove(i);
                temp=temp.getNext();
            }
        }
        return false;
    }

    public int size() {
        return count+1;
    }
}

