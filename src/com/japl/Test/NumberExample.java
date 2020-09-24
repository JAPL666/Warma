package com.japl.Test;

public class NumberExample {   //节点
    private int number;
    private NumberExample next;

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public NumberExample getNext() {
        return next;
    }

    public void setNext(NumberExample next) {
        this.next = next;
    }
}