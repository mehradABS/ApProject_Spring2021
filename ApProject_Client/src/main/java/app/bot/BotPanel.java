package app.bot;

import controller.OfflineController;
import events.BotEvent;
import network.EventListener;
import resources.Colors;
import resources.Fonts;
import resources.Texts;
import responses.BotResponse;
import responses.visitors.BotResponseVisitor;
import responses.visitors.ResponseVisitor;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.HashMap;

public class BotPanel extends JPanel implements BotResponseVisitor {

    private final JTextField urlField;
    private final JTextField botNameField;
    private final JLabel invalidLabel;
    private final EventListener eventListener;


    public BotPanel(EventListener eventListener, HashMap<String, ResponseVisitor>
                    visitors) {
        visitors.put("BotResponseVisitor", this);
        this.eventListener = eventListener;
        botNameField = new JTextField();
        botNameField.setBounds(140,50,150,40);
        botNameField.setBackground(Color.decode(Colors.BUTTONS_COLOR));
        botNameField.setFont(Fonts.BUTTONS_FONT);
        //
        JLabel botNameLabel = new JLabel("botName:");
        botNameLabel.setBounds(40,50,130,40);
        botNameLabel.setForeground(Color.decode(Colors.LABEL_COLOR));
        botNameLabel.setFont(Fonts.Label_FONT);
        //
        urlField = new JPasswordField();
        urlField.setBounds(140,100,150,40);
        urlField.setBackground(Color.decode(Colors.BUTTONS_COLOR));
        urlField.setFont(Fonts.BUTTONS_FONT);
        //
        JLabel urlLabel = new JLabel("url:");
        urlLabel.setBounds(90,100,130,40);
        urlLabel.setForeground(Color.decode(Colors.LABEL_COLOR));
        urlLabel.setFont(Fonts.Label_FONT);
        //
        JButton createButton = new JButton("create");
        createButton.setBounds(140,200,150,40);
        createButton.setBackground(Color.decode(Colors.BUTTONS_COLOR));
        createButton.setFocusable(false);
        createButton.setFont(Fonts.BUTTONS_FONT);
        createButton.addActionListener(e-> createButtonAction());
        //
        invalidLabel = new JLabel(Texts.INVALID_LOGIN);
        invalidLabel.setBounds(140,300,400,40);
        invalidLabel.setForeground(Color.decode(Colors.LABEL_COLOR));
        invalidLabel.setFont(Fonts.Label_FONT);
        invalidLabel.setVisible(false);
        //
        setLayout(null);
        setBounds(260,50,400,400);
        setBackground(Color.decode(Colors.MENU_PANEL_COLOR));
        add(botNameField);
        add(urlLabel);
        add(urlField);
        add(botNameLabel);
        add(invalidLabel);
        add(createButton);
    }

    public void createButtonAction(){
        BotEvent event = new BotEvent();
        event.setUrl(urlField.getText());
        event.setBotName(botNameField.getText());
        eventListener.listen(event);
    }

    @Override
    public void getAnswer(BotResponse botResponse) {
        if("ok".equals(botResponse.getAnswer())){
            try {
                OfflineController.getContext().getUsers().set(botResponse.getBot());
            } catch (IOException e) {
                e.printStackTrace();
            }
            resetPanel();
        }
        else{
            invalidLabel.setText(botResponse.getAnswer());
            invalidLabel.setVisible(true);
        }
    }

    public void resetPanel(){
        invalidLabel.setText("");
        invalidLabel.setVisible(false);
        urlField.setText("");
        botNameField.setText("");
    }
}