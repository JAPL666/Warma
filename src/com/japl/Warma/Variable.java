package com.japl.Warma;

import com.japl.Utils.WarmaUtils;
import com.japl.Utils.WarmaObjects;

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
            value =WarmaUtils.getVariableValue(value);

            Map<String, Object> m = WarmaObjects.WarmaMap();
            m.put("value",value);
            m.put("type",type);
            WarmaObjects.set(name,m);
        }
    }
}
