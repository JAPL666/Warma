package com.japl;

import com.japl.Warma.Warma;

import java.io.File;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner  scanner=new Scanner(System.in);
        String path=scanner.next();
        if(new File(path).exists()&&path.contains(".warma")){
            Warma.run(path);
        }else{
            System.out.println("找不到可执行脚本！");
        }
    }
}
