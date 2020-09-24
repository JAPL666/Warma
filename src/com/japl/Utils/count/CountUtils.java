package com.japl.Utils.count;

import java.util.List;

public class CountUtils {
    public static int count(String string){
        //后缀表达式计算
        List<String> x = RPN.infix2suffix(string);
        StringBuilder str= new StringBuilder();
        for(String xx:x){
            str.append(xx).append(",");
        }
        String sign = "+-*/";
        String[] exp = str.toString().split(",");
        StackExample stack = new StackExample();
        int result = 0;
        for (String s : exp) {
            if (sign.contains(s)) {
                NumberExample numberExample1 = stack.pop();
                NumberExample numberExample2 = stack.pop();
                switch (s) {
                    case "+":
                        result = numberExample2.getNumber() + numberExample1.getNumber();
                        break;
                    case "-":
                        result = numberExample2.getNumber() - numberExample1.getNumber();
                        break;
                    case "*":
                        result = numberExample2.getNumber() * numberExample1.getNumber();
                        break;
                    case "/":
                        result = numberExample2.getNumber() / numberExample1.getNumber();
                        break;
                }
                NumberExample numberExample = new NumberExample();
                numberExample.setNumber(result);
                stack.push(numberExample);      //将结果result入栈
            } else {
                NumberExample numberExample = new NumberExample();
                numberExample.setNumber(Integer.parseInt(s));
                stack.push(numberExample);
            }
        }
        return result;
    }
}
