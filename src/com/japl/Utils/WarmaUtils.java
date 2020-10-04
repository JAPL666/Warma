package com.japl.Utils;

import com.japl.Utils.count.CountUtils;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WarmaUtils {
    //获取指定范围的字符串
    public static String getString(String str,String start,String end){
        return  str.substring(str.indexOf(start)+start.length(),str.indexOf(end)).trim();
    }
    //获取代码块结束行
    public static int getEndRow(String[] code,int index,String[] Start,String[] End){
        int deep=0;
        int j;
        int x = 0;
        for(j=index;j<code.length;j++){
            boolean b1=false;
            boolean b2=false;
            for(String start:Start){
                //这里有问题记得修改
                b1= code[j].contains(start);
            }

            for(String end:End){
                b2= code[j].contains(end);
            }

            if(b1){
                deep++;
            }else{
                if(b2){
                    deep--;
                    if(deep==0){
                        x=j;
                        break;
                    }
                }
            }
        }
        return x;
    }
    //获取函数底部
    public static int getFunctionEnd(String[] code,int index){
        String name= WarmaUtils.getString(code[index],"函数","(");
        boolean bool=false;
        int x=0;
        for(int i=0;i<code.length;i++){
            if(code[i].contains("函数 "+name+"(")&&code[i].contains("(")&&code[i].contains("){")){
                bool=true;
            }
            if(bool){
                if(code[i].contains("}返回(")&&code[i].contains("(")&&code[i].contains(");")){
                    x=i;
                    break;
                }
            }
        }
        return x;
    }
    public static boolean checkNum(String number) {
        String rex = "^[0-9]\\d*(\\.\\d+)?$";
        Pattern p = Pattern.compile(rex);
        Matcher m = p.matcher(number);
        return m.find();
    }
    //获取变量的值
    public static String getVariable(String str){
        String res=str;
        while (true){
            if(res.contains("[@<")&&res.contains(">]")||res.contains("[$(")&&res.contains(")]")){
                String index_str = WarmaUtils.getString(res,"[","]").trim();
                String name = getVariableValue(index_str);
                res=res.replace(index_str,name);
            }else if(res.contains("@<")&&res.contains(">")){
                //获取变量名
                String name = WarmaUtils.getString(res,"@<",">").trim();
                String value;
                if(name.contains("[")&&name.contains("]")){
                    String index_str=WarmaUtils.getString(res,"[","]").trim();
                    //获取下标
                    int index = Integer.parseInt(index_str);
                    //获取数组变量名
                    String arrayName=WarmaUtils.getString(res,"@<","[");

                    Map<String, Object> val= WarmaObjects.get(arrayName);
                    //获取数组中的值
                    String[] arrays=(String[])val.get("value");
                    value=arrays[index];
                }else{
                    Map<String, Object> val= WarmaObjects.get(name);
                    //获取变量的值
                    value=val.get("value").toString();
                }
                res=res.replace("@<"+name+">",value);
            }else{
                break;
            }
        }
        return res;
    }
    //获取四则运算
    public static String getCount(String str){
        String res=str;
        while (true){
            if(res.contains("$(")&&res.contains(")")){
                String value = WarmaUtils.Regex("(?<=\\$\\()[^)]*(?=\\))",str);

                char[] chars = value.toCharArray();
                for(char ch:chars){
                    if(!checkNum(String.valueOf(ch))&&ch!='+'&&ch!='-'&&ch!='*'&&ch!='/'&&ch!='('&&ch!=')'&&ch!='.'){
                        //不是数字或者运算符直接返回
                        return str;
                    }
                }
                String x= CountUtils.count(value);
                res=res.replace("$("+value+")",x);
            }else{
                break;
            }
        }
        return res;
    }
    public static String getVariableValue(String value){
        value=WarmaUtils.getVariable(value);
        if(value.contains("$(")&&value.contains(")")&&!value.contains("@<")){
            value=WarmaUtils.getCount(value);
        }
        return value;
    }
    public static String[] insert(String[] array,String str){
        int size=array.length;
        String[] arr=new String[size+1];
        //数组复制
        System.arraycopy(array, 0, arr, 0, array.length);
        arr[size]=str;
        return arr;
    }
    //读取文件
    public static String ReadTextFile(String path){
        try {
            FileInputStream in = new FileInputStream(path);
            int lenght;
            byte[] data=new byte[1024];
            ByteArrayOutputStream out=new ByteArrayOutputStream();
            while((lenght=in.read(data))!=-1){
                out.write(data,0,lenght);
            }
            return new String(out.toByteArray(), StandardCharsets.UTF_8);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    //正则表达式
    public static String Regex(String regex,String str) {
        String string="";
        Matcher mat= Pattern.compile(regex).matcher(str);
        while(mat.find()) {
            string=mat.group(0);
        }
        return string;
    }
    //赋值
    public static void Assignment(String assignName,Object value,String type){
        Map<String, Object> m = WarmaObjects.WarmaMap();
        m.put("value",value);
        m.put("type",type);
        WarmaObjects.set(assignName,m);
    }
}
