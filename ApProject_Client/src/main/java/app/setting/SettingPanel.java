package app.setting;

import controller.OfflineController;
import events.setting.SettingEvent;
import network.EventListener;
import resources.Colors;
import resources.Fonts;
import resources.Texts;
import responses.setting.SettingResponse;
import responses.visitors.ResponseVisitor;
import responses.visitors.setting.SettingPanelResponseVisitor;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.HashMap;


public class SettingPanel extends JPanel implements SettingPanelResponseVisitor {

    private final EventListener eventListener;
    private final JButton publicButton;
    private final JButton privateButton;
    private final JButton allUsersButton;
    private final JButton nobodyButton;
    private final JButton followingsButton;
    private final JButton openButton;
    private final JButton closeButton;
    private final JLabel privacyLabel;
    private final JLabel lastSeenLabel;
    private final JLabel activateLabel;
    private final SettingController controller = new SettingController();

    public SettingPanel(EventListener eventListener
        , HashMap<String, ResponseVisitor> visitors) {
        this.eventListener = eventListener;
        visitors.put("SettingPanelResponseVisitor", this);
        JPanel privacyPanel = new JPanel();
        makePanel(0, privacyPanel);
        privacyLabel = new JLabel();
        privacyLabel.setFont(Fonts.LABEL_FONT_INFO_PANEL);
        privacyPanel.add(privacyLabel);
        publicButton = new JButton(Texts.PUBLIC);
        publicButton.addActionListener(e-> publicButtonAction());
        privateButton = new JButton(Texts.PRIVATE);
        privateButton.addActionListener(e-> privateButtonAction());
        makeButton(privateButton);
        makeButton(publicButton);
        publicButton.setBounds(250,10,100,45);
        privateButton.setBounds(360,10,100,45);
        privacyPanel.add(publicButton);
        privacyPanel.add(privateButton);
        //
        JPanel lastSeenPanel = new JPanel();
        lastSeenPanel.setLayout(null);
        lastSeenPanel.setBackground(Color.decode(Colors.SUB_PANEL));
        lastSeenPanel.setBounds(0,70,500,160);
        lastSeenLabel = new JLabel();
        lastSeenLabel.setFont(Fonts.LABEL_FONT_INFO_PANEL);
        lastSeenPanel.add(lastSeenLabel);
        allUsersButton = new JButton(Texts.ALL);
        allUsersButton.addActionListener(e-> allUsersButtonAction());
        nobodyButton = new JButton(Texts.NOBODY);
        nobodyButton.addActionListener(e-> nobodyButtonAction());
        followingsButton = new JButton(Texts.FOLLOWINGS1);
        followingsButton.addActionListener(e-> followingsButtonAction());
        allUsersButton.setBounds(290,5,200,45);
        nobodyButton.setBounds(290,60,200,45);
        followingsButton.setBounds(290,110,200,45);
        makeButton(allUsersButton);
        makeButton(followingsButton);
        makeButton(nobodyButton);
        lastSeenPanel.add(allUsersButton);
        lastSeenPanel.add(nobodyButton);
        lastSeenPanel.add(followingsButton);
        //
        JPanel activatePanel = new JPanel();
        makePanel(232, activatePanel);
        activateLabel = new JLabel();
        activateLabel.setFont(Fonts.LABEL_FONT_INFO_PANEL);
        activatePanel.add(activateLabel);
        openButton = new JButton(Texts.OPEN);
        openButton.addActionListener(e-> openButtonAction());
        closeButton = new JButton(Texts.CLOSE);
        closeButton.addActionListener(e-> closeButtonAction());
        makeButton(closeButton);
        makeButton(openButton);
        openButton.setBounds(250,10,100,45);
        closeButton.setBounds(360,10,100,45);
        activatePanel.add(openButton);
        activatePanel.add(closeButton);
        //
        JButton removeAccountButton = new JButton(Texts.REMOVE_Account);
        removeAccountButton.setBackground(Color.decode(Colors.CHANGE_INFO_COLOR));
        removeAccountButton.setFont(Fonts.BUTTONS_FONT);
        removeAccountButton.setBounds(140,310,250,40);
        removeAccountButton.addActionListener(e-> {
            if(OfflineController.IS_ONLINE) {
                SettingEvent event = new SettingEvent();
                event.setEvent("removeAccount");
                eventListener.listen(event);
                System.exit(0);
            }
        });
        removeAccountButton.setFocusable(false);
        //
        this.setLayout(null);
        this.setBackground(Color.decode(Colors.INFO_PANEL));
        this.setBounds(260,50,500,370);
        this.add(lastSeenPanel);
        this.add(privacyPanel);
        this.add(activatePanel);
        this.add(removeAccountButton);
    }

    public void getResponse(SettingResponse response){
        controller.setUser(response.getUser());
        if(response.getInfo() != null)
            getInfo(response.getInfo());
    }

    public void makePanel(int y, JPanel panel){
        panel.setLayout(null);
        panel.setBackground(Color.decode(Colors.SUB_PANEL));
        panel.setBounds(0,y,500,67);
    }

    public void makeButton(JButton button){
        button.setBackground(Color.decode(Colors.CHANGE_INFO_COLOR));
        button.setFocusable(false);
        button.setFont(Fonts.BUTTONS_FONT);
    }

