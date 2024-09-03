package com.phase3.phase3;

public class DistrictsHash {
    private int size;
    private DistrictsNode table[];
    public int counter=0;

    public DistrictsHash(){
        table = new DistrictsNode[11];
        size = 11;
        for(int i=0; i<size; i++){
            table[i] = new DistrictsNode();
        }
    }

    public void add(String name, String location){
        DistrictsNode value = new DistrictsNode(name);
        int index = hash(value);
        if(table[index].getDistrict()==null)  {
            counter++;
            table[index] = value;
        }
        if (!table[index].getLocations().contains(location)) table[index].getLocations().add(location);
        if(counter/(double)size>=0.5) reHash();
    }

    private void reHash(){
        int newSize = nextPrime(size*2 + 1), n=size;
        DistrictsNode temp[] = table;
        table = new DistrictsNode[newSize];
        for(int i=0; i<newSize; i++){
            table[i] = new DistrictsNode();
        }
        int index;
        size = newSize;
        for(int i=0; i<n; i++){
            if(temp[i].getDistrict()!=null) {
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

    private int hash(DistrictsNode value){
        int index = Math.abs(value.hashCode()%size);
        int i=1;
        while(table[index].getDistrict()!=null && !table[index].getDistrict().equals(value.getDistrict())) {
            index = Math.abs((value.hashCode()%size + (i*i)%size)%size);
            i++;
        }
        return index;
    }
    public DistrictsNode search(String name){
        DistrictsNode value = new DistrictsNode(name);
        int index = hash(value);
        if(table[index].getDistrict()!=null) return table[index];
        return null;
    }
    public DistrictsNode getNode(int i){
        return table[i];
    }
    public int size(){
        return size;
    }
    public DistrictsNode[] getTable(){
        return table;
    }
}
