package com.phase3.phase3;

public class MaxHeap {
    private static int DEF_MAX_HEAP_SIZE = 10;
    private int size;
    private Martyr elements[];

    public MaxHeap() {
        setup(DEF_MAX_HEAP_SIZE);
    }

    private void setup(int MaxSize) {
        elements = new Martyr[MaxSize];
        size=-1;
    }

    public void insertMaxHeap(Martyr value) {
        if(isFull()) reSize();
        elements[++size] = value;
        int i=size, parent = (int)Math.ceil(((double)size)/2)-1;

        while(i>0) {
            if(elements[parent].getAge() < elements[i].getAge()) {
                swap(parent, i);
                i=parent;
                parent = (int)Math.ceil(((double)parent)/2)-1;
            }
            else return;
        }
    }
    private void reSize(){
        int newSize=2*(size+1);
        Martyr temp[] = elements;
        elements = new Martyr[newSize];
        DEF_MAX_HEAP_SIZE=newSize;
        for(int i=0;i<=size;i++){
            elements[i]=temp[i];
        }
    }

    public void heapSort() {
        for(int j=(int)Math.ceil(((double)size)/2)-1; j>=1; j--) maxHeapify(j);
        int b=size;
        for(int j=size; j>=0; j--) {
            swap(0, j);
            size--;
            maxHeapify(0);
        }
        size=b;
    }
    public void removeMaxNum() {
        swap(0, size--);
        maxHeapify();
    }

    public void maxHeapify() {
        for(int j=(int)Math.ceil(((double)size)/2)-1; j>=0; j--) {
            maxHeapify(j);
        }
    }

    private void maxHeapify(int i) {
        int largest = i, l = i*2+1, r = i*2+2;
        if(l<=size && elements[l].getAge() > elements[largest].getAge()) largest = l;
        if(r<=size && elements[r].getAge() > elements[largest].getAge()) largest = r;
        if(largest!=i) {
            swap(i, largest);
            maxHeapify(largest);
        }
    }

    private void swap(int parent, int i) {
        Martyr x = elements[parent];
        elements[parent] = elements[i];
        elements[i] = x;
    }
    public Martyr parent(Martyr value) {
        for(int i=1; i<=size; i++) {
            if(elements[i].equals(value) && i!=0) return elements[(int)Math.ceil(((double)i)/2)-1];
        }
        return null;
    }
    public Martyr leftChild(Martyr value) {
        for(int i=0; i<=size; i++) {
            if(elements[i].equals(value) && i*2+1<=size) return elements[i*2+1];
        }
        return null;
    }
    public Martyr rightChild(Martyr value) {
        for(int i=0; i<=size; i++) {
            if(elements[i].equals(value) && i*2+2<=size) return elements[i*2+2];
        }
        return null;
    }
    public void showStructure() {
        int i=1, j=0, p=1;
        while(true) {
            for(;j<i; j++) {
                if(j>size) return;
                System.out.print(elements[j]+" ");
            }
            p*=2;
            i+=p;
            System.out.println();
        }
    }
    public void showSubTree(int i, int level) {
        int j=0, p=1;
        while(level>=0) {
            for(;j<=i; j++) {
                if(j>size) return;
                System.out.print(elements[j]+" ");
            }
            p*=2;
            i+=p;
            System.out.println();
            level--;
        }
    }
    public void clear() {
        setup(DEF_MAX_HEAP_SIZE);
    }
    public boolean isEmpty() {
        return size==0;
    }
    public boolean isFull() {
        return size==DEF_MAX_HEAP_SIZE-1;
    }
    public boolean isLeaf(Martyr value) {
        for(int i=0; i<=size; i++) {
            if(elements[i].equals(value) && i>(int)Math.ceil(((double)size)/2)-1) return true;
        }
        return false;
    }
    public int size(){
        return size+1;
    }
    public Martyr getMartyr(int i){
        return elements[i];
    }

}
