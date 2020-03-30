package com.ccq.utils;

import org.csource.common.MyException;
import org.csource.common.NameValuePair;
import org.csource.fastdfs.*;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.io.IOException;
public class FastDfsUtils {


    private TrackerClient trackerClient;
    private TrackerServer trackerServer;
    private StorageServer storageServer;


    public FastDfsUtils(String configlocation) throws IOException, MyException {

       /* if(configlocation.startsWith("classpath:")){
            configlocation = configlocation.replace("classpath:", this.getClass().getResource("/").getPath());
        }*/
        ClientGlobal.init(configlocation);
        trackerClient = new TrackerClient();
        trackerServer = trackerClient.getTrackerServer();
    }

    public String fileUpLoad(byte[] bytes, String file_end) throws IOException, MyException {
        return  fileUpLoad(bytes,file_end,null);
    }

    public String fileUpLoad(byte[] bytes, String file_end, NameValuePair[] nameValuePairs) throws IOException, MyException {
        StorageClient1 storageClient1 = new StorageClient1(trackerServer,storageServer);
        String[] strings = storageClient1.upload_file(bytes, file_end, nameValuePairs);
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < strings.length; i++) {
            stringBuffer.append(strings[i]);
            if(i==0){
                stringBuffer.append("/");
            }
        }
        return stringBuffer.toString();


    }

}

