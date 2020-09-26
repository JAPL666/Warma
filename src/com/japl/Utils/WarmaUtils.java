package com.japl.Utils;

public class WarmaUtils {
    //获取指定范围的字符串
    public static String getString(String str,String start,String end){
        return  str.substring(str.indexOf(start)+start.length(),str.indexOf(end)).trim();
    }
    //获取代码块结束行
    public static int getEndRow(String[] code,int index,String[] Start,String[] End){
        int deep=0;
        int j;
        int x = 0;
        for(j=index;j<code.length;j++){
            boolean b1=false;
            boolean b2=false;
            for(String start:Start){
                //这里有问题记得修改
                b1= code[j].contains(start);
            }

            for(String end:End){
                b2= code[j].contains(end);
            }

            if(b1){
                deep++;
            }else{
                if(b2){
                    deep--;
                    if(deep==0){
                        x=j;
                        break;
                    }
                }
            }
        }
        return x;
    }
    public static int getFunctionEnd(String[] code,int index){
        String name= WarmaUtils.getString(code[index],"函数","(");
        boolean bool=false;
        int x=0;
        for(int i=0;i<code.length;i++){
            if(code[i].contains("函数 "+name+"(")&&code[i].contains("(")&&code[i].contains("){")){
                bool=true;
            }
            if(bool){
                if(code[i].contains("}返回(")&&code[i].contains("(")&&code[i].contains(");")){
                    x=i;
                    break;
                }
            }
        }
        return x;
    }
}
