package com.japl.Warma;

import com.japl.Utils.WarmaUtils;

public class Branch {
    //分支语句
    public int Start(String[] code,int index,boolean bool){
        String str=code[index];

        String expression;
        char is;

        if(bool){
            expression = WarmaUtils.getString(str,"如果(","){").trim();
        }else{
            expression = WarmaUtils.getString(str,"如果不是(","){").trim();
        }

        //判断是否包含真假
        if(expression.equals("真")||expression.equals("假")){
            is=expression.charAt(0);
        }else{
            String res=WarmaUtils.getVariableValue(expression);
            is=new Expression().expres(res);
        }

        if (bool){
            return isTrue(is,index,code,'真');
        }else{
            return isTrue(is,index,code,'假');
        }
    }
    public int isTrue(char expression,int index,String[] code,char c){
        int x=0;
        if(expression==c){
            x=index;
        }else{
            for (int i=index;i<code.length;i++){
                if(code[i].contains("}否则{")){
                    x=i;
                    break;
                }
            }
        }
        return x;
    }
}
