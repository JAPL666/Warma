package com.japl.Utils;

import java.util.HashMap;
import java.util.Map;

public class WarmaObjects {
    public static String ClassPath="";
    public static Map<String,Map<String,Object>> warmaObj=new HashMap<>();
    public static Map<String,Object> WarmaMap(){
        return new HashMap<>();
    }
    public static void set(String name,Map<String,Object> map){
        warmaObj.put(name,map);
    }
    public static Map<String,Object> get(String name){
        return warmaObj.get(name);
    }
}
