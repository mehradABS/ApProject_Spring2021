package Models.Prizes;

import Logic.GamePanel;
import Models.MyBall;
import Models.MyBoard;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.LinkedList;

public class SmallBoard extends Prize {
    private static final int Prize_Id= 4;
    public SmallBoard(){}
    SmallBoard(int YVelocity, ImageIcon icon,
               int x, int y, MyBoard board, LinkedList<MyBall> balls) {
        super(YVelocity, icon, x, y, board, balls);
        usingTime =new Timer(15000,this::actionPerformed);
    }

    @Override
    public void usePrize() {
        addGotPrizes(4);
        GamePanel.gotPrizesId.add(this.getId());
        GamePanel.gotPrizes.add(this);
        board.setBoardImage(board.getSmallBoard().getImage());
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

    @Override
    public int getPrize_Id() {
        return Prize_Id;
    }

    @Override
    public Prize prizeBuilder(int YVelocity, ImageIcon icon, int x, int y,
                              MyBoard board, LinkedList<MyBall> balls) {
        return new SmallBoard (YVelocity,  icon, x,  y,
        board,  balls);
    }
}
