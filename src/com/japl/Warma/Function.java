package com.japl.Warma;

import com.japl.Utils.WarmaObjects;
import com.japl.Utils.WarmaUtils;

import java.util.Map;

public class Function {
    public Function(String[] code,int index){
        Start(code,index);
    }
    //函数
    public void Start(String[] code,int index){
        String str=code[index];
        boolean bool=false,b=true;
        StringBuilder builder=new StringBuilder();
        String name= WarmaUtils.getString(str,"#","(");
        String assing="";
        for (String s : code) {

            if (s.contains("函数 " + name + "(") && s.contains("(") && s.contains("){")) {
                bool = true;
                if (!str.contains("()")) {
                    //输入的参数
                    String[] values = WarmaUtils.getString(str, "(", ");").split(",");
                    //参数变量名
                    String[] Assign_values = WarmaUtils.getString(s, "(", "){").split(",");
                    for (int j = 0; j < values.length; j++) {
                        Assign_Type(values[j], Assign_values[j]);
                    }
                }
            }
            if (bool) {
                if (s.contains("}返回(") && s.contains("(") && s.contains(");")) {
                    if (!s.contains("}返回();")) {
                        //获取返回变量名
                        assing = WarmaUtils.getString(s, "}返回(", ");");
                    }
                    break;
                }
                if (b) {
                    //第一次运行，不执行
                    b = false;
                } else {
                    builder.append(s.trim()).append("\n");
                }
            }
        }
        //执行
        Warma.execute(builder.toString());

        //返回值赋值到声明的变量
        if(str.contains("变量")&&str.contains("=")){
            //获取变量的值
            String a = WarmaUtils.getString(assing,"@<",">");
            Map<String, Object> val= WarmaObjects.get(a);

            //获取新变量名
            String assing_new = WarmaUtils.getString(str,"变量","=").replace("@","");
            //赋值到新变量
            Map<String, Object> m = WarmaObjects.WarmaMap();
            m.put("value",val.get("value"));
            m.put("type","变量");
            WarmaObjects.set(assing_new,m);
        }
    }
    public void Assign_Type(String str,String assign){
        if(str.contains("\"")){
            Map<String, Object> m = WarmaObjects.WarmaMap();
            m.put("value",str.replace("\"",""));
            m.put("type","变量");
            WarmaObjects.set(assign,m);
        }
    }
}
