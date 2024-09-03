package com.phase3.phase3;

public class Stack {
    SingleLinkedList stack;

    public Stack() {
        stack = new SingleLinkedList();
    }

    public boolean push(Object obj) {
        stack.addFirstStack(obj);
        return true;
    }

    public boolean isEmpty() {
        return stack.getFirst()==null;
    }

    public Object peek() {
        if (stack.size() == 0)
            return null;
        return stack.getFirst().getKey();
    }

    public Object pop() {
        if (stack.size() == 0)
            return null;
        Object res = stack.getFirst().getKey();
        stack.removeFirst();
        return res;
    }

    public void printStack() {
        Stack s2 = new Stack();
        while (this.getStack().size() != 0) {
            System.out.println(this.peek());
            s2.push(this.pop());
        }
        while (s2.getStack().size() != 0)
            this.push(s2.pop());
    }

    public void delete(Object data) {
        Stack s = new Stack();
        while(!this.isEmpty()) {
            if(((SNode)this.peek()).getKey().equals(data)) {
                this.pop();
                break;
            }
            else s.push(this.pop());
        }
        while(!s.isEmpty()) this.push(s.pop());
    }

    public SingleLinkedList getStack() {
        return stack;
    }

    public void setStack(SingleLinkedList stack) {
        this.stack = stack;
    }

    public int getSize() {
        return stack.size();
    }
    public void clear() {
        stack = new SingleLinkedList();
    }

}
