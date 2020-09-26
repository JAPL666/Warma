package com.japl.Warma;

import com.japl.Utils.WarmaObjects;
import com.japl.Utils.WarmaUtils;

import java.util.Map;

public class Print {
    //输出语句
    public Print(String str){
        String res;
        if(str.contains("@<")&&str.contains(">")){
            String value = WarmaUtils.getString(str,"@<",">").trim();
            Map<String, Object> val= WarmaObjects.get(value);
            res=str.replace("@<"+value+">",val.get("value").toString());
            Warma.execute(res.trim());
        }else{
            System.out.println(WarmaUtils.getString(str,"输出(\"","\");"));
        }
    }
}
