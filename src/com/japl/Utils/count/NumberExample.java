package com.japl.Utils.count;

public class NumberExample {   //节点
    private int number;
    private float f_number;
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

    public float getF_number() {
        return f_number;
    }

    public void setF_number(float f_number) {
        this.f_number = f_number;
    }
}