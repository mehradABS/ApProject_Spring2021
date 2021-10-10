package app.personalPage.subPart.info.view;

import app.personalPage.subPart.info.controller.LoadInfoController;
import resources.Colors;
import resources.Fonts;
import resources.Images;
import resources.Texts;
import view.listeners.StringListener;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class DefaultInfoPanel extends JPanel {

    private final LoadInfoController loadInfoController;
    private final JLabel usernameLabel;
    private final JTextArea biographyText;
    private StringListener stringListener;
    private final JLabel image;

    public DefaultInfoPanel() {
        biographyText = new JTextArea();
        biographyText.setLineWrap(true);
        biographyText.setBackground(Color.decode(Colors.DEFAULT_INFO_PANEL));
        biographyText.setEditable(false);
        biographyText.setFont(Fonts.LABEL_FONT_INFO_PANEL);
        biographyText.setLayout(null);
        //
        JButton selectPhotoButton = new JButton(Images.SELECT_PHOTO_ICON);
        selectPhotoButton.setBounds(29,155,54,45);
        selectPhotoButton.setBackground(Color.decode(Colors.BUTTONS_COLOR));
        selectPhotoButton.setFocusable(false);
        //
        loadInfoController = new LoadInfoController();
        //
        image =new JLabel();
        image.setBounds(0,0,100,100);
        JPanel imagePanel = new JPanel();
        imagePanel.setLayout(null);
        imagePanel.setBounds(20,20,100,100);
        imagePanel.add(image);
        //
        usernameLabel = new JLabel();
        usernameLabel.setBounds(30,115,200,40);
        usernameLabel.setForeground(Color.decode(Colors.WELCOME_LABEL_COLOR));
        usernameLabel.setFont(Fonts.USERNAME_FONT);
        //
        JButton newTweetButton = new JButton(Images.NEW_TWEET_ICON);
        newTweetButton.setBounds(200,150,50,50);
        newTweetButton.setBackground(Color.decode(Colors.BUTTONS_COLOR));
        newTweetButton.addActionListener(e -> {
            try {
                listenMe("newTweet");
            } catch (IOException ignored) {

            }
        });
        //
        JButton tweetHistoryButton = new JButton(Images.TWEET_HISTORY_ICON);
        tweetHistoryButton.setBounds(400,150,50,50);
        tweetHistoryButton.setBackground(Color.decode(Colors.BUTTONS_COLOR));
        tweetHistoryButton.addActionListener(e -> {
            try {
                listenMe("tweetHistory");
            } catch (IOException i) {
                   i.printStackTrace();
            }
        });
        //
        JButton infoButton = new JButton(Images.INFO_ICON);
        infoButton.setBounds(600,150,50,50);
        infoButton.setBackground(Color.decode(Colors.BUTTONS_COLOR));
        infoButton.addActionListener(e -> {
            try {
                listenMe("info");
            } catch (IOException ignored) {

            }
        });
        //
        this.setLayout(null);
        this.setBounds(0,0,700,200);
        this.setBackground(Color.decode(Colors.DEFAULT_INFO_PANEL));
        this.add(imagePanel);
        this.add(usernameLabel);
        this.add(selectPhotoButton);
        this.add(newTweetButton);
        this.add(infoButton);
        this.add(tweetHistoryButton);
    }

    public void setInfo() throws IOException {
        String[] info = loadInfoController.getInfo();
        String path = loadInfoController.loadImage(100);
        image.setIcon(new ImageIcon(ImageIO.read(new File(path))));
        usernameLabel.setText(info[1]);
                if(!info[0].equals("null")){
            biographyText.setText((info[0]));
            JLabel label = new JLabel(Texts.TextHandling(info[0],"common"));
            biographyText.setBounds(140,20,
                    Math.min((int)label.getPreferredSize().getWidth(),540),
                    (int)label.getPreferredSize().getHeight());
            this.add(biographyText);
        }
        else {
            biographyText.setText("");
        }
    }

    public void setStringListener(StringListener stringListener) {
        this.stringListener = stringListener;
    }

    public void listenMe(String name) throws IOException {
        stringListener.stringEventOccurred(name);
    }
}
