package app.auth.view;


import app.auth.controller.RegisterController;
import auth.AuthToken;
import network.EventListener;
import resources.Texts;
import responses.visitors.ResponseVisitor;
import view.listeners.StringListener;

import javax.swing.*;
import java.io.IOException;
import java.util.HashMap;

public class AuthenticationMainPanel extends JPanel {

    private StringListener stringListener;
    private final StartPanel startPanel;
    private final RegistrationPanel registrationPanel;
    private final LoginPanel loginPanel;
    private final ManageBiography manageBiography;

    public AuthenticationMainPanel(EventListener eventListener,
                                   HashMap<String, ResponseVisitor> visitors,
                                   AuthToken authToken) {
        RegisterController registerController = new RegisterController();
        //
        this.setLayout(null);
        this.setBounds(0,0,2000,800);
        //
        startPanel = new StartPanel();
        startPanel.setListener(new StringListener() {
            @Override
            public void stringEventOccurred(String text) {
                if(text.equals(Texts.LOGIN)){
                    removeAll();
                    add(loginPanel);
                    repaint();
                    revalidate();
                }
                else if(text.equals(Texts.REGISTER_BUTTON_TEXT)){
                    removeAll();
                    add(registrationPanel);
                    repaint();
                    revalidate();
                }
            }
        });
        //
        manageBiography = new ManageBiography(eventListener, visitors);
        manageBiography.setStringListener(text -> {
            if(text.equals("register")){
                removeAll();
                repaint();
                revalidate();
                listenMe();
                add(startPanel);
            }
        });
        //
        registrationPanel = new RegistrationPanel(eventListener, authToken,
                registerController);
        visitors.put("RegistrationResponseVisitor", registrationPanel);
        registrationPanel.setListener(text -> {
            if(text.equals(Texts.BACK)){
                 removeAll();
                 add(startPanel);
                 repaint();
                 revalidate();
            }
            else if(text.equals(Texts.CONTINUE)){
                removeAll();
                add(manageBiography);
                repaint();
                revalidate();
            }
        });
        //
        loginPanel = new LoginPanel(eventListener, visitors, authToken);
        loginPanel.setListener(text -> {
            if(text.equals(Texts.LOGIN)){
                removeAll();
                repaint();
                revalidate();
                listenMe();
                add(startPanel);
            }
            else if(text.equals(Texts.BACK)){
                removeAll();
                add(startPanel);
                repaint();
                revalidate();
            }
        });
        //
        this.add(startPanel);
        repaint();
        revalidate();
    }

    public void setStringListener(StringListener stringListener) {
        this.stringListener = stringListener;
    }

    public void listenMe() throws IOException {
        stringListener.stringEventOccurred("aut");
    }
}