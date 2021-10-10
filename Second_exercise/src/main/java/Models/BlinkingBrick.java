package Models;

import Logic.GamePanel;
import Models.Bricks.MyBrick;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class BlinkingBrick extends MyBrick implements ActionListener{
    private final Timer timer;
    private boolean visible=true;
    public BlinkingBrick(ImageIcon icon) throws IOException {
        super(icon);
        timer = new Timer(1000, this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        visible=!visible;
        this.setVisible(visible);
    }

    public boolean IsVisible(){
        return visible;
    }

    public void brick_break(MyBall ball, GamePanel panel){
        panel.getPlayer().setScore(panel.getPlayer().getScore()+1);
        panel.removeComponent(this);
        panel.removeBrick(this);
        panel.setBrickCounter(panel.getBrickCounter()-1);
        file.delete();
        removeFile=true;
        timer.stop();
    }

    public void start(){
        timer.start();
    }

    public void stop(){
        timer.stop();
    }

}
