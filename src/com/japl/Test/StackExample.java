package com.japl.Test;

public class StackExample {    //记录节点的链表
    private NumberExample popNumber = new NumberExample();
    private int length;


    public NumberExample pop(){
        NumberExample numberExample = new NumberExample();
        numberExample = popNumber;
        popNumber = popNumber.getNext();
        length --;
        return numberExample;
    }

    public void push(NumberExample stu){
        stu.setNext(popNumber);
        popNumber = stu;
        length++;
    }

    public NumberExample getPopStudent() {
        return popNumber;
    }

    public void setPopStudent(NumberExample student) {
        this.popNumber = student;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }
}
