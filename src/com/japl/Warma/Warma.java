package com.japl.Warma;

import com.japl.Utils.WarmaObjects;
import java.util.Map;

public class Warma {
    //执行
    public static void execute(String command){
        assert command != null;
        String[] code = command.split("\n");

        for (int i=0;i<code.length;i++){
            String str=code[i];

            if(str.equals("")){
                //空行跳过
                continue;
            }

            if(str.trim().charAt(0)=='/'&&str.trim().charAt(1)=='/'){
                //注释
                continue;
            }

            if(str.contains("循环")&&str.contains("次{")){
                //循环
                Loop(code,i);
            }else if(str.contains("@")&&str.contains("=")){
                //变量赋值
                Assign(str);

            }else if(str.contains("@<")&&str.contains(">")){
                //变量
                Variable(str);
            }else if (str.contains("输出(")){
                //输出语句
                System.out.println(getString(str,"输出(\"","\");"));

            }else if(str.contains("如果")){
                //分支语句1
                if(str.contains("如果(")){
                    i=Branch(code,i,true);
                }else if(str.contains("如果不是(")){
                    i=Branch(code,i,false);
                }
            }else if(str.contains("}否则{")){
                //分支语句2
                i=getEndRow(code,i,new String[]{"}否则{"},new String[]{"};"});
            }else if(str.contains("函数")&&str.contains("(")&&str.contains("){")){
                //函数
                i=getEndRow(code,i,new String[]{"函数","(","){"},new String[]{"返回","(",");"});
            }else if(str.contains("#")&&str.contains("(")&&str.contains(");")){
                //函数调用

            }
        }
    }
    /*-------------------------------------------------分割线---------------------------------------------------------*/
    //循环
    public static void Loop(String[] code,int index){
        String str=code[index];
        int x=Integer.parseInt(getString(str,"循环","次"));

        for(int k=0;k<x-1;k++){
            StringBuilder c= new StringBuilder();
            int deep=0;
            int j;
            boolean bool=true;
            for(j=index;j<code.length;j++){
                if(code[j].contains("循环")&&code[j].contains("次{")){
                    deep++;
                }else{
                    if(code[j].contains("}循环结束;")){
                        deep--;
                        if(deep==0){
                            break;
                        }
                    }
                }
                if(bool){
                    bool=false;
                }else{
                    c.append(code[j].trim()).append("\n");
                }
            }
            //执行代码
            execute(c.toString());
        }
    }
    //变量赋值
    public static void Assign(String str){
        String name = getString(str,"字符串","=");
        String value = getString(str,name+"=\"","\";");

        String type = str.split(" ")[0].trim();

        Map<String, Object> m = WarmaObjects.WarmaMap();
        m.put("value",value);
        m.put("type",type);
        WarmaObjects.set(name.replace("@",""),m);
    }
    //变量
    public static void Variable(String str){
        String value = getString(str,"@<",">").trim();
        Map<String, Object> val=WarmaObjects.get(value);
        execute(str.replace("@<"+value+">",val.get("value").toString()));
    }
    //分支语句
    public static int Branch(String[] code,int index,boolean bool){
        String str=code[index];
        String expression = getString(str,"如果(",")");

        int x=0;
        if(expression.equals("真")){
            x=index;
        }else{
            for (int i=index;i<code.length;i++){
                if(code[i].contains("}否则{")){
                    x=i;
                    break;
                }
            }
        }
        return  x;
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
    //获取指定范围的字符串
    public static String getString(String str,String start,String end){
        char[] s=str.toCharArray();
        char[] s1=start.toCharArray();
        char[] s2=end.toCharArray();
        int a=0,b=0;

        int index=0;
        boolean bool=true;
        for (int i=0;i<s.length;i++){
            if(bool){
                if(s1.length==1){
                    a=str.indexOf(start);
                    continue;
                }
                if(s[i]==s1[index]){
                    index++;
                    if(index>s1.length-1){
                        index=0;
                        bool=false;
                        a=i+1;
                    }
                }
            }else{
                if(s2.length==1){
                    b=str.indexOf(end);
                    continue;
                }
                if(s[i]==s2[index]){
                    index++;
                    if(index>s2.length-1){
                        b=i-(s2.length-1);
                    }
                }
            }
        }
        return  str.substring(a,b).trim();
    }
}
