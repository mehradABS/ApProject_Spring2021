package app.explore.watchProfile;

import network.EventListener;
import resources.Colors;
import responses.visitors.ResponseVisitor;
import view.listeners.StringListener;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.HashMap;

public class ProfileMainPanel extends JPanel {


    private final ProfileTopPanel profileTopPanel;
    private StringListener stringListener;
    private int userId;

    public ProfileMainPanel(EventListener eventListener, HashMap<String, ResponseVisitor>
                            visitors) {
        profileTopPanel = new ProfileTopPanel(eventListener, visitors);
        profileTopPanel.setStringListener(text -> {
            if(text.equals("back")) {
                listenMe("back");
            }
            else if(text.startsWith("message")){
                listenMe(text);
            }
        });
        //
        this.setLayout(null);
        this.setBounds(0,0,700,700);
        this.setBackground(Color.decode(Colors.INFO_PANEL));
        this.add(profileTopPanel);
    }

    public void setStringListener(StringListener stringListener) {
        this.stringListener = stringListener;
    }

    public void listenMe(String name){
        try {
            stringListener.stringEventOccurred(name);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    public void resetPanel(){
        profileTopPanel.stopLoop();
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public ProfileTopPanel getProfileTopPanel() {
        return profileTopPanel;
    }
}
