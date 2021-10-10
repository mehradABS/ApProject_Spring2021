package app.auth.view;



import app.auth.controller.RegisterController;
import auth.AuthToken;
import events.auth.RegistrationEvent;
import net.sourceforge.jdatepicker.impl.JDatePanelImpl;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;
import net.sourceforge.jdatepicker.impl.SqlDateModel;
import network.EventListener;
import resources.Colors;
import resources.Fonts;
import resources.Images;
import resources.Texts;
import responses.auth.RegistrationResponse;
import responses.visitors.auth.RegistrationResponseVisitor;
import view.listeners.StringListener;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.time.LocalDate;

public class RegistrationPanel extends JPanel implements RegistrationResponseVisitor {

    private final JButton backButton;
    private final JButton continueButton;
    private final JTextField firstnameField;
    private final JTextField lastnameField;
    private final JTextField usernameField;
    private final JPasswordField passwordField;
    private final JTextField emailField;
    private final JTextField phoneField;
    private final JDatePickerImpl datePicker;
    private StringListener stringListener;
    private final JLabel invalidLabel;
    private final RegistrationEvent registrationEvent;
    private final AuthToken authToken;
    private final EventListener eventListener;
    private final RegisterController registerController;

    public RegistrationPanel(EventListener eventListener, AuthToken authToken
    , RegisterController registerController) {
        this.registerController = registerController;
        //
        this.authToken = authToken;
        this.eventListener = eventListener;
        //
        registrationEvent = new RegistrationEvent();
        //
        firstnameField = new JTextField();
        firstnameField.setBounds(650,190,250,40);
        firstnameField.setBackground(Color.decode(Colors.BUTTONS_COLOR));
        firstnameField.setFont(Fonts.BUTTONS_FONT);
        //
        JLabel firstnameLabel = new JLabel(Texts.FIRSTNAME);
        firstnameLabel.setBounds(540,185,130,40);
        firstnameLabel.setForeground(Color.decode(Colors.LABEL_COLOR));
        firstnameLabel.setFont(Fonts.Label_FONT);
        //
        lastnameField = new JTextField();
        lastnameField.setBounds(650,240,250,40);
        lastnameField.setBackground(Color.decode(Colors.BUTTONS_COLOR));
        lastnameField.setFont(Fonts.BUTTONS_FONT);
        //
        JLabel lastnameLabel = new JLabel(Texts.LASTNAME);
        lastnameLabel.setBounds(540,235,130,40);
        lastnameLabel.setForeground(Color.decode(Colors.LABEL_COLOR));
        lastnameLabel.setFont(Fonts.Label_FONT);
        //
        usernameField = new JTextField();
        usernameField.setBounds(650,290,250,40);
        usernameField.setBackground(Color.decode(Colors.BUTTONS_COLOR));
        usernameField.setFont(Fonts.BUTTONS_FONT);
        //
        JLabel usernameLabel = new JLabel(Texts.USERNAME);
        usernameLabel.setBounds(540,285,130,40);
        usernameLabel.setForeground(Color.decode(Colors.LABEL_COLOR));
        usernameLabel.setFont(Fonts.Label_FONT);
        //
        passwordField = new JPasswordField();
        passwordField.setBounds(650,340,250,40);
        passwordField.setBackground(Color.decode(Colors.BUTTONS_COLOR));
        passwordField.setFont(Fonts.BUTTONS_FONT);
        //
        JLabel passwordLabel = new JLabel(Texts.PASSWORD);
        passwordLabel.setBounds(540,335,130,40);
        passwordLabel.setForeground(Color.decode(Colors.LABEL_COLOR));
        passwordLabel.setFont(Fonts.Label_FONT);
        //
        emailField = new JTextField();
        emailField.setBounds(650,390,250,40);
        emailField.setBackground(Color.decode(Colors.BUTTONS_COLOR));
        emailField.setFont(Fonts.BUTTONS_FONT);
        //
        JLabel emailLabel = new JLabel(Texts.EMAIL);
        emailLabel.setBounds(580,385,130,40);
        emailLabel.setForeground(Color.decode(Colors.LABEL_COLOR));
        emailLabel.setFont(Fonts.Label_FONT);
        //
        phoneField = new JTextField();
        phoneField.setBounds(650,440,250,40);
        phoneField.setBackground(Color.decode(Colors.BUTTONS_COLOR));
        phoneField.setFont(Fonts.BUTTONS_FONT);
        //
        JLabel phoneLabel = new JLabel(Texts.PHONE);
        phoneLabel.setBounds(580,435,130,40);
        phoneLabel.setForeground(Color.decode(Colors.LABEL_COLOR));
        phoneLabel.setFont(Fonts.Label_FONT);
        //
        SqlDateModel model = new SqlDateModel();
        JDatePanelImpl datePanel = new JDatePanelImpl(model);
        datePicker = new JDatePickerImpl(datePanel);
        datePicker.setBounds(650, 490, 200, 40);
        datePicker.getJFormattedTextField()
                .setBackground(Color.decode(Colors.BUTTONS_COLOR));
        datePicker.getJFormattedTextField().setFont(Fonts.BUTTONS_FONT);
        //
        JLabel birthLabel = new JLabel(Texts.BIRTH);
        birthLabel.setBounds(580,485,130,40);
        birthLabel.setForeground(Color.decode(Colors.LABEL_COLOR));
        birthLabel.setFont(Fonts.Label_FONT);
        //
        JLabel createAccountLabel = new JLabel(Texts.CREATE_ACCOUNT);
        createAccountLabel.setBounds(520,30,600,200);
        createAccountLabel.setForeground(Color.decode(Colors.WELCOME_LABEL_COLOR));
        createAccountLabel.setFont(Fonts.WELCOME_LABEL_FONT);
        //
        continueButton = new JButton(Texts.CONTINUE);
        continueButton.setBounds(650,580,250,40);
        continueButton.setBackground(Color.decode(Colors.BUTTONS_COLOR));
        continueButton.setFocusable(false);
        continueButton.setFont(Fonts.BUTTONS_FONT);
        continueButton.addActionListener(e-> registerAction());
        //
        backButton = new JButton(Texts.BACK);
        backButton.setBounds(520,580,100,40);
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
        invalidLabel = new JLabel();
        invalidLabel.setBounds(700,630,400,40);
        invalidLabel.setForeground(Color.decode(Colors.LABEL_COLOR));
        invalidLabel.setFont(Fonts.Label_FONT);
        invalidLabel.setVisible(false);
        //
        this.setLayout(null);
        this.setBounds(0,0,2000,800);
        this.add(continueButton);
        this.add(firstnameField);
        this.add(firstnameLabel);
        this.add(lastnameField);
        this.add(lastnameLabel);
        this.add(usernameField);
        this.add(usernameLabel);
        this.add(passwordField);
        this.add(passwordLabel);
        this.add(emailField);
        this.add(emailLabel);
        this.add(phoneField);
        this.add(phoneLabel);
        this.add(datePicker);
        this.add(birthLabel);
        this.add(createAccountLabel);
        this.add(backButton);
        this.add(invalidLabel);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2D = (Graphics2D) g;
        g2D.drawImage(Images.START_PANEL_IMAGE,0,0,null);
    }

