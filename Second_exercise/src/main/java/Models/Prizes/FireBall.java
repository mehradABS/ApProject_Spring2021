package Models.Prizes;

import Logic.GamePanel;
import Models.MyBall;
import Models.MyBoard;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.LinkedList;

public class FireBall extends Prize {
    private static final int Prize_Id= 1;
    public FireBall(){}
    FireBall(int YVelocity, ImageIcon icon, int x, int y,
             LinkedList<MyBall> balls, MyBoard board) {
        super(YVelocity, icon, x, y,board,balls);
        usingTime =new Timer(15000,this::actionPerformed);
    }


    @Override
    public  void usePrize() {
        addGotPrizes(1);
        GamePanel.gotPrizesId.add(this.getId());
        GamePanel.gotPrizes.add(this);
        for (MyBall ball:balls) {
            ball.setBallImage(ball.getFire_ball().getImage());
            ball.setFireBall(true);
        }
        usingTime.start();
        setUsingTimerState(true);
    }

    public void actionPerformed(ActionEvent e) {
        removeGotPrize(this);
        boolean setImage= GotPrizeNotContains(1);

        if(setImage){
        for (MyBall ball:balls) {
            ball.setBallImage(ball.getCommon_ball().getImage());
            ball.setFireBall(false);
          }
        }
        usingTime.stop();
        setUsingTimerState(false);
    }

    public int getPrize_Id(){
        return Prize_Id;
    }

    @Override
    public Prize prizeBuilder(int YVelocity,ImageIcon icon,
                              int x,int y,MyBoard board,
                              LinkedList<MyBall> balls) {
        return new FireBall( YVelocity,  icon,  x,  y,
        balls,  board);
    }
}