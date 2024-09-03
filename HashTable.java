package com.phase3.phase3;

public class HashTable {
    private int size;
    private Node table[];
    public int counter=0;

    public HashTable(){
        table = new Node[11];
        size = 11;
        for(int i=0; i<size; i++){
            table[i] = new Node();
        }
    }

    public void add(Martyr martyr, String date){
        Node value = new Node(date);
        int index = hash(value);
        if(table[index].getFlag()!='F') {
            table[index] = value;
            counter++;
        }
        if(martyr!=null) table[index].getTree().insert(martyr);
        if(counter/(double)size>=0.5) reHash();
    }

    private void reHash(){
        int newSize = nextPrime(size*2 + 1), n=size;
        Node temp[] = table;
        table = new Node[newSize];
        for(int i=0; i<newSize; i++){
            table[i] = new Node();
        }
        int index;
        size = newSize;
        for(int i=0; i<n; i++){
            if(temp[i].getFlag()=='F') {
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

    private int hash(Node value){
        int index = value.hashCode()%size;
        int i=1;
        while(table[index].getFlag()=='F' && !table[index].getDate().equals(value.getDate())) {
            index = (value.hashCode()%size + (i*i)%size)%size;
            i++;
        }
        return index;
    }

    public void traverse(){
        for(int i=0; i<size; i++){
            if(table[i].getFlag()=='F') {
                System.out.print(i+" | " + table[i].getDate() + " | ");
                table[i].getTree().traverseInOrder();
                System.out.println();
            }
        }
    }

    public boolean delete(String date){
        int index = search(date);
        System.out.println(date);
        if(index!=-1) {
            table[index].setFlag('D');
            return true;
        }
        return false;
    }
    public int search(String date){
        Node value = new Node(date);
        int index = hash(value);
        if(table[index].getFlag()=='F') return index;
        return -1;
    }
    public void update(String date, String newDate){
        int i = search(newDate);
        int index = search(date);
        if(i==-1){
            delete(date);
            table[index].setDate(newDate);
            int newIndex = hash(table[index]);
            table[newIndex] = table[index];
            table[newIndex].setFlag('F');
            table[index] = new Node();
        }
        else if(i!=index){
            insertTree(i, table[index].getTree().getRoot());
            table[index].setFlag('D');
        }
    }
    private void insertTree(int index, TNode node){
        if(node==null) return;
        insertTree(index, node.getLeft());
        table[index].getTree().insert(node.getData());
        insertTree(index, node.getRight());
    }
    public Node getNode(int i){
        return table[i];
    }
    public int size(){
        return size;
    }
    // public int
    public Node[] getTable(){
        return table;
    }
}
