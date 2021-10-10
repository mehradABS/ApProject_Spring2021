package Models.Prizes;

import Logic.GamePanel;
import Models.MyBall;
import Models.MyBoard;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.LinkedList;

public class ConfusedBoard extends Prize {
    private static final int Prize_Id= 7;
    public ConfusedBoard(){}
    ConfusedBoard(int YVelocity, ImageIcon icon, int x, int y, MyBoard board, LinkedList<MyBall> balls) {
        super(YVelocity, icon, x, y, board, balls);
        usingTime =new Timer(15000,this::actionPerformed);
    }

    @Override
    public void usePrize() {
        addGotPrizes(7);
        GamePanel.gotPrizesId.add(this.getId());
        GamePanel.gotPrizes.add(this);
        board.setConfused(true);
        usingTime.start();
        setUsingTimerState(true);
    }
    public void actionPerformed(ActionEvent e) {
        removeGotPrize(this);
        boolean resetBoard= GotPrizeNotContains(7);
        if(resetBoard){
            board.setConfused(false);
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
        return new ConfusedBoard( YVelocity, icon, x,  y,
         board, balls);
    }
}
