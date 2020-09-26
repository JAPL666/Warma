package com.japl.Warma;

import com.japl.Utils.WarmaUtils;
import com.japl.Utils.WarmaObjects;
import com.japl.Utils.count.CountUtils;

import java.util.Map;

public class Variable {
    //变量赋值
    public void Assign(String str){
        String name = WarmaUtils.getString(str,"变量","=").replace("@","");
        String type = str.split(" ")[0].trim();
        String value;
        //字符串类型
        if(str.contains("\"")){
            value = WarmaUtils.getString(str,name+"=\"","\";");

            if(value.contains("$(")&&!value.contains("@<")){
                String val = WarmaUtils.getString(str,"$(",")").trim();
                int x= CountUtils.count(val);
                value=String.valueOf(x);

            }
            Map<String, Object> m = WarmaObjects.WarmaMap();
            m.put("value",value);
            m.put("type",type);
            WarmaObjects.set(name,m);
        }
    }
}
