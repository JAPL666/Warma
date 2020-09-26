package com.japl.Warma;

import com.japl.Utils.WarmaUtils;

public class Branch {
    //分支语句
    public int Start(String[] code,int index,boolean bool){
        String str=code[index];
        String expression = WarmaUtils.getString(str,"如果(",")");
        if (bool){
            return isTrue(expression,index,code,'真');
        }else{
            return isTrue(expression,index,code,'假');
        }
    }
    public int isTrue(String expression,int index,String[] code,char c){
        int x=0;
        if(expression.equals(String.valueOf(c))){
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
