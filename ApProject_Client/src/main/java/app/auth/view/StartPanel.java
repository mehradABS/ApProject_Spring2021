package app.auth.view;

import resources.Colors;
import resources.Fonts;
import resources.Images;
import resources.Texts;
import view.listeners.StringListener;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;


public class StartPanel extends JPanel{

    private final JButton loginButton;
    private final JButton registerButton;
    private final JLabel welcomeLabel;
    private StringListener stringListener;

    public StartPanel() {
        loginButton = new JButton(Texts.LOGIN_BUTTON_TEXT);
        loginButton.setBounds(650,340,250,40);
        loginButton.setBackground(Color.decode(Colors.BUTTONS_COLOR));
        loginButton.setFocusable(false);
        loginButton.setFont(Fonts.BUTTONS_FONT);
        loginButton.addActionListener(e->
        {
            try {
                listenMe(loginButton.getText());
            } catch (IOException ignored) {

            }
        });
        //
        registerButton = new JButton(Texts.REGISTER_BUTTON_TEXT);
        registerButton.setBounds(650,390,250,40);
        registerButton.setBackground(Color.decode(Colors.BUTTONS_COLOR));
        registerButton.setFocusable(false);
        registerButton.setFont(Fonts.BUTTONS_FONT);
        registerButton.addActionListener(e->
        {
            try {
                listenMe(registerButton.getText());
            } catch (IOException ignored) {

            }
        });
        //
        welcomeLabel = new JLabel(Texts.WELCOME_LABEL_TEXT);
        welcomeLabel.setBounds(520,200,600,200);
        welcomeLabel.setForeground(Color.decode(Colors.WELCOME_LABEL_COLOR));
        welcomeLabel.setFont(Fonts.WELCOME_LABEL_FONT);
        //
        this.setLayout(null);
        this.add(welcomeLabel);
        this.add(registerButton);
        this.add(loginButton);
        this.setBounds(0,0,2000,800);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2D = (Graphics2D) g;
        g2D.drawImage(Images.START_PANEL_IMAGE,0,0,null);
    }

    public JButton getLoginButton() {
        return loginButton;
    }

    public JButton getRegisterButton() {
        return registerButton;
    }

    public JLabel getWelcomeLabel() {
        return welcomeLabel;
    }

    public StringListener getListeners() {
        return stringListener;
    }

    public void listenMe(String buttonText) throws IOException {
        stringListener.stringEventOccurred(buttonText);
    }

    public void setListener(StringListener stringListener){
        this.stringListener = stringListener;
    }
}
