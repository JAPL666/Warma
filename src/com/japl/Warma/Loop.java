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
            boolean Continue=true;
            boolean bool=true;

            for(int j=index;j<code.length;j++){
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
                    //添加一行没用的代码，反正不执行
                    c.append("循环".trim()).append("\n");
                }else{
                    if(code[j].contains("跳过本次循环;")&&!code[j].contains("注释:")){
                        Continue=false;
                    }
                    if(code[j].contains("终止循环;")&&!code[j].contains("注释:")){
                        Continue=false;
                        k=x;
                        c.append("}循环结束;".trim()).append("\n");
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
