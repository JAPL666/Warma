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

            if(str.contains("注释:")){
                //注释
                continue;
            }

            if(str.contains("循环")&&str.contains("次{")){
                //循环
                Loop(code,i);
            }else if(str.contains("变量")&&str.contains("@")&&str.contains("=")){
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
                //i=getEndRow(code,i,new String[]{"函数","(","){"},new String[]{"返回","(",");"});
                i=getFunctionEnd(code,i);

            }else if(str.contains("#")&&str.contains("(")&&str.contains(");")){
                //函数调用
                Function(code,i);
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
        String name = getString(str,"变量","=");
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

        if (bool){
            return isTrue(expression,index,code,'真');
        }else{
            return isTrue(expression,index,code,'假');
        }
    }
    public static int isTrue(String expression,int index,String[] code,char c){
        int x=0;
        if(expression.equals(String.valueOf(c))){
            x=index;
        }else{
            for (int i=index;i<code.length;i++){
                if(code[i].contains("}否则{")){
                    x=i;
                    break;
                }
            }
        }
        return x;
    }
    //函数
    public static void Function(String[] code,int index){
        String str=code[index];
        boolean bool=false,b=true;
        StringBuilder builder=new StringBuilder();
        String name=getString(str,"#","(");

        for (int i=0;i<code.length;i++){

            if(code[i].contains("函数 "+name+"(")&&code[i].contains("(")&&code[i].contains("){")){
                bool=true;
                if(str.contains("()")){
                    //无参数
                }else{
                    //输入的参数
                    String[] values=getString(str,"(",");").split(",");
                    //参数变量名
                    String[] Assign_values=getString(code[i],"(","){").split(",");
                    for (int j=0;j<values.length;j++){
                        Assign_Type(values[j],Assign_values[j]);
                    }
                }
            }
            if(bool){
                if(code[i].contains("}返回(")&&code[i].contains("(")&&code[i].contains(");")){
                    if(code[i].contains("}返回();")){
                        //返回值为空
                        //System.out.println("返回值为空");
                    }else{
                        String assing=getString(code[i],"}返回(",");");
                        Map<String, Object> m = WarmaObjects.WarmaMap();
                        m.put("value",assing);
                        m.put("type","字符串");
                        WarmaObjects.set(name,m);
                    }
                    break;
                }
                if(b){
                    //第一次运行，不执行
                    b=false;
                }else{
                    builder.append(code[i].trim()).append("\n");
                }
            }
        }
        //执行
        execute(builder.toString());
    }
    public static void Assign_Type(String str,String assign){
        if(str.contains("\"")){
            Map<String, Object> m = WarmaObjects.WarmaMap();
            m.put("value",str.replace("\"",""));
            m.put("type","函数返回值");
            WarmaObjects.set(assign,m);
        }
    }
    /*-------------------------------------------------分割线---------------------------------------------------------*/
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
    public static int getFunctionEnd(String[] code,int index){
        String name=getString(code[index],"函数","(");
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
    //获取指定范围的字符串
    public static String getString(String str,String start,String end){
        return  str.substring(str.indexOf(start)+start.length(),str.indexOf(end)).trim();
    }
}
