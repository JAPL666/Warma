package com.japl.Warma;

import com.japl.Utils.WarmaUtils;
import com.japl.Utils.count.CountUtils;

public class Count {
    //四则运算
    public void Integer(String str){
        //不处理函数返回变量
        if(!str.contains("}返回(")){
            String value = WarmaUtils.getString(str,"$(",")").trim();
            int x= CountUtils.count(value);
            Warma.execute(str.replace("$("+value+")",String.valueOf(x)));
        }
    }
}
