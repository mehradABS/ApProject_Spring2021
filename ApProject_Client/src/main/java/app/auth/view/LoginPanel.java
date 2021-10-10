package app.auth.view;


import auth.AuthToken;
import controller.OfflineController;
import events.auth.LoginEvent;
import network.EventListener;
import resources.Colors;
import resources.Fonts;
import resources.Images;
import resources.Texts;
import responses.auth.RegistrationResponse;
import responses.visitors.ResponseVisitor;
import responses.visitors.auth.RegistrationResponseVisitor;
import view.listeners.StringListener;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.HashMap;

public class LoginPanel extends JPanel implements RegistrationResponseVisitor {

    private final JTextField usernameField;
    private final JPasswordField passwordField;
    private final JButton backButton;
    private final JLabel invalidLabel;
    private StringListener stringListener;
    private final EventListener eventListener;
    private final LoginEvent loginEvent;
    private final AuthToken authToken;

    public LoginPanel(EventListener eventListener, HashMap<String, ResponseVisitor>
            visitors, AuthToken authToken) {
        this.authToken = authToken;
        visitors.put("LoginResponseVisitor", this);
        this.eventListener = eventListener;
        loginEvent = new LoginEvent();
        //
        JLabel welcomeLabel = new JLabel(Texts.WELCOME_BACK);
        welcomeLabel.setBounds(610,240,600,200);
        welcomeLabel.setForeground(Color.decode(Colors.WELCOME_LABEL_COLOR));
        welcomeLabel.setFont(Fonts.WELCOME_LABEL_FONT);
        //
        usernameField = new JTextField();
        usernameField.setBounds(650,390,250,40);
        usernameField.setBackground(Color.decode(Colors.BUTTONS_COLOR));
        usernameField.setFont(Fonts.BUTTONS_FONT);
        //
        JLabel usernameLabel = new JLabel(Texts.USERNAME);
        usernameLabel.setBounds(540,390,130,40);
        usernameLabel.setForeground(Color.decode(Colors.LABEL_COLOR));
        usernameLabel.setFont(Fonts.Label_FONT);
        //
        passwordField = new JPasswordField();
        passwordField.setBounds(650,440,250,40);
        passwordField.setBackground(Color.decode(Colors.BUTTONS_COLOR));
        passwordField.setFont(Fonts.BUTTONS_FONT);
        //
        JLabel passwordLabel = new JLabel(Texts.PASSWORD);
        passwordLabel.setBounds(540,435,130,40);
        passwordLabel.setForeground(Color.decode(Colors.LABEL_COLOR));
        passwordLabel.setFont(Fonts.Label_FONT);
        //
        JButton loginButton = new JButton(Texts.LOGIN);
        loginButton.setBounds(650,490,250,40);
        loginButton.setBackground(Color.decode(Colors.BUTTONS_COLOR));
        loginButton.setFocusable(false);
        loginButton.setFont(Fonts.BUTTONS_FONT);
        loginButton.addActionListener(e->
        {
            try {
                loginAction();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });
        //
        invalidLabel = new JLabel(Texts.INVALID_LOGIN);
        invalidLabel.setBounds(630,540,400,40);
        invalidLabel.setForeground(Color.decode(Colors.LABEL_COLOR));
        invalidLabel.setFont(Fonts.Label_FONT);
        invalidLabel.setVisible(false);
        //
        backButton = new JButton(Texts.BACK);
        backButton.setBounds(520,490,100,40);
        backButton.setBackground(Color.decode(Colors.BUTTONS_COLOR));
        backButton.setFocusable(false);
        backButton.setFont(Fonts.BUTTONS_FONT);
        backButton.addActionListener(e->{
            resetPanel();
            try {
                listenMe(backButton.getText());
            } catch (IOException ignored) {

            }
        });
        //
        this.setLayout(null);
        this.setBounds(0,0,2000,800);
        this.add(backButton);
        this.add(loginButton);
        this.add(passwordField);
        this.add(usernameField);
        this.add(welcomeLabel);
        this.add(passwordLabel);
        this.add(usernameLabel);
        this.add(invalidLabel);
    }

    public void listenMe(String buttonText) throws IOException {
        stringListener.stringEventOccurred(buttonText);
    }

    public void setListener(StringListener stringListener){
        this.stringListener = stringListener;
    }

    public void loginAction() throws IOException {
        loginEvent.setUsername(usernameField.getText());
        StringBuilder password = new StringBuilder();
        char[] a = passwordField.getPassword();
        for (char c : a) {
            password.append(c);
        }
        loginEvent.setPassword(password.toString());
        eventListener.listen(loginEvent);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2D = (Graphics2D) g;
        g2D.drawImage(Images.START_PANEL_IMAGE,0,0,null);
    }

    public void resetPanel(){
        invalidLabel.setVisible(false);
        passwordField.setText(null);
        usernameField.setText(null);
    }

    @Override
    public void getAnswer(RegistrationResponse registrationResponse) {
        String answer = registrationResponse.getAnswer();
        if(answer.startsWith("ok")){
            authToken.setAuthToken(Long.parseLong(answer.substring(2)));
            OfflineController.setCurrentUser(registrationResponse.getId());
            try {
                listenMe("login");
            } catch (IOException e) {
                e.printStackTrace();
            }
            resetPanel();
        }
        else {
            invalidLabel.setText(answer);
            invalidLabel.setVisible(true);
        }
    }
}