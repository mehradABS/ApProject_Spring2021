package Logic;

import Models.Bricks.MyBrick;
import Models.Prizes.Prize;

import javax.swing.*;
import java.io.IOException;

public class Pause {
    private JButton menu1;
    private GamePanel panel;
    private final JButton restart;
    private final JButton menu;
    private final Father frame;
    public Pause(Father frame){
        this.frame=frame;
        restart = new JButton("restart");
        menu = new JButton("menu");
        menu1=new JButton("menu");
        menu1.setFocusable(false);
        restart.setFocusable(false);
        menu.setFocusable(false);
        menu.setBounds(900,400,250,40);
        menu1.setBounds(900,400,250,40);
        restart.setBounds(900,500,250,40);
        restart.addActionListener(e->{
            try {
                panel.restart();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });
        menu.addActionListener(e->{
            frame.backToMenu();
        });
         menu1.addActionListener(e->{
             frame.backToMenu();
         });
    }

    public JButton getRestart() {
        return restart;
    }

    public JButton getMenu() {
        return menu;
    }

    public GamePanel getPanel() {
        return panel;
    }

    public void setPanel(GamePanel panel) {
        this.panel = panel;
    }

    public void pause(){
        for (Prize prize:panel.getPrizes()) {
            prize.MoveTimer(false);
            prize.UsingTime(false);
        }
        for (MyBrick brick: panel.getBricks()) {
            brick.stop();
        }
        panel.changeRegeneratingBrick(false);
        panel.changeRepaintTimer(false);
        panel.setPauseGame(true);
    }

    public Father getFrame() {
        return frame;
    }

    public void Continue(){
        for (Prize prize:panel.getPrizes()) {
            prize.MoveTimer(true);
            prize.UsingTime(true);
        }
        for (MyBrick brick: panel.getBricks()) {
            brick.start();
        }
        panel.changeRegeneratingBrick(true);
        panel.changeRepaintTimer(true);
        panel.setPauseGame(false);
        panel.remove(menu);
        panel.remove(restart);

    }

    public JButton getMenu1() {
        return menu1;
    }

    public void GameOver(){

     }
}