package config;


import resources.Paths;
import resources.Ports;

import javax.swing.*;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class MainConfig {

    String mainPath = "src\\main\\resources\\config\\mainConfig\\mainConfigFile";

    public MainConfig() throws IOException {
        setConfig();
    }

    private void setConfig() throws IOException {
        Properties mainProperties = new Properties();
        Properties portProperty = new Properties();
        Properties pathsProperties = new Properties();
        FileReader reader = new FileReader(mainPath);
        mainProperties.load(reader);
        String paths = (String) mainProperties.get("paths");
        FileReader reader2 = new FileReader(paths);
        pathsProperties.load(reader2);
        //
        Paths.CHAT_PATH =(String)pathsProperties.get("CHAT_PATH");
        Paths.CHAT_PATH_ID_COUNTER =(String)pathsProperties.get("CHAT_PATH_ID_COUNTER");
        Paths.CHAT_IMAGE_PATH =(String)pathsProperties.get("CHAT_IMAGE_PATH");
        Paths.MESSAGE_PATH =(String)pathsProperties.get("MESSAGE_PATH");
        Paths.TWEET_PATH =(String)pathsProperties.get("TWEET_PATH");
        Paths.COMMENT_PATH =(String)pathsProperties.get("COMMENT_PATH");
        Paths.MESSAGE_PATH_ID_COUNTER =(String)pathsProperties.get("MESSAGE_PATH_ID_COUNTER");
        Paths.TWEET_IMAGE_PATH =(String)pathsProperties.get("TWEET_IMAGE_PATH");
        Paths.TWEET_PATH_GET_ALL =(String)pathsProperties.get("TWEET_PATH_GET_ALL");
        Paths.MESSAGE_PATH_GET_ALL =(String)pathsProperties.get("MESSAGE_PATH_GET_ALL");
        Paths.COMMENT_PATH_GET_ALL =(String)pathsProperties.get("COMMENT_PATH_GET_ALL");
        Paths.USER_PATH_GET_ALL =(String)pathsProperties.get("USER_PATH_GET_ALL");
        Paths.REPORT_PATH =(String)pathsProperties.get("REPORT_PATH");
        Paths.REPORT_PATH_ID_COUNTER =(String)pathsProperties.get("REPORT_PATH_ID_COUNTER");
        Paths.REQUEST_PATH =(String)pathsProperties.get("REQUEST_PATH");
        Paths.REQUEST_PATH_ID_COUNTER =(String)pathsProperties.get("REQUEST_PATH_ID_COUNTER");
        Paths.USER_PATH =(String)pathsProperties.get("USER_PATH");
        Paths.USER_PATH_ID_COUNTER =(String)pathsProperties.get("USER_PATH_ID_COUNTER");
        Paths.USER_IMAGE_PATH =(String)pathsProperties.get("USER_IMAGE_PATH");
        //
        String portFilePath = (String) mainProperties.get("portsPath");
        portProperty.load(new FileReader(portFilePath));
        Ports.MAIN_PORT =Integer.parseInt(
                (String) portProperty.get("MAIN_PORT"));
    }
}
