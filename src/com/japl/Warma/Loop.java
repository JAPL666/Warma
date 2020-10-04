package com.japl.Warma;

import com.japl.Utils.WarmaUtils;

public class Loop {
    //循环
    public int Start(String[] code,int index){
        String str=code[index];
        String value=WarmaUtils.getString(str,"循环","次{");
        value=WarmaUtils.getVariableValue(value);
        int x=Integer.parseInt(value);
        int i=0;
        for(int k=0;k<x;k++){
            StringBuilder c= new StringBuilder();
            int deep=0;
            int j;
            boolean bool=true;
            boolean Continue=true;

            for(j=index;j<code.length;j++){
                if(code[j].contains("循环")&&code[j].contains("次{")){
                    deep++;
                }else{
                    if(code[j].contains("}循环结束;")){
                        deep--;
                        if(deep==0){
                            break;
                        }
                    }
                }
                if(bool){
                    bool=false;
                }else{
                    if(code[j].contains("跳过本次循环;")&&!code[j].contains("注释:")){
                        Continue=false;
                    }
                    if(code[j].contains("终止循环;")&&!code[j].contains("注释:")){
                        Continue=false;
                        k=x;
                    }
                    if(Continue){
                        c.append(code[j].trim()).append("\n");
                    }
                }
                i=j+1;
            }
            //执行代码
            Warma.execute(c.toString());
        }
        return i;
    }
}
