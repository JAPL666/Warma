package com.japl.Warma;

import com.japl.Utils.WarmaUtils;

public class Loop {
    public Loop(String[] code,int index) {
        Start(code,index);
    }
    //循环
    public void Start(String[] code,int index){
        String str=code[index];
        String value=WarmaUtils.getString(str,"循环","次{");
        value=WarmaUtils.getVariableValue(value);
        int x=Integer.parseInt(value);

        for(int k=0;k<x-1;k++){
            StringBuilder c= new StringBuilder();
            int deep=0;
            int j;
            boolean bool=true;
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
                    c.append(code[j].trim()).append("\n");
                }
            }
            //执行代码
            Warma.execute(c.toString());
        }
    }
}
