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
        if(str.contains("\";")){
            value = WarmaUtils.getString(str,name+"=\"","\";");
            value =WarmaUtils.getVariableValue(value);

            Map<String, Object> map = WarmaObjects.WarmaMap();
            map.put("value",value);
            map.put("type",type);
            WarmaObjects.set(name,map);
        }else if(str.contains("[")&&str.contains("];")){
            //数组

            String val=WarmaUtils.getString(str,"=[","];");
            String[] arrays=val.split("\",\"");
            //移除开头引号
            arrays[0]=arrays[0].substring(1);
            //移除结尾引号
            arrays[arrays.length-1]=arrays[arrays.length-1].substring(0,arrays.length-1);

            Map<String, Object> map = WarmaObjects.WarmaMap();
            map.put("value",arrays);
            map.put("type",type);
            WarmaObjects.set(name,map);
        }
    }
}
