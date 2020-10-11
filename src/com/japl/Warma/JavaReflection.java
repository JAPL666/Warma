package com.japl.Warma;

import com.japl.Utils.WarmaUtils;
import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;

public class JavaReflection {
    //通过反射调用jar包并获取返回值
    public int run(String[] code,int index){
        //解析参数
        String jarFile = WarmaUtils.getString(code[index], "调用jar包(\"", "\");");
        String className = WarmaUtils.getString(code[index+1], "加载类(\"", "\");");
        String methodName = WarmaUtils.getString(code[index+2], "调用方法(\"", "\");");
        String parameter = WarmaUtils.getString(code[index+3], "输入参数(", ");");
        String type = WarmaUtils.getString(code[index+4], "参数类型(", ");");
        String variable = WarmaUtils.getString(code[index+5], "调用结果(\"@<", ">\");");

        String[] parameters=parameter.split(",");
        //参数数组
        Object[] par=new Object[parameters.length];

        String[] types=type.split(",");
        //类型数组
        Class<?>[] class_type=new Class[types.length];

        if(code[index+3].contains("输入参数()")){
            //无参数
            par=new Object[0];
            class_type=new Class[0];
        }

        for(int i=0;i<types.length;i++){
            String str=WarmaUtils.getVariableValue(parameters[i]);
            if(str.equals("")){
                //空内容跳过
                break;
            }
            //分配类型
            switch (types[i]){
                case "字符串":
                    class_type[i]=String.class;
                    //设置值
                    par[i]=str;
                    break;
                case "整型":
                    class_type[i]=int.class;

                    par[i]=Integer.parseInt(str);
                    break;
                case "浮点":
                    class_type[i]=float.class;

                    par[i]=Float.parseFloat(str);
                    break;
                case "布尔":
                    class_type[i]=boolean.class;

                    if(str.equals("真")){
                        par[i]=true;
                    }else{
                        par[i]=false;
                    }
                    break;
            }
        }

        try {
            //调用jar包
            File file = new File(jarFile);//jar包的路径
            URL url = file.toURI().toURL();
            URLClassLoader loader = new URLClassLoader(new URL[]{url});//创建类加载器
            Class<?> cls = loader.loadClass(className);//加载指定类
            Object instance = cls.getDeclaredConstructor().newInstance();
            Method method = cls.getMethod(methodName,class_type);
            Object out = method.invoke(instance,par);

            //返回值存入变量
            WarmaUtils.Assignment(variable,out.toString(),"变量");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return index+5;
    }
}
