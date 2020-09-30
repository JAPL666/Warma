package com.japl.Warma;

import com.japl.Utils.WarmaUtils;

public class Print {
    //输出语句
    public Print(String str){
        String value=WarmaUtils.getString(str,"输出(\"","\");");
        value=WarmaUtils.getVariable(value);
        if(value.contains("$(")&&value.contains(")")&&!value.contains("@<")){
            value=WarmaUtils.getCount(value);
        }
        System.out.println(value);
    }
}
