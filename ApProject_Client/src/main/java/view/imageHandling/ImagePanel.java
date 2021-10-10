package view.imageHandling;

import javax.swing.*;
import java.awt.*;

public  class ImagePanel extends JPanel {

    private JLabel profile;

    public ImagePanel() {
        super();
    }

    public void setProfile(ImageIcon profile) {
        if (profile == null) {
            this.profile = null;
        } else {
            this.profile = new JLabel();
            this.profile.setBounds(0, 0, 150, 150);
            this.profile.setIcon(profile);
        }
    }

    public JLabel getProfile() {
        return profile;
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        this.removeAll();
        if(profile != null)
            this.add(profile);
    }
}
