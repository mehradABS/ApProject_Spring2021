package Models.Bricks;

import Models.Bricks.MyBrick;

import javax.swing.*;
import java.io.IOException;

public class InvisibleBrick extends MyBrick {

    public InvisibleBrick(ImageIcon icon) throws IOException {
        super(icon);
        this.setVisible(false);
    }
}