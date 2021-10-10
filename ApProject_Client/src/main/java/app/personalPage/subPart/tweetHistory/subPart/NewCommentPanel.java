package app.personalPage.subPart.tweetHistory.subPart;

import app.personalPage.subPart.newPost.NewPostPanel;
import controller.OfflineController;
import events.messages.NewCommentEvent;
import network.EventListener;
import resources.Colors;
import resources.Images;
import resources.Texts;
import responses.visitors.ResponseVisitor;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.HashMap;

public class NewCommentPanel extends NewPostPanel {

    private final NewCommentEvent newCommentEvent;
    private int tweetId;
    private final JButton backButton;
    private String responseVisitorType;

    public NewCommentPanel(EventListener eventListener, HashMap<String, ResponseVisitor>
                            visitors, String newCommentResponseVisitor) {
        super(eventListener, visitors, newCommentResponseVisitor);
        //
        OfflineController.ONLINE_PANELS.add(this);
        //
        newCommentEvent = new NewCommentEvent();
        newCommentEvent.setResponseVisitorType(newCommentResponseVisitor);
        //
        defaultText.setText(Texts.COMMENT_J_TEXTAREA_DEFAULT_TEXT);
        //
        backButton = new JButton(Images.BACK_ICON);
        backButton.setBounds(650,10,45,45);
        backButton.setFocusable(false);
        backButton.setBackground(Color.decode(Colors.SUB_PANEL));
        backButton.addActionListener(e -> {
            try {
                resetPanel();
                listenMe("back");
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });
        //
        this.setBounds(0,100,700,500);
        this.setBackground(Color.decode(Colors.SUB_PANEL));
        this.add(backButton);
    }

    public void setTweetId(int tweetId) {
        this.tweetId = tweetId;
    }

    public int getTweetId() {
        return tweetId;
    }

    public void PostButtonAction() {
        newCommentEvent.setEncodedImage(encodedImage);
        newCommentEvent.setText(tweetText.getText());
        newCommentEvent.setTweetId(tweetId);
        eventListener.listen(newCommentEvent);
    }

   public void setDefaultText(String text){
        defaultText.setText(text);
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
            add(backButton);
            add(connectingLabel);
            repaint();
            revalidate();
        }
    }

    public void addPanels(){
        this.add(backButton);
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
            add(backButton);
            add(connectingLabel);
            repaint();
            revalidate();
        }
    }
}
