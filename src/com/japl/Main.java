package com.japl;

import com.japl.Utils.Japl;
import com.japl.Utils.*;

public class Main {
	//\\{([\\s\\S]*)\\}
	//正则表达式匹配花括号内容
    public static void main(String[] args) {
        String command=Japl.getText("A:\\Project\\IDEA\\Warma\\a.warma");
		Warma.execute(command);
		//String str=Japl.Regex("(^[^}]*\\})",command);
		//System.out.println(str);
    }
}
