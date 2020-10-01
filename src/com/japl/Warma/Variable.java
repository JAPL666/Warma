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
        if(str.contains("+=")&&str.contains("\";")){
            //连接
            name = WarmaUtils.getString(str,"变量","+=").replace("@","");
            value = WarmaUtils.getString(str,name+"+=\"","\";");
            value =WarmaUtils.getVariableValue(value);

            //判断是否数组
            if(name.contains("[")&&name.contains("]")){
                //获取下标
                int index = Integer.parseInt(WarmaUtils.getString(name,"[","]").trim());
                //获取数组变量名
                String arrayName=WarmaUtils.getString(str,"@","[").replace("@","");

                Map<String, Object> val= WarmaObjects.get(arrayName);

                //获取数组中的值
                String[] arrays=(String[])val.get("value");
                arrays[index]+=value;

                Map<String, Object> map = WarmaObjects.WarmaMap();
                map.put("value",arrays);
                map.put("type",type);
                WarmaObjects.set(arrayName,map);
            }else{
                String a_str=WarmaObjects.get(name).get("value").toString();
                Map<String, Object> map = WarmaObjects.WarmaMap();

                map.put("value",a_str+value);
                map.put("type",type);
                WarmaObjects.set(name,map);
            }
        }else if(str.contains("=")&&str.contains("\";")){
            //赋值
            value = WarmaUtils.getString(str,name+"=\"","\";");
            value =WarmaUtils.getVariableValue(value);

            //判断是否数组
            if(name.contains("[")&&name.contains("]")){
                //获取下标
                int index = Integer.parseInt(WarmaUtils.getString(name,"[","]").trim());
                //获取数组变量名
                String arrayName=WarmaUtils.getString(str,"@","[").replace("@","");

                Map<String, Object> val= WarmaObjects.get(arrayName);

                //获取数组中的值
                String[] arrays=(String[])val.get("value");
                arrays[index]=value;

                //修改数组中的值
                Map<String, Object> map = WarmaObjects.WarmaMap();
                map.put("value",arrays);
                map.put("type",type);
                WarmaObjects.set(arrayName,map);
            }else{
                //赋值
                Map<String, Object> map = WarmaObjects.WarmaMap();
                map.put("value",value);
                map.put("type",type);
                WarmaObjects.set(name,map);
            }
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
