package app.auth.view;


import app.auth.controller.ProfileImageController;
import app.auth.controller.RegisterController;
import events.auth.SetProfileAndBioEvent;
import network.ImageSender;
import resources.Colors;
import resources.Fonts;
import resources.Images;
import resources.Texts;
import responses.visitors.ReceivingConfirmationResponseVisitor;
import responses.visitors.ResponseVisitor;

import view.imageHandling.ImagePanel;
import view.listeners.StringListener;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import network.EventListener;

public class ManageBiography extends JPanel implements
        ReceivingConfirmationResponseVisitor {

    private final JFileChooser chooser;
    private final ImagePanel imagePanel;
    private final JTextArea biographyField;
    private final JLabel pngLabel;
    private StringListener stringListener;
    private final ProfileImageController ProfileImageController;
    private final SetProfileAndBioEvent setProfileAndBioEvent;
    private String filePath = "null";
    private final EventListener eventListener;

    public ManageBiography(EventListener eventListener
            , HashMap<String, ResponseVisitor> visitors) {
        visitors.put("ReceivingImageConfirmationResponseVisitor", this);
        //
        this.eventListener = eventListener;
        //
        ProfileImageController = new ProfileImageController();
        //
        setProfileAndBioEvent = new SetProfileAndBioEvent();
        //
        pngLabel = new JLabel(Texts.PNG_LABEL);
        pngLabel.setBounds(830,455,400,40);
        pngLabel.setForeground(Color.decode(Colors.LABEL_COLOR));
        pngLabel.setFont(Fonts.Label_FONT);
        pngLabel.setVisible(false);
        //
        JLabel biographyLabel = new JLabel(Texts.BIOGRAPHY);
       biographyLabel.setBounds(520,30,600,200);
       biographyLabel.setForeground(Color.decode(Colors.WELCOME_LABEL_COLOR));
       biographyLabel.setFont(Fonts.BIOGRAPHY_FONT);
       //
        biographyField = new JTextArea();
        biographyField.setLineWrap(true);
        JScrollPane scrollPane = new JScrollPane(biographyField,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setBounds(520,170,450,80);
        biographyField.setBounds(520,170,450,80);
       biographyField.setBackground(Color.decode(Colors.BUTTONS_COLOR));
       biographyField.setFont(Fonts.BUTTONS_FONT);
       //
        JButton selectPhotoButton = new JButton(Texts.SELECT_BUTTON);
       selectPhotoButton.setBounds(688,580,100,40);
       selectPhotoButton.setBackground(Color.decode(Colors.BUTTONS_COLOR));
       selectPhotoButton.setFocusable(false);
       selectPhotoButton.setFont(Fonts.BUTTONS_FONT);
       selectPhotoButton.addActionListener(e-> selectPhotoButtonAction());
       //
       chooser = new JFileChooser();
       //
       imagePanel = new ImagePanel();
       imagePanel.setLayout(null);
       imagePanel.setBackground(Color.decode(Colors.IMAGE_KEEPER_COLOR));
       imagePanel.setBounds(665,400,150,150);
       //
        JLabel profileImageLabel = new JLabel(Texts.PROFILE_IMAGE);
       profileImageLabel.setBounds(635,280,600,150);
       profileImageLabel.setForeground(Color.decode(Colors.WELCOME_LABEL_COLOR));
       profileImageLabel.setFont(Fonts.BIOGRAPHY_FONT);
       //

        JButton registerButton = new JButton(Texts.REGISTER_BUTTON_TEXT);
        registerButton.setBounds(619,650,250,40);
        registerButton.setBackground(Color.decode(Colors.BUTTONS_COLOR));
        registerButton.setFocusable(false);
        registerButton.setFont(Fonts.BUTTONS_FONT);
        registerButton.addActionListener(e-> registerButtonAction());
        //
        this.setLayout(null);
       this.setBounds(0,0,2000,800);
       this.add(biographyLabel);
       this.add(scrollPane);
       this.add(imagePanel);
       this.add(profileImageLabel);
       this.add(selectPhotoButton);
       this.add(registerButton);
       this.add(pngLabel);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2D = (Graphics2D) g;
        g2D.drawImage(Images.START_PANEL_IMAGE,0,0,null);
    }

    public void selectPhotoButtonAction(){
        pngLabel.setVisible(false);
        int res = chooser.showOpenDialog(null);
        if (res == JFileChooser.APPROVE_OPTION) {
            try {
                filePath = ProfileImageController
                        .checkFile(chooser.getSelectedFile().getPath(),
                                150, 150);
            } catch (IOException e) {
                e.printStackTrace();
            }
            if(filePath.equals("bad image")){
                pngLabel.setVisible(true);
            }
            else{
                try {
                    imagePanel.setProfile(new ImageIcon(ImageIO.read(new File(
                            filePath))));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                imagePanel.repaint();
                imagePanel.revalidate();
            }
            repaint();
            revalidate();
        }
    }

    public void registerButtonAction(){
        if(!biographyField.getText().equals("")){
            setProfileAndBioEvent.setBio(biographyField.getText());
        }
        else{
            setProfileAndBioEvent.setBio(Texts.DEFAULT_BIO_TEXT);
        }
        if(!"null".equals(filePath)) {
            setProfileAndBioEvent.setEncodedImage(ImageSender.encodeImage(filePath));
        }
        else {
            setProfileAndBioEvent.setEncodedImage(filePath);
        }
        eventListener.listen(setProfileAndBioEvent);
    }

    public void setStringListener(StringListener stringListener) {
        this.stringListener = stringListener;
    }

    public void listenMe(String name) throws IOException {
          stringListener.stringEventOccurred(name);
    }

    public void resetPanel(){
       pngLabel.setVisible(false);
       biographyField.setText(null);
       imagePanel.setProfile(null);
    }

    @Override
    public void gotoAnotherPanel() {
        new RegisterController().setBio(biographyField.getText());
        resetPanel();
        try {
            listenMe("register");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}