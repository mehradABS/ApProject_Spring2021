package view.postView.tweetHistory;

import resources.Colors;
import resources.Fonts;
import resources.Images;
import resources.Texts;
import view.listeners.StringListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class PostView extends JPanel {

    protected boolean menuBarVisibility;
    protected final List<StringListener> stringListeners;
    protected final JTextArea tweetText;
    protected final JLabel like;
    protected final JButton likeButton;
    protected final JLabel dislike;
    protected final JButton dislikeButton;
    protected final JLabel share;
    protected final JButton shareButton;
    protected final JLabel reply;
    protected final JButton replyButton;
    protected final JLabel tweetImageKeeper;
    protected final JLabel usernameLabel;
    protected final JLabel profileImage;
    protected int tweetId;
    private final JLabel retweetLabel;
    private final JButton retweetButton;


    public PostView() {
        stringListeners = new LinkedList<>();
        //
        profileImage =new JLabel();
        profileImage.setBounds(10,10,60,60);
        //
        usernameLabel = new JLabel();
        usernameLabel.setBounds(80,30,400,40);
        usernameLabel.setForeground(Color.decode(Colors.POST_VIEW_LABELS));
        usernameLabel.setFont(Fonts.USERNAME_FONT);
        //
        tweetImageKeeper = new JLabel();
        //
        tweetText = new JTextArea();
        tweetText.setLineWrap(true);
        tweetText.setBackground(Color.decode(Colors.POST_VIEW));
        tweetText.setEditable(false);
        tweetText.setFont(Fonts.LABEL_FONT_INFO_PANEL);
        tweetText.setLayout(null);
        //
        like = new JLabel(Images.LIKE_ICON);
        likeButton = new JButton();
        likeButton.setBackground(Color.decode(Colors.POST_VIEW));
        addMouseListener(likeButton,"like");
        //
        dislike = new JLabel(Images.DISLIKE_ICON);
        dislikeButton = new JButton();
        dislikeButton.setBackground(Color.decode(Colors.POST_VIEW));
        addMouseListener(dislikeButton,"dislike");
        //
        share = new JLabel(Images.SHARE_ICON);
        shareButton = new JButton();
        shareButton.setBackground(Color.decode(Colors.POST_VIEW));
        addMouseListener(shareButton,"share");
        //
        reply = new JLabel(Images.REPLY_ICON);
        replyButton = new JButton();
        replyButton.setBackground(Color.decode(Colors.POST_VIEW));
        addMouseListener(replyButton,"reply");
        //
        this.setLayout(null);
        this.setBackground(Color.decode(Colors.POST_VIEW));
        addMouseListener(this);
        retweetLabel = new JLabel(Images.RETWEET_ICON);
        retweetButton = new JButton();
        retweetButton.setBackground(Color.decode(Colors.POST_VIEW));
        addMouseListener(retweetButton,"retweet");
    }

    public void setSizes(int x, int y){
        int height;
        if(tweetImageKeeper.getIcon() != null ) {
            tweetImageKeeper.setBounds(20, 90 + tweetText.getHeight(),
                    500, 200);
            height = 330 + tweetText.getHeight();
        }
        else{
            height = 100 + tweetText.getHeight();
        }
        reply.setBounds(0,0,80,50);
        replyButton.setBounds(20,height,80,50);
        replyButton.add(reply);
        share.setBounds(0,0,60,50);
        shareButton.setBounds(150,height,70,50);
        shareButton.add(share);
        like.setBounds(0,0,80,50);
        likeButton.setBounds(280,height,80,50);
        likeButton.add(like);
        dislike.setBounds(0,0,80,50);
        dislikeButton.setBounds(410,height,80,50);
        dislikeButton.add(dislike);
        retweetLabel.setBounds(0,0,80,50);
        retweetButton.setBounds(540,height,80,50);
        retweetButton.add(retweetLabel);
        this.setBounds(x,y,700,height+100);
    }

    public void setInfo(String tweetText, Icon tweetImage,
                         String username, Icon profileImage
                         , int like, int dislike, int replies){
        JLabel label = new JLabel(Texts.TextHandling(tweetText,"common"));
        int h = 10;
        for (int i = 0; i < tweetText.length(); i++) {
            if(tweetText.charAt(i) == '\n'){
                h+=40;
            }
        }
        this.tweetText.setText(tweetText);
        this.tweetText.setBounds(20,80,
                Math.min((int)label.getPreferredSize().getWidth(),660),
                Math.max((int)label.getPreferredSize().getHeight(), h));

        tweetImageKeeper.setIcon(tweetImage);

        usernameLabel.setText(username);

        this.profileImage.setIcon(profileImage);

        this.like.setText(String.valueOf(like));
        this.dislike.setText(String.valueOf(dislike));

        this.reply.setText(String.valueOf(replies));
    }

    public void setMoreIcon(boolean visible){
        retweetButton.setVisible(true);
        repaint();
        revalidate();
    }

    public boolean getMoreIconVisibility() {
        return false;
    }

    public JLabel getLike() {
        return like;
    }

    public JLabel getDislike() {
        return dislike;
    }

    public JLabel getShare() {
        return share;
    }

    public JLabel getReply() {
        return reply;
    }

    public JTextArea getTweetText() {
        return tweetText;
    }

    public JLabel getTweetImageKeeper() {
        return tweetImageKeeper;
    }

    public JLabel getUsernameLabel() {
        return usernameLabel;
    }

    public JLabel getProfileImage() {
        return profileImage;
    }

    public void setLikeText(String likeNumber, String dislikeNumber){
        like.setText(likeNumber);
        dislike.setText(dislikeNumber);
        likeButton.setEnabled(false);
        dislikeButton.setEnabled(true);
        repaint();
        revalidate();
    }

    public void setDisLikeText(String likeNumber, String dislikeNumber){
        like.setText(likeNumber);
        dislike.setText(dislikeNumber);
        likeButton.setEnabled(true);
        dislikeButton.setEnabled(false);
        repaint();
        revalidate();
    }

    public void setTweetId(int tweetId) {
        this.tweetId = tweetId;
    }

    public int getTweetId() {
        return tweetId;
    }

   public void setReplyText(String repliesNumber){
       this.reply.setText(repliesNumber);
       repaint();
       revalidate();
    }

    public void addComponent(boolean like){
       this.add(profileImage);
       this.add(usernameLabel);
       this.add(tweetText);
       if(tweetImageKeeper.getIcon() != null){
           this.add(tweetImageKeeper);
       }
       this.add(replyButton);
       this.add(shareButton);
       this.add(this.likeButton);
       this.add(dislikeButton);
       if(like){
           this.likeButton.setEnabled(false);
       }
       else{
           dislikeButton.setEnabled(false);
       }
        this.add(retweetButton);
    }

    public void listenMe(String name) throws IOException {
        for(StringListener stringListener: stringListeners){
            stringListener.stringEventOccurred(name);
        }
    }

    public void addStringListener(StringListener stringListener){
        stringListeners.add(stringListener);
    }

    public void addMouseListener(JButton label, String name){

       label.addActionListener(e -> {
           try {
               listenMe(name+tweetId);
           } catch (IOException ioException) {
               ioException.printStackTrace();
           }
       });
    }


    public void addMouseListener(JPanel panel){
        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                //TODO
                try {
                    listenMe(String.valueOf(tweetId));
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
                panel.setBackground(Color.decode(Colors.POST_VIEW));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                panel.setBackground(Color.decode(Colors.POST_VIEW));
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                panel.setBackground(Color.decode(Colors.POST_VIEW_MOUSE_HOVER));
            }
        });
    }

    public void setBlockItem(boolean active){

    }

    public void setRetweetText(String retweetNumber){
        retweetLabel.setText(retweetNumber);
        retweetLabel.repaint();
        retweetLabel.revalidate();
    }


    public int getRetweetText(){
        return Integer.parseInt(retweetLabel.getText());
    }
}
