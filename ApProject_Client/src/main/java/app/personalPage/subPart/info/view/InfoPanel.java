package app.personalPage.subPart.info.view;

import app.personalPage.subPart.info.controller.LoadInfoController;
import controller.OfflineController;
import events.GetPasswordEvent;
import network.EventListener;
import resources.Colors;
import resources.Fonts;
import resources.Images;
import resources.Texts;
import responses.visitors.GetPasswordResponseVisitor;
import responses.visitors.ResponseVisitor;
import view.listeners.StringListener;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;


public class InfoPanel extends JPanel implements GetPasswordResponseVisitor {

    private final JPanel firstnamePanel;
    private final JPanel lastnamePanel;
    private final JPanel usernamePanel;
    private final JPanel passwordPanel;
    private final JPanel birthdayPanel;
    private final JPanel emailPanel;
    private final JPanel phonePanel;
    private final JPanel biographyPanel;
    private final LoadInfoController loadInfoController;
    private final JLabel firstnameLabel;
    private final JLabel lastnameLabel;
    private final JLabel usernameLabel;
    private final JLabel passwordLabel;
    private final JLabel emailLabel;
    private final JLabel phoneLabel;
    private final JLabel biographyLabel;
    private final JLabel birthdayLabel;
    private final ChangeInfoPanel changeInfoPanel;
    private final List<StringListener> stringListeners;
    private final EventListener eventListener;


    public InfoPanel(EventListener eventListener, HashMap<String,
            ResponseVisitor> visitors)  {
        this.eventListener = eventListener;
        visitors.put("GetPasswordResponseVisitor", this);
        stringListeners = new LinkedList<>();
        //
        changeInfoPanel = new ChangeInfoPanel(eventListener, visitors);
        OfflineController.ONLINE_PANELS.add(changeInfoPanel);
        changeInfoPanel.addStringListener(text -> {
            if(text.equals("back")){
                removeAll();
                addPanels();
                repaint();
                revalidate();
            }
            else if(text.equals("save")){
                removeAll();
                setInfoPanel();
                addPanels();
                repaint();
                revalidate();
            }
        });
        changeInfoPanel.addStringListener(text -> {
            if(text.equals("biography")){
                listenMe("biography");
            }
            else if(text.equals("username")){
                listenMe("username");
            }
        });
        //
        loadInfoController = new LoadInfoController();
        //
        JButton exitButton = new JButton(Images.CLOSE_ICON);
        exitButton.setBounds(450,10,45,45);
        exitButton.setFocusable(false);
        exitButton.setBackground(Color.decode(Colors.SUB_PANEL));
        exitButton.addActionListener(e -> {
            try {
                listenMe("exit");
            } catch (IOException ignored) {

            }
        });
        //
        firstnamePanel = new JPanel();
        firstnamePanel.setLayout(null);
        firstnamePanel.setBounds(0,0,500,67);
        firstnamePanel.setBackground(Color.decode(Colors.SUB_PANEL));
        firstnameLabel = new JLabel();
        firstnameLabel.setFont(Fonts.LABEL_FONT_INFO_PANEL);
        firstnamePanel.add(exitButton);
        //
        lastnamePanel = new JPanel();
        lastnamePanel.setLayout(null);
        lastnamePanel.setBackground(Color.decode(Colors.SUB_PANEL));
        lastnamePanel.setBounds(0,71,500,67);
        lastnameLabel = new JLabel();
        lastnameLabel.setFont(Fonts.LABEL_FONT_INFO_PANEL);
        //
        usernamePanel = new JPanel();
        usernamePanel.setLayout(null);
        usernamePanel.setBackground(Color.decode(Colors.SUB_PANEL));
        usernamePanel.setBounds(0,142,500,67);
        usernameLabel = new JLabel();
        usernameLabel.setFont(Fonts.LABEL_FONT_INFO_PANEL);
        //
        passwordPanel = new JPanel();
        passwordPanel.setLayout(null);
        passwordPanel.setBackground(Color.decode(Colors.SUB_PANEL));
        passwordPanel.setBounds(0,213,500,67);
        passwordLabel = new JLabel();
        passwordLabel.setFont(Fonts.LABEL_FONT_INFO_PANEL);
        //
        emailPanel = new JPanel();
        emailPanel.setLayout(null);
        emailPanel.setBackground(Color.decode(Colors.SUB_PANEL));
        emailPanel.setBounds(0,284,500,67);
        emailLabel = new JLabel();
        emailLabel.setFont(Fonts.LABEL_FONT_INFO_PANEL);
        //
        phonePanel = new JPanel();
        phonePanel.setLayout(null);
        phonePanel.setBackground(Color.decode(Colors.SUB_PANEL));
        phonePanel.setBounds(0,355,500,67);
        phoneLabel = new JLabel();
        phoneLabel.setFont(Fonts.LABEL_FONT_INFO_PANEL);
        //
        biographyPanel = new JPanel();
        biographyPanel.setLayout(null);
        biographyPanel.setBackground(Color.decode(Colors.SUB_PANEL));
        biographyPanel.setBounds(0,426,500,67);
        biographyLabel = new JLabel();
        biographyLabel.setFont(Fonts.LABEL_FONT_INFO_PANEL);
        //
        birthdayPanel = new JPanel();
        birthdayPanel.setLayout(null);
        birthdayPanel.setBackground(Color.decode(Colors.SUB_PANEL));
        birthdayPanel.setBounds(0,497,500,67);
        birthdayLabel = new JLabel();
        birthdayLabel.setFont(Fonts.LABEL_FONT_INFO_PANEL);
        //
        this.setLayout(null);
        this.setBackground(Color.decode(Colors.INFO_PANEL));
        this.setBounds(960,50,500, 572);
        addPanels();
     }

