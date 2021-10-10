package view.postView.message;

import resources.Colors;
import resources.Fonts;
import resources.Texts;
import view.listeners.StringListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

public class MessageView extends JPanel {

    private int messageId;
    private int fileMenuObjects;
    private final JLabel usernameLabel;
    private StringListener stringListener;
    private final JLabel messageImageKeeper;
    private final JTextArea messageText;
    private final JMenuBar menuBar;
    private final JLabel timeLabel;
    private final JLabel profileImageKeeper;
    private final JMenu fileMenu;
    private final JMenuItem editItem;
    private final JMenuItem deleteItem;

    public MessageView() {
        profileImageKeeper = new JLabel();
        profileImageKeeper.setBounds(10,10,60,60);
        //
        usernameLabel = new JLabel();
        usernameLabel.setBounds(75,26,400,40);
        usernameLabel.setFont(Fonts.USERNAME_FONT);
        timeLabel = new JLabel();
        timeLabel.setFont(Fonts.USERNAME_FONT);
        //
        messageImageKeeper = new JLabel();
        //
        messageText = new JTextArea();
        messageText.setBackground(Color.decode(Colors.POST_VIEW_MOUSE_HOVER));
        messageText.setFont(Fonts.LABEL_FONT_INFO_PANEL);
        messageText.setLayout(null);
        //
        menuBar = new JMenuBar();
        fileMenu = new JMenu(Texts.MENUBAR);
        fileMenu.setBackground(Color.decode(Colors.POST_VIEW));
        menuBar.add(fileMenu);
        menuBar.setBounds(620,10,50,40);
        menuBar.setBackground(Color.decode(Colors.POST_VIEW));
        editItem = new JMenuItem(Texts.EDIT);
        deleteItem = new JMenuItem(Texts.DELETE);
        editItem.setBackground(Color.decode(Colors.BLOCK));
        deleteItem.setBackground(Color.decode(Colors.BLOCK));
        fileMenu.add(editItem);
        fileMenu.add(deleteItem);
        fileMenuObjects = 2;
        //
        editItem.addActionListener(e -> {
            try {
                listenMe("edit");
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });
        deleteItem.addActionListener(e -> {
            try {
                listenMe("delete");
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });
        //
        this.setLayout(null);
        this.setBackground(Color.decode(Colors.POST_VIEW_MOUSE_HOVER));
        this.add(profileImageKeeper);
    }

    public void setStringListener(StringListener stringListener) {
        this.stringListener = stringListener;
    }

    public void listenMe(String name) throws IOException {
        stringListener.stringEventOccurred(name);
    }

    public void setInfo(String messageText, Icon messageImage, String username
                       , String time, Icon profileImage){
        JLabel label = new JLabel(Texts.TextHandling(messageText,"common"));
        int h = 10;
        for (int i = 0; i < messageText.length(); i++) {
            if(messageText.charAt(i) == '\n'){
                h+=30;
            }
        }
        this.messageText.setText(messageText);
        this.messageText.setLineWrap(true);
        this.messageText.setBounds(20,80,
                Math.min((int)label.getPreferredSize().getWidth(),660),
                Math.max((int)label.getPreferredSize().getHeight()+10,h));
        messageImageKeeper.setIcon(messageImage);
        usernameLabel.setText(username);
        timeLabel.setText(time);
        this.profileImageKeeper.setIcon(profileImage);
    }

    public int getWidth(){
        int width;
        if(messageImageKeeper.getIcon() != null ) {
            messageImageKeeper.setBounds(20, 90 + messageText.getHeight(),
                    500, 200);
            width = 550;
        }
        else{
            width = messageText.getWidth()+70;
        }
        return width;
    }

    public int setSizes(int x, int y){
        int height;
        int width;
        if(messageImageKeeper.getIcon() != null ) {
            messageImageKeeper.setBounds(20, 90 + messageText.getHeight(),
                    500, 200);
            height = 330 + messageText.getHeight();
            width = 550;
        }
        else{
            height = 100 + messageText.getHeight();
            width = messageText.getWidth()+70;
        }
        this.setBounds
                (x,y,width,height);
        timeLabel.setBounds(width - 60,height-30,70,30);
        menuBar.setBounds(width - 60,10,50,30);
        return height;
    }

    public void setDeleteItem(boolean deletable) {
        if (!deletable) {
            fileMenu.remove(deleteItem);
            fileMenuObjects --;
            repaint();
            revalidate();
        }
    }

    public void setEditItem(boolean editable) {
        if (!editable) {
            fileMenu.remove(editItem);
            fileMenuObjects--;
            repaint();
            revalidate();
        }
    }

    public void addComponents() {
        this.add(usernameLabel);
        this.add(messageText);
        if (messageImageKeeper.getIcon() != null) {
            this.add(messageImageKeeper);
        }
        if(fileMenuObjects != 0)
        {this.add(menuBar);}
        this.add(timeLabel);
        messageText.setEditable(false);
    }

    public int getMessageId() {
        return messageId;
    }

    public JTextArea getMessageText() {
        return messageText;
    }

    public void setMessageId(int messageId) {
        this.messageId = messageId;
    }
}
