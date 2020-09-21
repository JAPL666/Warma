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
                    i=Branch(code,i,true);
                }else if(str.contains("如果不是(")){
                    i=Branch(code,i,false);
                }
            }else if(str.contains("}否则{")){
                i=getEndRow(code,i,new String[]{"}否则{"},new String[]{"};"});
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
    public static int Branch(String[] code,int index,boolean bool){
        String str=code[index];
        String expression = Japl.Regex("如果\\((.+?)\\)", str).trim();

        int x=0;
        if(expression.equals("真")){
            x=index;
            int x1=getEndRow(code,index,new String[]{"如果(","){"},new String[]{"};"});
//            System.out.println("当前行数："+index);
//            System.out.println("结束行数："+x1);
//            System.out.println("目标行："+code[x1]);
        }else{
            for (int i=index;i<code.length;i++){
                if(code[i].contains("}否则{")){
                    int x1=getEndRow(code,i,new String[]{"}否则{"},new String[]{"};"});
//                    System.out.println("当前行数："+i);
//                    System.out.println("结束行数："+x1);
//                    System.out.println("目标行："+code[x1]);
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
}