     public void setInfoPanel() throws IOException {
        if(!OfflineController.IS_ONLINE){
            String[] info = loadInfoController.getAllInfo();
            setInfo(info);
        }
        else{
            eventListener.listen(new GetPasswordEvent());
        }
    }

    public void setPassword(String password){
        try {
            String[] info = loadInfoController.getAllInfo();
            info[3] = password;
            setInfo(info);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setInfo(String[] info) throws IOException {
        firstnameLabel.setText("<html>"+Texts.FIRSTNAME_INFO_PANEL+"<br>"+info[0]
        +"</html>");
        firstnameLabel.setBounds(2,0,
                (int)firstnameLabel.getPreferredSize().getWidth()+10,
                70);
        firstnamePanel.add(firstnameLabel);
        //
        lastnameLabel.setText("<html>"+Texts.LASTNAME_INFO_PANEL+"<br>"+info[1]
                +"</html>");
        lastnameLabel.setBounds(2,0,
                (int)lastnameLabel.getPreferredSize().getWidth()+10,
                70);
        lastnamePanel.add(lastnameLabel);
        //
        usernameLabel.setText("<html>"+Texts.USERNAME_INFO_PANEL+"<br>"+info[2]
                +"</html>");
        usernameLabel.setBounds(2,0,
                (int)usernameLabel.getPreferredSize().getWidth()+10,
                70);
        usernamePanel.add(usernameLabel);
        //
        passwordLabel.setText("<html>"+Texts.PASSWORD_INFO_PANEL+"<br>"+info[3]
                +"</html>");
        passwordLabel.setBounds(2,0,
                (int)passwordLabel.getPreferredSize().getWidth()+10,
                70);
        passwordPanel.add(passwordLabel);
        //
        emailLabel.setText("<html>"+Texts.EMAIL_INFO_PANEL+"<br>"+info[4]
                +"</html>");
        emailLabel.setBounds(2,0,
                (int)emailLabel.getPreferredSize().getWidth()+10,
                70);
        emailPanel.add(emailLabel);
        //
        phoneLabel.setText("<html>"+Texts.PHONE_INFO_PANEL+"<br>"+info[5]
                +"</html>");
        phoneLabel.setBounds(2,0,
                (int)phoneLabel.getPreferredSize().getWidth()+10,
                70);
        phonePanel.add(phoneLabel);
        //
        biographyLabel.setText("<html>"+Texts.BIOGRAPHY_INFO_PANEL+"<br>"+info[6]
                +"</html>");
        biographyLabel.setBounds(2,0,
                (int)biographyLabel.getPreferredSize().getWidth()+10,
                70);
        biographyPanel.add(biographyLabel);
        //
        birthdayLabel.setText("<html>"+Texts.BIRTH_INFO_PANEL+"<br>"+info[7]
                +"</html>");
        birthdayLabel.setBounds(2,0,
                (int)birthdayLabel.getPreferredSize().getWidth()+10,
                70);
        birthdayPanel.add(birthdayLabel);
        addMouseListener(firstnamePanel, Texts.FIRSTNAME_INFO_PANEL, info[0]);
        addMouseListener(lastnamePanel, Texts.LASTNAME_INFO_PANEL, info[1]);
        addMouseListener(usernamePanel, Texts.USERNAME_INFO_PANEL, info[2]);
        addMouseListener(passwordPanel, Texts.PASSWORD_INFO_PANEL, info[3]);
        addMouseListener(emailPanel, Texts.EMAIL_INFO_PANEL, info[4]);
        addMouseListener(phonePanel, Texts.PHONE_INFO_PANEL, info[5]);
        addMouseListener(birthdayPanel, Texts.BIRTH_INFO_PANEL, info[7]);
        addMouseListener(biographyPanel, Texts.BIOGRAPHY_INFO_PANEL, info[6]);
        repaint();
        revalidate();
    }

    public void addMouseListener(JPanel panel, String text1,
                                 String text2){
        boolean finalIsBirth;
        finalIsBirth = text1.equals(Texts.BIRTH_INFO_PANEL);
        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                changeInfoPanel.setTextOfLabel(text1);
                if(!finalIsBirth) {
                    changeInfoPanel.setTextOfTextField(text2);
                }
                else {
                    changeInfoPanel.setDatePicker(text2);
                }
                removeAll();
                changeInfoPanel.setInfo();
                add(changeInfoPanel);
                panel.setBackground(Color.decode(Colors.SUB_PANEL));
                repaint();
                revalidate();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                panel.setBackground
                        (Color.decode(Colors.SUB_PANEL_MOUSE_HOVER));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                panel.setBackground(Color.decode(Colors.SUB_PANEL));
            }
        });
    }

    public void addPanels(){
        this.add(firstnamePanel);
        this.add(lastnamePanel);
        this.add(usernamePanel);
        this.add(passwordPanel);
        this.add(emailPanel);
        this.add(phonePanel);
        this.add(biographyPanel);
        this.add(birthdayPanel);
    }

    public void addStringListener(StringListener stringListener){
        stringListeners.add(stringListener);
    }

    public void listenMe(String name) throws IOException {
        for (StringListener stringListener:stringListeners){
            stringListener.stringEventOccurred(name);
        }
    }

   public void resetPanel(){
       removeAll();
       addPanels();
       repaint();
       revalidate();
   }
}
