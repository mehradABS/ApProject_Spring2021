package app.personalPage.subPart.info.view;


import app.personalPage.subPart.info.controller.ChangeInfoController;
import controller.OfflineController;
import controller.OnlinePanels;
import events.info.ChangeInfoEvent;
import net.sourceforge.jdatepicker.impl.JDatePanelImpl;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;
import net.sourceforge.jdatepicker.impl.SqlDateModel;
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
import java.time.LocalDate;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class ChangeInfoPanel extends JPanel implements OnlinePanels,
        RegistrationResponseVisitor {

    private final JLabel connectingLabel;
    private final JTextArea textField;
    private final JLabel label;
    private final List<StringListener> stringListeners;
    private final EventListener eventListener;
    private final ChangeInfoEvent changeInfoEvent;
    private final JLabel invalidLabel;
    private final JDatePickerImpl datePicker;
    private final JScrollPane scrollPane;
    private final ChangeInfoController changeInfoController = new ChangeInfoController();
    private final JButton saveButton;
    private final JButton backButton;

    public ChangeInfoPanel(EventListener eventListener, HashMap<String,
            ResponseVisitor> visitors) {
        visitors.put("ChangeInfoResponseVisitor", this);
        this.eventListener = eventListener;
        //
        stringListeners = new LinkedList<>();
        //
        label = new JLabel();
        label.setFont(Fonts.LABEL_FONT_INFO_PANEL);
        label.setBounds(27,24,200,60);
        //
        connectingLabel = new JLabel(Texts.CONNECTING);
        connectingLabel.setBounds(100,0,500,200);
        connectingLabel.setFont(Fonts.WELCOME_LABEL_FONT);
        //
        //
        SqlDateModel model = new SqlDateModel();
        JDatePanelImpl datePanel = new JDatePanelImpl(model);
        datePicker = new JDatePickerImpl(datePanel);
        datePicker.setBounds(30, 80, 460, 100);
        datePicker.getJFormattedTextField().setBounds(30,80,460,100);
        datePicker.getJFormattedTextField()
                .setBackground(Color.decode(Colors.CHANGE_INFO_COLOR));
        datePicker.getJFormattedTextField().setFont(Fonts.BUTTONS_FONT);
        datePicker.add(label);
        datePicker.setVisible(false);
        //
        invalidLabel = new JLabel();
        invalidLabel.setBounds(190,200,400,40);
        invalidLabel.setForeground(Color.decode(Colors.LABEL_COLOR));
        invalidLabel.setFont(Fonts.Label_FONT);
        invalidLabel.setVisible(false);
        //
        changeInfoEvent = new ChangeInfoEvent();
        //
        textField = new JTextArea();
        textField.setLineWrap(true);
        textField.setBounds(0,0,460,100);
        textField.setBackground(Color.decode(Colors.CHANGE_INFO_COLOR));
        textField.setFont(Fonts.BUTTONS_FONT);
        scrollPane = new JScrollPane(textField,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBounds(30,80,460,100);
        //
        saveButton = new JButton();
        saveButton.setBounds(160,240,180,50);
        saveButton.setText(Texts.SAVE_BUTTON);
        saveButton.setBackground(Color.decode(Colors.CHANGE_INFO_COLOR));
        saveButton.setFont(Fonts.BUTTONS_FONT);
        saveButton.setFocusable(false);
        saveButton.addActionListener(e -> saveButtonAction());
        //
        backButton = new JButton(Images.BACK_ICON);
        backButton.setBounds(450,10,45,45);
        backButton.setFocusable(false);
        backButton.setBackground(Color.decode(Colors.SUB_PANEL));
        backButton.addActionListener(e -> {
            resetPanel();
            try {
                listenMe("back");
            } catch (IOException ignored) {

            }
        });
        //
        this.setBackground(Color.decode(Colors.SUB_PANEL));
        this.setBounds(0,0,500,500);
        this.setLayout(null);
        add(backButton);
    }

    public void setTextOfTextField(String text){
        textField.setText(text);
    }

    public void setTextOfLabel(String text){
        label.setText(text);
    }

    public void addStringListener(StringListener stringListener) {
        this.stringListeners.add(stringListener);
    }

    public void setDatePicker(String text){
        scrollPane.setVisible(false);
        datePicker.getJFormattedTextField().setText(text);
        datePicker.setVisible(true);
    }

    public void listenMe(String name) throws IOException {
        for (StringListener stringListener : stringListeners) {
            stringListener.stringEventOccurred(name);
        }
    }

    public void saveButtonAction(){
        switch (label.getText()) {
            case Texts.BIRTH -> changeInfoEvent.setBirth(LocalDate.of(
                    datePicker.getModel().getYear(),
                    datePicker.getModel().getMonth() + 1,
                    datePicker.getModel().getDay()));
            case Texts.BIOGRAPHY_INFO_PANEL, Texts.EMAIL_INFO_PANEL ->
                    changeInfoEvent.setEmail(textField.getText());
            case Texts.FIRSTNAME_INFO_PANEL ->
                    changeInfoEvent.setFirstname(textField.getText());
            case Texts.LASTNAME_INFO_PANEL ->
                    changeInfoEvent.setLastname(textField.getText());
            case Texts.USERNAME_INFO_PANEL ->
                    changeInfoEvent.setUsername(textField.getText());
            case Texts.PASSWORD_INFO_PANEL ->
                    changeInfoEvent.setPassword(textField.getText());
            case Texts.PHONE_INFO_PANEL ->
                    changeInfoEvent.setPhone(textField.getText());
        }
        changeInfoEvent.setType(label.getText());
        eventListener.listen(changeInfoEvent);
    }

    public void resetPanel(){
        datePicker.setVisible(false);
        scrollPane.setVisible(true);
        invalidLabel.setVisible(false);
    }

    @Override
    public void changeState() {
        resetPanel();
        removeAll();
        if(OfflineController.IS_ONLINE) {
            addPanels();
        }
        else{
            add(connectingLabel);
            add(backButton);
            repaint();
            revalidate();
        }
    }

    public void addPanels(){
        this.add(scrollPane);
        this.add(saveButton);
        this.add(backButton);
        this.add(invalidLabel);
        this.add(datePicker);
        this.add(label);
        repaint();
        revalidate();
    }

    public void setInfo(){
        if(OfflineController.IS_ONLINE){
            addPanels();
        }
        else{
            add(connectingLabel);
            repaint();
            revalidate();
        }
    }

    @Override
    public void getAnswer(RegistrationResponse registrationResponse) {
        if(!registrationResponse.getAnswer().equals("ok")){
            invalidLabel.setText(registrationResponse.getAnswer());
            invalidLabel.setVisible(true);
            repaint();
            revalidate();
            return;
        }
        resetPanel();
        changeInfoController.changeInfo(label.getText(), changeInfoEvent);
        try {
            listenMe("save");
            if(label.getText().equals(Texts.USERNAME_INFO_PANEL)
                    || label.getText().equals(Texts.BIOGRAPHY_INFO_PANEL)){
                listenMe(label.getText());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
