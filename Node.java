package com.phase3.phase3;

public class Node {
    private String date;
    private char flag;
    private AVLTree tree;

    public Node(String date){
        this.date = date;
        flag='F';
        tree = new AVLTree();
    }

    @Override
    public int hashCode() {
        int sum=0;
        for(int i=0;i<date.length();i++){
            sum+=date.charAt(i);
        }
        return sum;
    }

    public Node(){
        flag = 'E';
        tree = new AVLTree();
    }
    public boolean equals(Node n){
        return date.equals(n.getDate());
    }
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public char getFlag() {
        return flag;
    }

    public void setFlag(char flag) {
        this.flag = flag;
    }

    public AVLTree getTree() {
        return tree;
    }

    public void setTree(AVLTree tree) {
        this.tree = tree;
    }
    public String toString(){
        return date;
    }
}
