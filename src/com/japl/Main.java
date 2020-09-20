package com.japl;

import com.japl.Utils.Japl;
import com.japl.Utils.*;

public class Main {

    public static void main(String[] args) {
        String command = Japl.getText("A:\\Project\\IDEA\\Warma\\a.warma");
        assert command != null;
        Warma.execute(command);
    }
}
