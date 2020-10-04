package com.japl.Warma;

import com.japl.Utils.WarmaObjects;
import com.japl.Utils.WarmaUtils;

import java.io.File;

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
                i=new Loop().Start(code,i);
            }else if(str.contains("变量")&&str.contains("@")&&str.contains("=")&&!str.contains("#")){
                //变量赋值
                new Variable().Assign(str);
            }else if (str.contains("输出(")&&str.contains(");")){
                //输出语句
                new Print(str);

            }else if(str.contains("如果")&&str.contains("){")){

                //分支语句1
                if(str.contains("如果(")){
                    i=new Branch().Start(code,i,true);
                }else if(str.contains("如果不是(")){
                    i=new Branch().Start(code,i,false);
                }

            }else if(str.contains("}否则{")){
                //分支语句2
                i=WarmaUtils.getEndRow(code,i,new String[]{"}否则{"},new String[]{"};"});
            }else if(str.contains("函数")&&str.contains("(")&&str.contains("){")){
                //函数
                i=WarmaUtils.getFunctionEnd(code,i);

            }else if(str.contains("#")&&str.contains("(")&&str.contains(");")){
                //函数调用
                new Function(code,i);
            }else if(str.contains("调用jar包(\"")&&str.contains("\");")){
                //通过反射调用jar包
                i=new JavaReflection().run(code,i);
            }
        }
    }
    public static void run(String path){
        //设置当前脚本路径
        WarmaObjects.ClassPath=new File(path).getParent();
        //读取脚本
        String command =WarmaUtils.ReadTextFile(path);
        assert command != null;
        //执行脚本
        Warma.execute(command);
    }
}
