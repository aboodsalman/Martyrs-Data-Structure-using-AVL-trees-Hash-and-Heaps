package com.phase3.phase3;

import java.lang.reflect.Array;

public class AVLTree extends BaseBinaryTree{

    public void updateHeight(TNode node){
        if(node==null) return;
        node.setHeight(getDepth(node)-1);
    }
    public int getDepth(TNode node) {
        if(node==null) return 0;
        return Math.max(1+getDepth(node.getLeft()), 1+getDepth(node.getRight()));
    }
    private TNode rotateToRight(TNode node){
        TNode leftChild = node.getLeft();
        if(leftChild!=null){
            node.setLeft(leftChild.getRight());
            leftChild.setRight(node);
        }
        updateHeight(node);
        updateHeight(leftChild);
        return leftChild;
    }
    private TNode rotateToLeft(TNode node){
        TNode rightChild = node.getRight();
        if(rightChild!=null){
            node.setRight(rightChild.getLeft());
            rightChild.setLeft(node);
        }
        updateHeight(node);
        updateHeight(rightChild);
        return rightChild;
    }
    public int getHeight(TNode node){
        return node==null ? -1 : node.height();
    }

    public int Balance(TNode node){
        if(node==null) return 0;
        return getHeight(node.getLeft())-getHeight(node.getRight());
    }
    public void insert(Object data){
        this.setRoot(insert(this.getRoot(), data));
    }
    public TNode insert(TNode node, Object data){
        if(node==null) return new TNode(data);
        if(((Martyr)data).compareTo((Martyr)node.getData())<0) node.setLeft(insert(node.getLeft(), data));
        else if(((Martyr)data).compareTo((Martyr)node.getData())>0) node.setRight(insert(node.getRight(), data));
        else return node;
        updateHeight(node);
        int bf = Balance(node);
        if(bf>1){
            if(Balance(node.getLeft())>=0) return rotateToRight(node);
            else{
                node.setLeft(rotateToLeft(node.getLeft()));
                return rotateToRight(node);
            }
        }
        else if(bf<-1){
            if(Balance(node.getRight())<=0) {

                return rotateToLeft(node);
            }
            else{
                node.setRight(rotateToRight(node.getRight()));
                return rotateToLeft(node);
            }
        }
        else return node;
    }
    public void traverseInOrder() {
        traverseInOrder(this.getRoot());
    }
    private void traverseInOrder(TNode node) {
        if(node==null) return;
        traverseInOrder(node.getLeft());
        System.out.print(node.getData()+" ");
        traverseInOrder(node.getRight());
    }

    public TNode deleteNode(TNode root, Object key) {
        if (root == null)
            return root;

        if (((Martyr)key).compareTo((Martyr)root.getData())<0)
            root.setLeft(deleteNode(root.getLeft(), key));

        else if (((Martyr)key).compareTo((Martyr)root.getData())>0)
            root.setRight(deleteNode(root.getRight(), key));

        else
        {

            if ((root.getLeft() == null) || (root.getRight() == null))
            {
                TNode temp = null;
                if (temp == root.getLeft())
                    temp = root.getRight();
                else
                    temp = root.getLeft();

                if (temp == null)
                {
                    temp = root;
                    root = null;
                }
                else
                    root = temp;
            }
            else
            {
                TNode temp = minValueNode(root.getRight());
                root.setData(temp.getData());
                root.setRight(deleteNode(root.getRight(), temp.getData()));
            }
        }
        if (root == null)
            return root;

        root.height = Math.max(height(root.getLeft()), height(root.getRight())) + 1;
        int balance = Balance(root);
        if (balance > 1 && Balance(root.getLeft()) >= 0)
            return rotateToRight(root);

        if (balance > 1 && Balance(root.getLeft()) < 0)
        {
            root.setLeft(rotateToLeft(root.getLeft()));
            return rotateToRight(root);
        }

        if (balance < -1 && Balance(root.getRight()) <= 0)
            return rotateToLeft(root);
        if (balance < -1 && Balance(root.getRight()) > 0)
        {
            root.setRight(rotateToRight(root.getRight()));
            return rotateToLeft(root);
        }

        return root;
    }
    int height(TNode N) {
        if (N == null)
            return 0;

        return N.height;
    }
    private TNode minValueNode(TNode node) {
        TNode current = node;
        while (current.getLeft() != null)
            current = current.getLeft();

        return current;
    }
    public int size(){
        return size(this.getRoot());
    }
    private int size(TNode node) {
        if(node==null) return 0;
        return 1+size(node.getLeft())+size(node.getRight());
    }
    public double avgAges(){
        return ((double)getAvgs(getRoot()))/size();
    }
    private int getAvgs(TNode node){
        if(node==null) return 0;
        return ((Martyr)node.getData()).getAge()+getAvgs(node.getLeft())+getAvgs(node.getRight());
    }

    public String maxDistrictLocation(){
        if(getRoot()==null) return "No Martyrs Exist";
        Queue queue = new Queue();
        queue.enQueue(getRoot());
        HashString hashDistrict = new HashString(), hashLocation = new HashString();
        int mxDistrict=0, mxLocation=0;
        String district = "", location="";
        while(!queue.isEmpty()){
            TNode node = (TNode)queue.deQueue();
            if(node.getLeft()!=null) queue.enQueue(node.getLeft());
            if(node.getRight()!=null) queue.enQueue(node.getRight());
            int countDistrict=hashDistrict.add(((Martyr)node.getData()).getDistrict());
            int countLocation=hashLocation.add(((Martyr)node.getData()).getLocation());
            if(countDistrict>mxDistrict){
                mxDistrict=countDistrict;
                district=((Martyr)node.getData()).getDistrict();
            }
            if(countLocation>mxLocation){
                mxLocation=countLocation;
                location=((Martyr)node.getData()).getLocation();
            }
        }
        return district+","+location;
    }

}