package com.japl.Warma;

import com.japl.Utils.WarmaUtils;

public class Expression {

    String  and="<和>",
            or="<或者>",
            equals="[等于]",
            contains="[包含]",
            morethan="[大于]",
            lessthan="[小于]",
            morethan_or_equals="[大于等于]",
            lessthan_or_equals="[小于等于]";

    public char expres(String str){
        if(str.contains(and)){
            //和
            String[] values=str.split("\\"+and);
            char x='假';
            for (String value:values){
                x=Switch(value);
                if(x=='假'){
                    break;
                }
            }
            return x;

        }else if(str.contains(or)){
            //或者
            String[] values=str.split("\\"+or);
            char x='假';
            for (String value:values){
                x=Switch(value);
                if(x=='真'){
                    break;
                }
            }
            return x;
        }else{
            return Switch(str);
        }
    }
    public char Switch(String str){

        if(str.contains(equals)){
            //等于
            return equals(str);
        }else if(str.contains(contains)){
            //包含
            return contains(str);
        }else if(str.contains(morethan)){
            //大于
            return morethan(str);
        }else if(str.contains(lessthan)){
            //小于
            return lessthan(str);
        }else if(str.contains(morethan_or_equals)){
            //大于等于
            return morethan_or_equals(str);
        }else if(str.contains(lessthan_or_equals)){
            //小于等于
            return lessthan_or_equals(str);
        }
        return '假';
    }

    //等于
    private char equals(String str){
        String[] value = str.split("\\"+equals);
        String v1=value[0].trim(),v2=value[1].trim();
        //如果是数字
        if(WarmaUtils.checkNum(v1)&&WarmaUtils.checkNum(v2)){
            if(Integer.parseInt(v1)==Integer.parseInt(v2)){
                return '真';
            }else{
                return '假';
            }
        }else{
            //如果是字符串
            if(v1.equals(v2)){
                return '真';
            }else{
                return '假';
            }
        }
    }

    //字符串包含
    private char contains(String str){
        String[] value = str.split("\\"+contains);
        String v1=value[0].trim(),v2=value[1].trim();
        if(v1.contains(v2)){
            return '真';
        }else{
            return '假';
        }
    }

    //大于
    private char morethan(String str){
        String[] value = str.split("\\"+morethan);
        String v1=value[0].trim(),v2=value[1].trim();
        //如果是数字
        if(WarmaUtils.checkNum(v1)&&WarmaUtils.checkNum(v2)){
            if(Integer.parseInt(v1)>Integer.parseInt(v2)){
                return '真';
            }else{
                return '假';
            }
        }
        return '假';
    }

    //小于
    private char lessthan(String str){
        String[] value = str.split("\\"+lessthan);
        String v1=value[0].trim(),v2=value[1].trim();
        //如果是数字
        if(WarmaUtils.checkNum(v1)&&WarmaUtils.checkNum(v2)){
            if(Integer.parseInt(v1)<Integer.parseInt(v2)){
                return '真';
            }else{
                return '假';
            }
        }
        return '假';
    }

    //大于等于
    private char morethan_or_equals(String str){
        String[] value = str.split("\\"+morethan_or_equals);
        String v1=value[0].trim(),v2=value[1].trim();
        //如果是数字
        if(WarmaUtils.checkNum(v1)&&WarmaUtils.checkNum(v2)){
            if(Integer.parseInt(v1)>=Integer.parseInt(v2)){
                return '真';
            }else{
                return '假';
            }
        }
        return '假';
    }

    //小于等于
    private char lessthan_or_equals(String str){
        String[] value = str.split("\\"+lessthan_or_equals);
        String v1=value[0].trim(),v2=value[1].trim();
        //如果是数字
        if(WarmaUtils.checkNum(v1)&&WarmaUtils.checkNum(v2)){
            if(Integer.parseInt(v1)<=Integer.parseInt(v2)){
                return '真';
            }else{
                return '假';
            }
        }
        return '假';
    }
}
