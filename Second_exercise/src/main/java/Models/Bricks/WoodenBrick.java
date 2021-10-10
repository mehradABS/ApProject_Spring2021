package Models.Bricks;

import Logic.GamePanel;
import Models.Bricks.MyBrick;
import Models.MyBall;

import javax.swing.*;
import java.io.IOException;

public class WoodenBrick extends MyBrick {
    public WoodenBrick(ImageIcon icon) throws IOException {
        super(icon);
    }

    public void brick_break(MyBall ball, GamePanel panel) {
        if (ball.IsNotFireBall()) {
            live--;
            if (live > 0) {
                this.setIcon(new ImageIcon
                        ("finalproject\\photo\\brick99.png"));
                this.setImageIconNumber(99);
            } else {
                panel.getPlayer().setScore(panel.getPlayer().getScore()+1);
                panel.removeComponent(this);
                panel.removeBrick(this);
                panel.setBrickCounter(panel.getBrickCounter() - 1);
                file.delete();
                removeFile=true;
            }
        }
        else{
            panel.getPlayer().setScore(panel.getPlayer().getScore()+1);
            panel.removeComponent(this);
            panel.removeBrick(this);
            panel.setBrickCounter(panel.getBrickCounter() - 1);
            file.delete();
            removeFile=true;
        }
        try {
            Save();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
