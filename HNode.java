package com.phase3.phase3;

public class HNode {
    private String name;
    private int count;

    public HNode(String name) {
        this.name = name;
    }
    public HNode(){}
    public String getName(){
        return name;
    }
    public int getCount(){
        return count;
    }
    public void setName(String name){
        this.name = name;
    }
    public void setCount(int count){
        this.count = count;
    }
    public int hashCode(){
        int sum=0;
        for(int i=0;i<name.length();i++){
            sum+=name.charAt(i);
        }
        return sum;
    }
}
