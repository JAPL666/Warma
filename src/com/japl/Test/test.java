package com.japl.Test;

public class test {

    public static void main(String[] args) {
        //后缀表达式计算
        stack();
    }

    private static void stack(){
        String str = "9,3,1,-,3,*,+,10,2,/,+";
        String sign = "+-*/";
        String[] exp = str.split(",");
        StackExample stack = new StackExample();
        int result = 0;
        for(int i = 0; i < exp.length; i++){
            if(sign.contains(exp[i])){
                NumberExample numberExample1 = stack.pop();
                NumberExample numberExample2 = stack.pop();
                if("+".equals(exp[i])){
                    result = numberExample2.getNumber() + numberExample1.getNumber();
                }else if("-".equals(exp[i])){
                    result = numberExample2.getNumber() - numberExample1.getNumber();
                }else if("*".equals(exp[i])){
                    result = numberExample2.getNumber() * numberExample1.getNumber();
                }else if("/".equals(exp[i])){
                    result = numberExample2.getNumber() / numberExample1.getNumber();
                }
                NumberExample numberExample = new NumberExample();
                numberExample.setNumber(result);
                stack.push(numberExample);      //将结果result入栈
            }else{
                NumberExample numberExample = new NumberExample();
                numberExample.setNumber(Integer.parseInt(exp[i]));
                stack.push(numberExample);
            }
            System.out.println(result);
        }
    }
}
