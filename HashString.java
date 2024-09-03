package com.phase3.phase3;

public class HashString {
    private int size;
    private HNode table[];
    public int counter=0;

    public HashString(){
        table = new HNode[11];
        size = 11;
        for(int i=0; i<size; i++){
            table[i] = new HNode();
        }
    }

    public int add(String name){
        HNode value = new HNode(name);
        int index = hash(value);
        if(table[index].getCount()==0)  {
            counter++;
            table[index] = value;
        }
        table[index].setCount(table[index].getCount()+1);
        if(counter/(double)size>=0.5) reHash();
        return table[index].getCount();
    }

    private void reHash(){
        int newSize = nextPrime(size*2 + 1), n=size;
        HNode temp[] = table;
        table = new HNode[newSize];
        for(int i=0; i<newSize; i++){
            table[i] = new HNode();
        }
        int index;
        size = newSize;
        for(int i=0; i<n; i++){
            if(temp[i].getName()!=null) {
                index = hash(temp[i]);
                table[index] = temp[i];
            }
        }
    }
    private int nextPrime(int n){
        while(!isPrime(n)) n+=2;
        return n;
    }
    private boolean isPrime(int n){
        int sqrt = (int) Math.sqrt(n);
        for(int i=2; i<sqrt; i++) {
            if (n % i == 0) return false;
        }
        return true;
    }

    private int hash(HNode value){
        int index = Math.abs(value.hashCode()%size);
        int i=1;
        while(table[index].getCount()!=0 && !table[index].getName().equals(value.getName())) {
            index = Math.abs((value.hashCode()%size + (i*i)%size)%size);
            i++;
        }
        return index;
    }
    public HNode getNode(int i){
        return table[i];
    }
    public int size(){
        return size;
    }
    public HNode[] getTable(){
        return table;
    }
}
