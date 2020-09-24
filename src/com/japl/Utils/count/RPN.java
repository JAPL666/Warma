package com.japl.Utils.count;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RPN {

    public static Map<String, Integer> signMap = new HashMap<>();   //存储运算符和优先级
    public static String signCol = "+-*/()";

    static {
        signMap.put("+", 1);
        signMap.put("-", 1);
        signMap.put("*", 4);
        signMap.put("/", 4);
        signMap.put("(", 0);       //用于括号里不只一次运算时  比如（3*2-3）
    }
    public static List<String> infix2suffix(String string) {
        StringBuilder str=new StringBuilder();
        char[] chars=string.toCharArray();
        for (char aChar : chars) {
            if (signCol.contains(String.valueOf(aChar))){
                str.append(",").append(aChar).append(",");
            }else{
                str.append(aChar);
            }
        }
        String[] strs=str.toString().split(",");
        str.delete(0,str.length());
        for(String s:strs){
            if(!s.equals("")){
                str.append(s).append(",");
            }
        }
        SignList signList = new SignList();
        String[] exp = str.toString().split(",");
        List<String> result = new ArrayList<>();

        for (String s : exp) {
            if (signCol.contains(s)) {
                if ("(".equals(s)) {     //如果是（  入栈
                    SignNode node = new SignNode();
                    node.setSignName(s);
                    signList.push(node);
                } else if (")".equals(s)) {
                    popStack(signList, result);
                } else {
                    pushOrPopStack(signList, s, result);
                }
            }else{   //不是运算符则添加到结果集
                result.add(s);
            }
        }
        while (signList.getLength() > 0) {
            SignNode node = signList.pop();
            result.add(node.getSignName());
        }
        return result;
    }

    public static void popStack(SignList signList, List<String> result) {
        SignNode node = signList.pop(); //将栈顶元素出栈
        if (!"(".equals(node.getSignName())) { //如果栈顶元素不是左括号（ 添加到结果集
            result.add(node.getSignName());
            popStack(signList, result);   //继续判断新的栈顶元素
        }
    }

    public static void pushOrPopStack(SignList signList, String val, List<String> result) {

        if (signList.getLength() != 0) {     //如果不为空栈

            SignNode stackTop = signList.element();             //获取栈顶元素
            if (signMap.get(stackTop.getSignName()) < signMap.get(val)) {      //优先级高于栈顶元素，则入栈
                SignNode signNode = new SignNode();
                signNode.setSignName(val);
                signList.push(signNode);
            } else {      //否则 栈顶元素出栈
                SignNode node = signList.pop();
                result.add(node.getSignName());     //将栈顶元素的值加入到结果集
                pushOrPopStack(signList, val, result);   //继续与新的栈顶元素进行比较
            }
        } else {      //如果为空栈   直接入栈
            SignNode node = new SignNode();
            node.setSignName(val);
            signList.push(node);
        }
    }
}