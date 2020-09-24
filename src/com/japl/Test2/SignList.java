package com.japl.Test2;

public class SignList {
    private SignNode list;
    private int length;

    public SignNode element(){
        return list;
    }

    public SignNode pop(){
        SignNode signNode = new SignNode();
        signNode = list;
        list = list.next;
        length --;
        return signNode;
    }

    public void push(SignNode signNode){
        signNode.next = list;
        list = signNode;
        length ++;
    }

    public SignNode getList() {
        return list;
    }

    public void setList(SignNode list) {
        this.list = list;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }
}