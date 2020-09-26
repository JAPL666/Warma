package com.japl.Warma;

import com.japl.Utils.WarmaObjects;
import com.japl.Utils.WarmaUtils;
import com.japl.Utils.count.CountUtils;

import java.util.Map;

public class Count {
    //四则运算
    public void Integer(String str){
        //不处理函数返回变量
        if(!str.contains("}返回(")){
            String value = WarmaUtils.getString(str,"$(",")").trim();
            if(str.contains("@<")&&str.contains(">")){
                String v = WarmaUtils.getString(str,"@<",">").trim();
                Map<String, Object> val= WarmaObjects.get(v);
                String res=str.replace("@<"+v+">",val.get("value").toString());
                Warma.execute(res.trim());
            }else{
                int x= CountUtils.count(value);
                Warma.execute(str.replace("$("+value+")",String.valueOf(x)));
            }
        }
    }
}
