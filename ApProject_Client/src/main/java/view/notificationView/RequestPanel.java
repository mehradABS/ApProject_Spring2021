package view.notificationView;

import resources.Colors;
import resources.Images;
import view.listeners.StringListener;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class RequestPanel extends NotificationPanel{

    private StringListener stringListener;
    private int id;

    public void setInfo(int y, String text, int id) {
        super.setInfo(y,text, id);
        this.id = id;
        JButton acceptButton = new JButton(Images.ACCEPT);
        makeButton("accept",acceptButton);
        JButton deleteButton = new JButton(Images.DELETE);
        makeButton("delete",deleteButton);
        JButton closeButton = new JButton(Images.CLOSE_ICON);
        makeButton("reject",closeButton);
        acceptButton.setBounds(600,5,100,55);
        deleteButton.setBounds(600,60,100,55);
        closeButton.setBounds(600,110,100,55);
    }

    public void makeButton(String name, JButton button){
        button.setBackground(Color.decode(Colors.CHANGE_INFO_COLOR));
        button.setFocusable(false);
        add(button);
        button.addActionListener(e -> listenMe(name+ id));
    }

    public void setStringListener(StringListener stringListener) {
        this.stringListener = stringListener;
    }

    public void listenMe(String name)  {
        try {
            stringListener.stringEventOccurred(name);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public RequestPanel() {
        super();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
