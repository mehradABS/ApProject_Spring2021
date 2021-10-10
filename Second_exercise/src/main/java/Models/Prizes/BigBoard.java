package Models.Prizes;

import Logic.GamePanel;
import Models.MyBall;
import Models.MyBoard;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.LinkedList;

public class BigBoard extends Prize {
    private static final int Prize_Id= 3;
    public BigBoard(){

    }
    BigBoard(int YVelocity, ImageIcon icon,
             int x, int y, MyBoard board, LinkedList<MyBall> balls) {
        super(YVelocity, icon, x, y, board, balls);
        usingTime =new Timer(15000, this::actionPerformed);
    }

    @Override
    public void usePrize() {
         addGotPrizes(3);
         GamePanel.gotPrizesId.add(this.getId());
         GamePanel.gotPrizes.add(this);
         board.setBoardImage(board.getBigBoard().getImage());
         usingTime.start();
         setUsingTimerState(true);
    }

    public void actionPerformed(ActionEvent e){
        removeGotPrize(this);
        boolean setImage= GotPrizeNotContains(3) && GotPrizeNotContains(4);

        if(setImage) {
            board.setBoardImage(board.getCommonBoard().getImage());
        }
        usingTime.stop();
        setUsingTimerState(false);
    }

    public  int getPrize_Id() {
        return Prize_Id;
    }

    @Override
    public Prize prizeBuilder(int YVelocity,ImageIcon icon,
                              int x,int y,MyBoard board,
                              LinkedList<MyBall> balls) {
        return new BigBoard( YVelocity,  icon,
         x,  y,  board,  balls);
    }

}
