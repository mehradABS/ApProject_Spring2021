package Models.Prizes;


import Logic.GamePanel;
import Models.MyBall;
import Models.MyBoard;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.LinkedList;

public class FastBall extends Prize {
    private static final int Prize_Id= 5;
    public FastBall(){}
    FastBall(int YVelocity, ImageIcon icon, int x, int y, MyBoard board, LinkedList<MyBall> balls) {
        super(YVelocity, icon, x, y, board, balls);
        usingTime =new Timer(15000,this::actionPerformed);
    }

    @Override
    public void usePrize() {
        if(GotPrizeNotContains(5)){
        for (MyBall ball:balls) {
            try {
                ball.changVelocity(true);
            } catch (IOException e) {
                e .printStackTrace();
            }
        }
        }
        addGotPrizes(5);
        GamePanel.gotPrizesId.add(this.getId());
        GamePanel.gotPrizes.add(this);
        usingTime.start();
        setUsingTimerState(true);
    }

    public void actionPerformed(ActionEvent e) {
        removeGotPrize(this);
        boolean setVelocity= GotPrizeNotContains(6) && GotPrizeNotContains(5);
        if(setVelocity){
            for (MyBall ball:balls) {
                try {
                    ball.resetVelocity();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        }
        usingTime.stop();
        setUsingTimerState(false);
    }

    @Override
    public int getPrize_Id() {
        return Prize_Id;
    }

    @Override
    public Prize prizeBuilder(int YVelocity, ImageIcon icon, int x, int y,
                              MyBoard board, LinkedList<MyBall> balls) {
        return new FastBall( YVelocity,  icon, x,  y,
         board,  balls);
    }
}