    public void setInfo(){
        if(OfflineController.IS_ONLINE) {
            SettingEvent event = new SettingEvent();
            event.setEvent("getInfo");
            eventListener.listen(event);
        }
        else{
            try {
                getInfo(controller.loadInfo());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void getInfo(String[] info){
         if(info[0].equals("public")){
             privacyLabel.setText("<html>"+Texts.PRIVACY+"<br>"+Texts.PUBLIC
             +"</html>");
             publicButton.setEnabled(false);
         }
         else{
             privacyLabel.setText("<html>"+Texts.PRIVACY+"<br>"+Texts.PRIVATE
                     +"</html>");
             privateButton.setEnabled(false);
         }
        privacyLabel.setBounds(2,0,
                (int)privacyLabel.getPreferredSize().getWidth()+10,
                70);
         if(info[1].equals("nobody")){
             lastSeenLabel.setText("<html>"+Texts.LAST_SEEN+"<br>"+Texts.NOBODY+
                     "</html>");
             nobodyButton.setEnabled(false);
         }
         else if(info[1].equals("allUsers")){
             lastSeenLabel.setText("<html>"+Texts.LAST_SEEN+"<br>"+Texts.ALL+
                     "</html>");
             allUsersButton.setEnabled(false);
         }
         else{
             lastSeenLabel.setText("<html>"+Texts.LAST_SEEN+"<br>"+Texts.FOLLOWINGS1+
                     "</html>");
             followingsButton.setEnabled(false);
         }
        lastSeenLabel.setBounds(2,0,
                (int)lastSeenLabel.getPreferredSize().getWidth()+10,
                70);
         if(Boolean.parseBoolean(info[2])){
             activateLabel.setText("<html>"+Texts.ACTIVATE+"<br>"+Texts.OPEN+
                     "</html>");
             openButton.setEnabled(false);
         }
         else{
             activateLabel.setText("<html>"+Texts.ACTIVATE+"<br>"+Texts.CLOSE+
                     "</html>");
             closeButton.setEnabled(false);
         }
        activateLabel.setBounds(2,0,
                (int)activateLabel.getPreferredSize().getWidth()+10,
                70);
         repaint();
         revalidate();
    }

    public void allUsersButtonAction(){
        SettingEvent event = new SettingEvent();
        event.setEvent("lastSeen");
        event.setLastSeen("allUsers");
        eventListener.listen(event);
        if(!OfflineController.IS_ONLINE){
            try {
                controller.setLastSeen("allUsers");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        lastSeenLabel.setText("<html>"+Texts.LAST_SEEN+"<br>"+Texts.ALL+
                "</html>");
        allUsersButton.setEnabled(false);
        nobodyButton.setEnabled(true);
        followingsButton.setEnabled(true);
        repaint();
        revalidate();
    }

    public void nobodyButtonAction(){
        SettingEvent event = new SettingEvent();
        event.setEvent("lastSeen");
        event.setLastSeen("nobody");
        eventListener.listen(event);
        if(!OfflineController.IS_ONLINE){
            try {
                controller.setLastSeen("nobody");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        lastSeenLabel.setText("<html>"+Texts.LAST_SEEN+"<br>"+Texts.NOBODY+
                "</html>");
        nobodyButton.setEnabled(false);
        followingsButton.setEnabled(true);
        allUsersButton.setEnabled(true);
        repaint();
        revalidate();
    }

    public void followingsButtonAction(){
        SettingEvent event = new SettingEvent();
        event.setEvent("lastSeen");
        event.setLastSeen("just yourFollowing");
        eventListener.listen(event);
        if(!OfflineController.IS_ONLINE){
            try {
                controller.setLastSeen("just yourFollowing");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        lastSeenLabel.setText("<html>"+Texts.LAST_SEEN+"<br>"+Texts.FOLLOWINGS1+
                "</html>");
        followingsButton.setEnabled(false);
        nobodyButton.setEnabled(true);
        allUsersButton.setEnabled(true);
        repaint();
        revalidate();
    }

    public void openButtonAction(){
        SettingEvent event = new SettingEvent();
        event.setEvent("account");
        event.setActivate(true);
        eventListener.listen(event);
        if(!OfflineController.IS_ONLINE){
            try {
                controller.setActivate(true);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        activateLabel.setText("<html>"+Texts.ACTIVATE+"<br>"+Texts.OPEN+
                "</html>");
        openButton.setEnabled(false);
        closeButton.setEnabled(true);
        repaint();
        revalidate();
    }

    public void closeButtonAction(){
        SettingEvent event = new SettingEvent();
        event.setEvent("account");
        event.setActivate(false);
        eventListener.listen(event);
        if(!OfflineController.IS_ONLINE){
            try {
                controller.setActivate(false);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        activateLabel.setText("<html>"+Texts.ACTIVATE+"<br>"+Texts.CLOSE+
                "</html>");
        closeButton.setEnabled(false);
        openButton.setEnabled(true);
        repaint();
        revalidate();
    }

    public void publicButtonAction(){
        SettingEvent event = new SettingEvent();
        event.setEvent("privacy");
        eventListener.listen(event);
        if(!OfflineController.IS_ONLINE){
            try {
                controller.setPrivacy();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        privacyLabel.setText("<html>"+Texts.PRIVACY+"<br>"+Texts.PUBLIC
                +"</html>");
        publicButton.setEnabled(false);
        privateButton.setEnabled(true);
        repaint();
        revalidate();
    }

    public void privateButtonAction(){
        SettingEvent event = new SettingEvent();
        event.setEvent("privacy");
        eventListener.listen(event);
        if(!OfflineController.IS_ONLINE){
            try {
                controller.setPrivacy();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        privacyLabel.setText("<html>"+Texts.PRIVACY+"<br>"+Texts.PRIVATE
                +"</html>");
        privateButton.setEnabled(false);
        publicButton.setEnabled(true);
        repaint();
        revalidate();
    }
}
