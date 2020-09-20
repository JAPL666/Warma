package com.japl.Utils;

import java.util.Map;

public class Warma {
    public static void execute(String command){
        assert command != null;
        String[] sp = command.split("\n");
		
        for (int i=0;i<sp.length;i++){
			
            if(sp[i].contains("循环")){
                int x=Integer.parseInt(Japl.Regex("循环([^\"]+)次",sp[i]).trim());
                for(int k=0;k<x-1;k++){
					StringBuilder c= new StringBuilder();
					int deep=0;
                    int j;
                    boolean bool=true;
                    for(j=i;j<sp.length;j++){

                        if(sp[j].contains("{")){
                            deep++;
                        }else{
                            if(sp[j].contains("}")){
                                deep--;
                                if(deep==0){
                                    break;
                                }
                            }
                        }
                        if(bool){
                            bool=false;
                        }else{
                            c.append(sp[j].trim()).append("\n");
                        }
                    }
					execute(c.toString());
//					System.out.println("============");
//					System.out.println(c);
//					System.out.println("============");
                }
            }else if(sp[i].contains("@")&&sp[i].contains("=")){
                String name = Japl.Regex("字符串([^\"]+)=", sp[i]).trim();
                String value = Japl.Regex(name+"=\"([^\"]+)\";", sp[i]).trim();
                String type = sp[i].split(" ")[0].trim();

                Map<String, Object> m = WarmaObjects.WarmaMap();
                m.put("value",value);
                m.put("type",type);
                WarmaObjects.set(name.replace("@",""),m);
            }else if(sp[i].contains("@")){
                String value = Japl.Regex("@\\<(.+?)\\>", sp[i]).trim();
				Map<String, Object> val=WarmaObjects.get(value);
				execute(sp[i].replace("@<"+value+">",val.get("value").toString()));
            }else if (sp[i].contains("输出")){
				System.out.println(Japl.Regex("输出\\(\"([^\"]+)\"\\);", sp[i]).trim());
			}
        }
    }
}
