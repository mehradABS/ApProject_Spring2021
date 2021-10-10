package view.usersView;

import resources.Colors;
import resources.Fonts;
import view.listeners.StringListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

public class UserPanel extends JPanel {

    protected String name;
    protected boolean isClicked = false;
    protected StringListener stringListener;
    protected  JLabel profileImage;
    protected int userId = -10;
    protected JLabel username;


    public UserPanel(){
        super();
    }

    public void setPanel(int y,int width){
        profileImage = new JLabel();
        profileImage.setBounds(5,5,60,60);
        //
        username = new JLabel();
        username.setBounds(80,15,200,40);
        username.setFont(Fonts.USERNAME_FONT);
        //
        this.setLayout(null);
        this.setBackground(Color.decode(Colors.POST_VIEW));
        this.add(profileImage);
        this.add(username);
        this.setBounds(0,y,width,67);
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setProfileImage(Icon profileImage){
        this.profileImage.setIcon(profileImage);
    }

    public void setUsername(String username, boolean online){
        this.username.setText(username);
        if(online){
            this.username.setForeground(Color.RED);
        }
    }

    public void setStringListener(StringListener stringListener) {
        this.stringListener = stringListener;
    }

    public void listenMe(String name) throws IOException {
        stringListener.stringEventOccurred(name);
    }

    public void addMouseListener(JPanel panel){
        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                //TODO
                try {
                    setClicked(true);
                    if(userId != -10)
                        listenMe(String.valueOf(userId));
                    else
                        listenMe(name);
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
                repaint();
                revalidate();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if(!isClicked) {
                    panel.setBackground(Color.decode(Colors.POST_VIEW));
                    repaint();
                    revalidate();
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                if (!isClicked) {
                    panel.setBackground(Color.decode(Colors.POST_VIEW_MOUSE_HOVER));
                    repaint();
                    revalidate();
                }
            }
        });
    }

    public void setClicked(boolean clicked) {
        isClicked = clicked;
        if(!isClicked)
          this.setBackground(Color.decode(Colors.POST_VIEW));
        else
            setBackground(Color.decode(Colors.BUTTONS_COLOR));
    }


    public boolean isClicked() {
        return isClicked;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }
}