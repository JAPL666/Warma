package com.japl.Warma;

import com.japl.Utils.Japl;
import com.japl.Utils.WarmaObjects;

import java.util.Map;

public class Warma {
    //执行
    public static void execute(String command){
        assert command != null;
        String[] code = command.split("\n");
		
        for (int i=0;i<code.length;i++){
			String str=code[i];

            if(str.contains("循环")&&str.contains("次")){
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
				System.out.println(Japl.Regex("输出\\(\"([^\"]+)\"\\);", code[i]).trim());
			}else if(str.contains("如果")){
                //分支语句
                if(str.contains("如果(")){
                    Branch(code,i,true);
                }else if(str.contains("如果不是(")){
                    Branch(code,i,false);
                }

            }
        }
    }
    /*-------------------------------------------------分割线---------------------------------------------------------*/
    //循环
    public static void Loop(String[] code,int index){
        String str=code[index];
        int x=Integer.parseInt(Japl.Regex("循环([^\"]+)次",str).trim());
        for(int k=0;k<x-1;k++){
            StringBuilder c= new StringBuilder();
            int deep=0;
            int j;
            boolean bool=true;
            for(j=index;j<code.length;j++){
                if(code[j].contains("循环")&&code[j].contains("次{")){
                    deep++;
                }else{
                    if(code[j].contains("}")){
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
            execute(c.toString());
        }
    }
    //变量赋值
    public static void Assign(String str){
        String name = Japl.Regex("字符串([^\"]+)=", str).trim();
        String value = Japl.Regex(name+"=\"([^\"]+)\";", str).trim();
        String type = str.split(" ")[0].trim();

        Map<String, Object> m = WarmaObjects.WarmaMap();
        m.put("value",value);
        m.put("type",type);
        WarmaObjects.set(name.replace("@",""),m);
    }
    //变量
    public static void Variable(String str){
        String value = Japl.Regex("@\\<(.+?)\\>", str).trim();
        Map<String, Object> val=WarmaObjects.get(value);
        execute(str.replace("@<"+value+">",val.get("value").toString()));
    }
    //分支语句
    public static void Branch(String[] code,int index,boolean bool){
        String str=code[index];
        String expression = Japl.Regex("如果\\((.+?)\\)", str).trim();
        System.out.println(expression);
        if(str.contains("与")){
            String[] values=expression.split("与");
            boolean b=false;
            for (String value:values){
                System.out.println(value);
                b=true;
            }
            if(b){
                StringBuilder c= new StringBuilder();
                int deep=0;
                int j;
                int Row=0;
                boolean start=true;
                for(j=index;j<code.length;j++) {
                    if (code[j].contains("){")) {
                        deep++;
                    } else {
                        if(code[j].contains("}否则{")){
                            deep--;
                            if (deep == 0) {
                                Row=j;
                                break;
                            }
                        }
                    }
                    if (start) {
                        start = false;
                    } else {
                        c.append(code[j].trim()).append("\n");
                        System.out.println(">"+code[j]);
                    }
                }
                System.out.println("当前行数："+index);
                int x1=getEndRow(code,index,new String[]{"){"},new String[]{"}否则{"});
                System.out.println("目标行数："+x1);
                System.out.println("目标"+code[x1]);

                int x2=getEndRow(code,index,new String[]{code[x1]},new String[]{"};"});
                System.out.println("结束行："+x2);
                index=x2;
                execute(c.toString());
            }else{

            }
        }else if(str.contains("或者")){

        }else{

        }
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
}
