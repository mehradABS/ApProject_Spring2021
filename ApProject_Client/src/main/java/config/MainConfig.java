package config;

import resources.Images;
import resources.Paths;
import resources.ServerAddress;

import javax.swing.*;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class MainConfig {

    String mainPath = "src\\main\\resources\\config\\mainConfig\\mainConfigFile";

    public MainConfig() throws IOException {
        setConfigs();
    }

    private void setConfigs() throws IOException {
        Properties mainProperties = new Properties();
        Properties portProperty = new Properties();
        Properties pathsProperties = new Properties();
        Properties imagePathProperties = new Properties();
        FileReader reader = new FileReader(mainPath);
        mainProperties.load(reader);
        String imagePath = (String) mainProperties.get("imagePath");
        String paths = (String) mainProperties.get("paths");
        FileReader reader1 = new FileReader(imagePath);
        FileReader reader2 = new FileReader(paths);
        pathsProperties.load(reader2);
        imagePathProperties.load(reader1);
        //
        Images.START_PANEL_IMAGE = new
                ImageIcon((String)imagePathProperties.get("START_PANEL_IMAGE"))
                .getImage();
        Images.PROFILE_ICON = new ImageIcon((String)imagePathProperties.
                get("PROFILE_ICON"));
        Images.NEW_TWEET_ICON = new ImageIcon(
                (String)imagePathProperties.get("NEW_TWEET_ICON")
        );
        Images.TIMELINE_ICON = new ImageIcon(
                (String)imagePathProperties.get("TIMELINE_ICON"));
        Images.INFO_ICON  = new ImageIcon(
                (String)imagePathProperties.get("INFO_ICON"));

        Images.SELECT_PHOTO_ICON = new ImageIcon(
                (String)imagePathProperties.get("SELECT_PHOTO_ICON"));

        Images.TWEET_HISTORY_ICON = new ImageIcon(
                (String)imagePathProperties.get("TWEET_HISTORY_ICON"));

        Images.CLOSE_ICON = new ImageIcon(
                (String)imagePathProperties.get("CLOSE_ICON"));

        Images.BACK_ICON = new ImageIcon(
                (String)imagePathProperties.get("BACK_ICON"));

        Images.LIKE_ICON = new ImageIcon(
                (String)imagePathProperties.get("LIKE_ICON"));

        Images.DISLIKE_ICON = new ImageIcon(
                (String)imagePathProperties.get("DISLIKE_ICON"));

        Images.REPLY_ICON = new ImageIcon(
                (String)imagePathProperties.get("REPLY_ICON"));

        Images.SHARE_ICON = new ImageIcon(
                (String)imagePathProperties.get("SHARE_ICON"));

        Images.BLOCK_ICON = new ImageIcon(
                (String)imagePathProperties.get("BLOCK_ICON"));

        Images.REPORT_ICON = new ImageIcon(
                (String)imagePathProperties.get("REPORT_ICON"));

        Images.MUTE_ICON = new ImageIcon(
                (String)imagePathProperties.get("MUTE_ICON"));

        Images.UNBLOCK_ICON = new ImageIcon(
                (String)imagePathProperties.get("UNBLOCK_ICON"));

        Images.RETWEET_ICON = new ImageIcon(
                (String)imagePathProperties.get("RETWEET_ICON"));

        Images.SEARCH_ICON = new ImageIcon(
                (String)imagePathProperties.get("SEARCH_ICON"));

        Images.SEARCH_ICON_SMALL = new ImageIcon(
                (String)imagePathProperties.get("SEARCH_ICON_SMALL"));

        Images.EXPLORE_ICON = new ImageIcon(
                (String)imagePathProperties.get("EXPLORE_ICON"));

        Images.MESSAGE_ICON = new ImageIcon(
                (String)imagePathProperties.get("MESSAGE_ICON"));

        Images.UNMUTE_ICON = new ImageIcon(
                (String)imagePathProperties.get("UNMUTE_ICON"));

        Images.MESSAGE_BIG_ICON = new ImageIcon(
                (String)imagePathProperties.get("MESSAGE_BIG_ICON"));

        Images.SEND_ICON = new ImageIcon(
                (String)imagePathProperties.get("SEND_ICON"));

        Images.ADD_LIST = new ImageIcon(
                (String)imagePathProperties.get("ADD_LIST"));

        Images.REMOVE_LIST = new ImageIcon(
                (String)imagePathProperties.get("REMOVE_LIST"));

        Images.LISTS = new ImageIcon(
                (String)imagePathProperties.get("LISTS"));

        Images.ADD_PERSON = new ImageIcon(
                (String)imagePathProperties.get("ADD_PERSON"));

        Images.REMOVE_PERSON = new ImageIcon(
                (String)imagePathProperties.get("REMOVE_PERSON"));

        Images.SETTING = new ImageIcon(
                (String)imagePathProperties.get("SETTING"));

        Images.SETTING1 = new ImageIcon(
                (String)imagePathProperties.get("SETTING1"));

        Images.NOTIFICATION = new ImageIcon(
                (String)imagePathProperties.get("NOTIFICATION"));

        Images.ACCEPT = new ImageIcon(
                (String)imagePathProperties.get("ACCEPT"));

        Images.DELETE = new ImageIcon(
                (String)imagePathProperties.get("DELETE"));

        Images.LOGOUT = new ImageIcon(
                (String)imagePathProperties.get("LOGOUT"));

        Images.CONNECTION = new ImageIcon(
                (String)imagePathProperties.get("CONNECTION"));

        Images.WIFI_OFF = new ImageIcon(
                (String)imagePathProperties.get("WIFI_OFF"));

        Images.WIFI_ON = new ImageIcon(
                (String)imagePathProperties.get("WIFI_ON"));

        Images.BOT = new ImageIcon(
                (String)imagePathProperties.get("BOT"));
        //
        Paths.CHAT_IMAGE_PATH =(String)pathsProperties.get("CHAT_IMAGE_PATH");
        Paths.CHAT_DEFAULT_IMAGE_PATH =(String)pathsProperties.get("CHAT_DEFAULT_IMAGE_PATH");
        Paths.TWEET_IMAGE_PATH =(String)pathsProperties.get("TWEET_IMAGE_PATH");
        Paths.USER_IMAGE_PATH =(String)pathsProperties.get("USER_IMAGE_PATH");
        Paths.USER_DEFAULT_IMAGE_PATH1 =(String)pathsProperties.get("USER_DEFAULT_IMAGE_PATH1");
        Paths.USER_DEFAULT_IMAGE_PATH2 =(String)pathsProperties.get("USER_DEFAULT_IMAGE_PATH2");
        Paths.MESSAGE_PATH =(String)pathsProperties.get("MESSAGE_PATH");
        Paths.TWEET_PATH =(String)pathsProperties.get("TWEET_PATH");
        Paths.COMMENT_PATH =(String)pathsProperties.get("COMMENT_PATH");
        Paths.CHAT_PATH =(String)pathsProperties.get("CHAT_PATH");
        Paths.USER_PATH =(String)pathsProperties.get("USER_PATH");
        Paths.EVENT_PATH = (String)pathsProperties.get("EVENT_PATH");
        Paths.EVENT_PATH_ID_COUNTER = (String)pathsProperties.get("EVENT_PATH_ID_COUNTER");
        //
        String portFilePath = (String) mainProperties.get("portsPath");
        portProperty.load(new FileReader(portFilePath));
        ServerAddress.MAIN_ADDRESS =(String) portProperty.get("MAIN_Address");
        ServerAddress.MAIN_PORT =Integer.parseInt(
                (String) portProperty.get("MAIN_PORT"));
    }
}
