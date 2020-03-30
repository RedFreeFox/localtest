package com.sun.sqlvalue.sqlvalue;

import cn.edu.jxnu.base.core.util.AESEncrypt;
import org.junit.Test;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

@SpringBootApplication
public class SqlvalueApplication {

    public static void main(String[] args) {
        //SpringApplication.run(SqlvalueApplication.class, args);
        String message = "";
        try {
            InputStream in = new FileInputStream(args[0]);
            Properties properties = new Properties();
            Connection con = null;
            properties.load(in);
            String url = properties.getProperty("jdbc.url");
            System.out.print(properties.getProperty("jdbc.driverClassName"));
            String username = AESEncrypt.decrypt(properties.getProperty("jdbc.username"), "");
            String password = AESEncrypt.decrypt(properties.getProperty("jdbc.password"), "");
            Class.forName(properties.getProperty("jdbc.driverClassName"));
            con = DriverManager.getConnection(url, username, password);
            con.close();
            message = "0";
        } catch (Exception e) {
            message = "1";
        }
        try {
            File file = new File(args[1]);
            OutputStream out = new FileOutputStream(file);
            /*if(file.exists()){
                file.delete();
            }*/
            OutputStreamWriter oWriter = new OutputStreamWriter(out, "UTF-8");
            oWriter.write(message, 0, message.length());
            oWriter.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
