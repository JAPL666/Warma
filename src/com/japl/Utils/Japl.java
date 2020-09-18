package com.japl.Utils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.GZIPInputStream;

public class Japl {
    public static String POST(String url,String string,String Cookies) {
        try {
            URL url2=new URL(url);
            HttpURLConnection connection=(HttpURLConnection)url2.openConnection();
            connection.setRequestMethod("POST");
            connection.addRequestProperty("Connection", "keep-alive");
            connection.addRequestProperty("Pragma", "no-cache");
            connection.addRequestProperty("content-type", "application/x-www-form-urlencoded");
            connection.addRequestProperty("Accept-Language", "zh-CN,en-US;q=0.8");
            connection.addRequestProperty("Sec-Fetch-Dest","empty");
            connection.addRequestProperty("Sec-Fetch-Mode","cors");
            connection.addRequestProperty("Sec-Fetch-Site","same-site");
            connection.addRequestProperty("Cookie", Cookies);

            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.connect();

            byte[] data=string.getBytes();
            connection.getOutputStream().write(data);
            int code=connection.getResponseCode();
            if(code==200){
                InputStream is = connection.getInputStream();
                ByteArrayOutputStream message =new ByteArrayOutputStream();
                int lenght;
                byte[] buffer =new byte[1024];
                while((lenght=is.read(buffer))!=-1) {
                    message.write(buffer,0,lenght);
                }
                is.close();
                message.close();
                return new String(message.toByteArray(), StandardCharsets.UTF_8);
            }else{
                System.out.println(code);
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
        return "null";
    }
    public static String Regex(String regex,String str) {
        String string="";
        Matcher mat= Pattern.compile(regex).matcher(str);
        while(mat.find()) {
            string=mat.group(1);
        }
        return string;
    }
    public static String GET(String Url, String cookies){
        try {
            URL url=new URL(Url);
            HttpURLConnection connection=(HttpURLConnection)url.openConnection();
            connection.setRequestMethod("GET");
            connection.addRequestProperty("Cookie", cookies);

            connection.addRequestProperty("Connection", "keep-alive");
            connection.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/83.0.4103.116 Safari/537.36");
            connection.addRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
            connection.addRequestProperty("Accept-Language", "zh-CN,zh;q=0.8,zh-TW;q=0.7,zh-HK;q=0.5,en-US;q=0.3,en;q=0.2");
            connection.addRequestProperty("Upgrade-Insecure-Requests", "1");
            connection.addRequestProperty("Cache-Control", "max-age=0");

            connection.connect();

            InputStream is = connection.getInputStream();

            String encoding = connection.getContentEncoding();
            if(encoding!=null){
                //判断是否gzip
                if(encoding.equals("gzip")){
                    is=new GZIPInputStream(is);
                }
            }
            ByteArrayOutputStream message =new ByteArrayOutputStream();
            int lenght;
            byte[] buffer =new byte[1024];
            while((lenght=is.read(buffer))!=-1) {
                message.write(buffer,0,lenght);
            }
            is.close();
            message.close();
            return new String(message.toByteArray(), StandardCharsets.UTF_8);

        }catch (IOException e) {
            e.printStackTrace();
        }
        return "null";
    }
    public static int Random(int s,int d){
        return (int)(Math.random()*(d-s)+s);
    }
    public static String getText(String path){
        try {
            FileInputStream in = new FileInputStream(path);
            int lenght;
            byte[] data=new byte[1024];
            ByteArrayOutputStream out=new ByteArrayOutputStream();
            while((lenght=in.read(data))!=-1){
                out.write(data,0,lenght);
            }
            return new String(out.toByteArray(), StandardCharsets.UTF_8);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
