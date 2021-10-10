package view.usersView;

import resources.Colors;
import resources.Images;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

public class UserPanelSelectable extends UserPanel{

    private JButton exitButton;

    public UserPanelSelectable() {
        super();
    }

    public void setPanel(int y, int width){
        super.setPanel(y, width);
        exitButton = new JButton(Images.CLOSE_ICON);
        exitButton.setBackground(Color.decode(Colors.SUB_PANEL));
        exitButton.setBounds(width - 60, 10 , 54, 45);
        exitButton.addActionListener(e -> {
            remove(exitButton);
            setClicked(false);
            repaint();
            revalidate();
            try {
                listenMe("exit");
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
                    setClicked(true);
                    listenMe(String.valueOf(userId));
                    add(exitButton);
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
}
