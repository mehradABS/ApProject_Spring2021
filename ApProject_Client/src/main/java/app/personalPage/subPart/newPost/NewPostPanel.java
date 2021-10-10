package app.personalPage.subPart.newPost;


import controller.OfflineController;
import controller.OnlinePanels;
import controller.fileHandling.CheckPhotoController;
import events.messages.NewTweetEvent;
import network.EventListener;
import network.ImageSender;
import resources.Colors;
import resources.Fonts;
import resources.Images;
import resources.Texts;
import responses.visitors.ReceivingConfirmationResponseVisitor;
import responses.visitors.ResponseVisitor;
import view.listeners.StringListener;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;


public class NewPostPanel extends JPanel implements ReceivingConfirmationResponseVisitor
, OnlinePanels {

    protected final JTextArea tweetText;
    protected final JButton tweetButton;
    protected final JLabel defaultText;
    protected final JLabel pngLabel;
    protected final JFileChooser chooser;
    private final NewTweetEvent newTweetEvent = new NewTweetEvent();
    protected final EventListener eventListener;
    protected final Panel imagePanel;
    protected final JLabel imageKeeper;
    protected StringListener stringListener;
    protected String encodedImage = "null";
    protected final CheckPhotoController checkPhotoController
            = new CheckPhotoController();
    protected final JButton addPhotoButton;
    protected final JScrollPane tweetTextPanel;
    protected final JLabel connectingLabel;

    public NewPostPanel(EventListener eventListener, HashMap<String, ResponseVisitor>
                         visitors, String name) {
        visitors.put(name, this);
        //
        this.eventListener = eventListener;
        //
        connectingLabel = new JLabel(Texts.CONNECTING);
        connectingLabel.setBounds(150,0,500,200);
        connectingLabel.setFont(Fonts.WELCOME_LABEL_FONT);
        //
        imageKeeper = new JLabel();
        imageKeeper.setBounds(0,0,500,200);
        //
        JButton closeImageButton = new JButton(Images.CLOSE_ICON);
        closeImageButton.setBounds(10,10,45,45);
        closeImageButton.setBackground(Color.decode(Colors.BUTTONS_COLOR));
        closeImageButton.setFocusable(false);
        closeImageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    closeImageButtonAction();
                } catch (IOException ignored) {

                }
                imageKeeper.setIcon(null);
                imagePanel.setVisible(false);
            }
        });
        //
        imagePanel = new Panel();
        imagePanel.setBounds(100,200,500,180);
        imagePanel.setBackground(Color.decode(Colors.IMAGE_KEEPER_COLOR));
        imagePanel.add(imageKeeper);
        imageKeeper.add(closeImageButton);
        imagePanel.setVisible(false);
        //
        chooser = new JFileChooser();
        //
        pngLabel = new JLabel(Texts.PNG_LABEL);
        pngLabel.setBounds(280,300,400,40);
        pngLabel.setForeground(Color.decode(Colors.LABEL_COLOR));
        pngLabel.setFont(Fonts.Label_FONT);
        pngLabel.setVisible(false);
        //
        addPhotoButton = new JButton(Texts.ADD_PHOTO_FOR_TWEET);
        addPhotoButton.setBounds(50,440,150,40);
        addPhotoButton.setBackground(Color.decode(Colors.BUTTONS_COLOR));
        addPhotoButton.setFocusable(false);
        addPhotoButton.setFont(Fonts.BUTTONS_FONT);
        addPhotoButton.addActionListener(e-> addPhotoButtonAction());
        //
        tweetButton = new JButton(Texts.ADD_TWEET_BUTTON_TEXT);
        tweetButton.setBounds(200,440,150,40);
        tweetButton.setBackground(Color.decode(Colors.BUTTONS_COLOR));
        tweetButton.setFocusable(false);
        tweetButton.setFont(Fonts.BUTTONS_FONT);
        tweetButton.setEnabled(false);
        tweetButton.addActionListener(e -> {
            try {
                PostButtonAction();
            } catch (IOException ignored) {

            }
        });
        //
        tweetText = new JTextArea();
        tweetText.setLayout(null);
        tweetText.setFont(Fonts.TWEET_FONT);
        tweetText.setLineWrap(true);
        tweetTextPanel = new JScrollPane(tweetText,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        tweetTextPanel.setBounds(10,10,600,180);
        tweetText.setBounds(10,10,600,180);
        tweetText.setBackground(Color.decode(Colors.BUTTONS_COLOR));
        tweetText.setFont(Fonts.TWEET_FONT);
        defaultText = new JLabel(Texts.TWEET_DEFAULT_TEXT);
        defaultText.setBounds(0,-5,200,40);
        defaultText.setFont(tweetText.getFont());
        tweetText.add(defaultText);
        tweetText.getDocument().putProperty("name","TextArea");
        tweetText.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                defaultText.setVisible(false);
                tweetButton.setEnabled(true);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                   if(tweetText.getText().isEmpty()){
                       defaultText.setVisible(true);
                       tweetButton.setEnabled(false);
                   }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {

            }
        });
        this.setLayout(null);
        this.setBounds(0,200,700,500);
        this.setBackground(Color.decode(Colors.PERSONAL_PAGE_DOWN_PANEL));
    }

    public void setImage(ImageIcon icon){
        imageKeeper.setIcon(icon);
        imagePanel.setVisible(true);
    }

    public void PostButtonAction() throws IOException {
        newTweetEvent.setEncodedImage(encodedImage);
        newTweetEvent.setText(tweetText.getText());
        eventListener.listen(newTweetEvent);
    }

    public void resetPanel() throws IOException {
        if(imageKeeper.getIcon() != null){
            closeImageButtonAction();
            imageKeeper.setIcon(null);
        }
        imagePanel.setVisible(false);
        tweetButton.setEnabled(false);
        tweetText.setText("");
        encodedImage = "null";
        defaultText.setVisible(true);
    }

    public void setStringListener(StringListener stringListener) {
        this.stringListener = stringListener;
    }

    public void listenMe(String name) throws IOException {
        stringListener.stringEventOccurred(name);
    }

    public void closeImageButtonAction() throws IOException {
        checkPhotoController.deleteMessageImage(0);
    }

    public void addPhotoButtonAction(){
        pngLabel.setVisible(false);
        int res = chooser.showOpenDialog(null);
        if (res == JFileChooser.APPROVE_OPTION) {
            String filePath = checkPhotoController.checkPhoto(0,
                    chooser.getSelectedFile().getPath(), 500, 200);
            try {
                if(!"null".equals(filePath)) {
                    setImage(new ImageIcon(ImageIO.read(new File(filePath))));
                    imagePanel.repaint();
                    imagePanel.revalidate();
                    encodedImage = ImageSender.encodeImage(filePath);
                    repaint();
                    revalidate();
                }
            }
            catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    @Override
    public void gotoAnotherPanel() {
        try {
            checkPhotoController.deleteMessageImage(0);
            resetPanel();
            listenMe("tweetButton");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void changeState() {
        try {
            resetPanel();
        } catch (IOException e) {
            e.printStackTrace();
        }
        removeAll();
        if(OfflineController.IS_ONLINE) {
            addPanels();
        }
        else{
            add(connectingLabel);
            repaint();
            revalidate();
        }
    }

    public void addPanels(){
        this.add(tweetTextPanel);
        this.add(addPhotoButton);
        this.add(tweetButton);
        this.add(imagePanel);
        this.add(pngLabel);
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

    public String getEncodedImage(){
        return encodedImage;
    }
}