package view.menu;

import controller.OfflineController;
import controller.OnlinePanels;
import resources.Colors;
import resources.Images;
import view.listeners.StringListener;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;


public class MenuPanel extends JPanel implements OnlinePanels {

    private StringListener stringListener;
    private final JButton connectionButton;

    public MenuPanel() {
        //
        JButton profileButton = new JButton(Images.PROFILE_ICON);
        profileButton.setBounds(45,40,100,50);
        profileButton.setBackground(Color.decode(Colors.BUTTONS_COLOR));
        profileButton.setFocusable(false);
        profileButton.addActionListener(e->{
            try {
                listenMe("profile");
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });
        //
        JButton timelineButton = new JButton(Images.TIMELINE_ICON);
        timelineButton.setBounds(45,105,100,50);
        timelineButton.setBackground(Color.decode(Colors.BUTTONS_COLOR));
        timelineButton.setFocusable(false);
        timelineButton.addActionListener(e -> {
            try {
                listenMe("timeline");
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });
        //
        JButton exploreButton = new JButton(Images.EXPLORE_ICON);
        exploreButton.setBounds(45,170,100,50);
        exploreButton.setBackground(Color.decode(Colors.BUTTONS_COLOR));
        exploreButton.setFocusable(false);
        exploreButton.addActionListener(e -> {
            try {
                listenMe("explore");
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });
        //
        JButton chatButton = new JButton(Images.MESSAGE_BIG_ICON);
        chatButton.setBounds(45,235,100,50);
        chatButton.setBackground(Color.decode(Colors.BUTTONS_COLOR));
        chatButton.setFocusable(false);
        chatButton.addActionListener(e -> {
            try {
                listenMe("chat");
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });
        //
        JButton listsButton = new JButton(Images.LISTS);
        listsButton.setBounds(45,300,100,50);
        listsButton.setBackground(Color.decode(Colors.BUTTONS_COLOR));
        listsButton.setFocusable(false);
        listsButton.addActionListener(e -> {
            try {
                listenMe("lists");
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });
        //
        JButton settingButton = new JButton(Images.SETTING);
        settingButton.setBounds(45,365,100,50);
        settingButton.setBackground(Color.decode(Colors.BUTTONS_COLOR));
        settingButton.setFocusable(false);
        settingButton.addActionListener(e -> {
            try {
                listenMe("setting");
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });
        //
        JButton notifButton = new JButton(Images.NOTIFICATION);
        notifButton.setBounds(45,430,100,50);
        notifButton.setBackground(Color.decode(Colors.BUTTONS_COLOR));
        notifButton.setFocusable(false);
        notifButton.addActionListener(e -> {
            try {
                listenMe("notif");
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });
        //
        JButton logoutButton = new JButton(Images.LOGOUT);
        logoutButton.setBounds(45,495,100,50);
        logoutButton.setBackground(Color.decode(Colors.BUTTONS_COLOR));
        logoutButton.setFocusable(false);
        logoutButton.addActionListener(e -> {
            try {
                listenMe("logout");
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });
        //
        JButton botButton = new JButton(Images.BOT);
        botButton.setBounds(45,560,100,50);
        botButton.setBackground(Color.decode(Colors.BUTTONS_COLOR));
        botButton.setFocusable(false);
        botButton.addActionListener(e -> {
            try {
                listenMe("bot");
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });
        //
        connectionButton = new JButton();
        if(OfflineController.IS_ONLINE){
            connectionButton.setIcon(Images.WIFI_ON);
        }
        else{
            connectionButton.setIcon(Images.WIFI_OFF);
        }
        connectionButton.setBounds(45,625,100,50);
        connectionButton.setBackground(Color.decode(Colors.BUTTONS_COLOR));
        connectionButton.setFocusable(false);
        connectionButton.addActionListener(e -> {
            try {
                listenMe("connection");
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });
        //
        this.setLayout(null);
        this.setBackground(Color.decode(Colors.MENU_PANEL_COLOR));
        this.setBounds(50,50,200,700);
        this.add(profileButton);
        this.add(timelineButton);
        this.add(exploreButton);
        this.add(chatButton);
        this.add(listsButton);
        this.add(settingButton);
        this.add(notifButton);
        this.add(logoutButton);
        this.add(connectionButton);
        add(botButton);
    }

    public void setStringListener(StringListener stringListener) {
        this.stringListener = stringListener;
    }

    public void listenMe(String name) throws IOException {
        stringListener.stringEventOccurred(name);
    }

    @Override
    public void changeState() {
        if(OfflineController.IS_ONLINE){
            connectionButton.setIcon(Images.WIFI_ON);
        }
        else{
            connectionButton.setIcon(Images.WIFI_OFF);
        }
    }
}