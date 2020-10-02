package com.japl;

import com.japl.Utils.WarmaUtils;
import com.japl.Warma.Warma;

public class Main {

    public static void main(String[] args) {
        String command =WarmaUtils.ReadTextFile("A:\\Project\\IDEA\\Warma\\a.warma");
        assert command != null;
        Warma.execute(command);
    }
}
