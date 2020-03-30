package com.ccq.test;

import org.csource.common.MyException;
import org.csource.common.NameValuePair;
import org.csource.fastdfs.*;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;

public class fdfstest {


    //成员变量
    TrackerServer trackerServer = null;
    StorageServer storageServer = null;
    StorageClient storageClient = null;
    /*public static void main(String[] args) throws IOException, MyException {
        // 加载配置文件
        ClientGlobal.initByProperties("config/fastdfs-client.properties");
        System.out.println("ClientGlobal.configInfo():" + ClientGlobal.configInfo());
    }*/

    @Before
    public void init() throws Exception {
        // 加载配置文件
        ClientGlobal.initByProperties("config/fastdfs-client.properties");
        // 获取连接
        TrackerClient trackerClient = new TrackerClient();
        trackerServer = trackerClient.getTrackerServer();
        storageClient = new StorageClient(trackerServer, storageServer);
    }


    @Test
    public void uploadFileOfByte() throws Exception {
        // 获取文件字节信息
        File file = new File("C:\\Users\\sun\\Pictures\\Saved Pictures\\e53c868ee9e8e7b28c424b56afe2066d.jpg");
        FileInputStream inputStream = new FileInputStream(file);
        byte[] file_buff = new byte[(int) file.length()];
        inputStream.read(file_buff);
        // 获取文件扩展名
        String fileName = file.getName();
        String extName = null;
        if (fileName.contains(".")) {
            extName = fileName.substring(fileName.lastIndexOf(".") + 1);
        } else {
            return;
        }
        // 图片元数据,如果设置为空，那么服务器上不会生成-m的原数据文件
        NameValuePair[] meta_list = new NameValuePair[2];
        meta_list[0] = new NameValuePair("fileName", "测试专用");
        meta_list[1] = new NameValuePair("length", "测试专用");
        // 文件上传，返回组名和访问地址
        String[] upload_file = storageClient.upload_file(file_buff, extName, meta_list);
        System.out.println(Arrays.asList(upload_file));
    }
}