    public void listenMe(String buttonText) throws IOException {
        stringListener.stringEventOccurred(buttonText);
    }

    public void setListener(StringListener stringListener){
        this.stringListener = stringListener;
    }

    public void registerAction(){
        registrationEvent.setFirstname(firstnameField.getText());
        registrationEvent.setLastname(lastnameField.getText());
        registrationEvent.setUsername(usernameField.getText());
        //
        StringBuilder password = new StringBuilder();
        char[] a = passwordField.getPassword();
        for (char c : a) {
            password.append(c);
        }
        registrationEvent.setPassword(password.toString());
        //
        registrationEvent.setEmail(emailField.getText());
        registrationEvent.setPhone(phoneField.getText());
        registrationEvent.setBirth(LocalDate.of(
                datePicker.getModel().getYear(),datePicker
                        .getModel().getMonth() + 1,datePicker.getModel().getDay()));
        eventListener.listen(registrationEvent);
    }

    public void resetPanel(){
        firstnameField.setText(null);
        lastnameField.setText(null);
        usernameField.setText(null);
        passwordField.setText(null);
        emailField.setText(null);
        phoneField.setText(null);
        datePicker.getJFormattedTextField().setText(null);
        invalidLabel.setVisible(false);
    }

    @Override
    public void getAnswer(RegistrationResponse registrationResponse) {
        String answer = registrationResponse.getAnswer();
        if(answer.startsWith("ok")){
            authToken.setAuthToken(Long.parseLong(answer.substring(2)));
            registerController.signup(registrationResponse.getId(), registrationEvent);
            try {
                listenMe(continueButton.getText());
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