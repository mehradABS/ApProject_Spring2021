package view.notificationView;


import resources.Colors;
import resources.Fonts;

import javax.swing.*;
import java.awt.*;

public class NotificationPanel extends JPanel {

    public void setInfo(int y,String text, int id) {
        JTextArea textArea = new JTextArea();
        textArea.setBackground(Color.decode(Colors.INFO_PANEL));
        textArea.setFont(Fonts.TWEET_FONT);
        textArea.setBounds(2,0,600,100);
        textArea.setText(text);
        textArea.setEditable(false);
        //
        this.setLayout(null);
        this.setBounds(0,y,800,200);
        this.setBackground(Color.decode(Colors.INFO_PANEL));
        this.add(textArea);
    }

    public NotificationPanel() {

    }
}