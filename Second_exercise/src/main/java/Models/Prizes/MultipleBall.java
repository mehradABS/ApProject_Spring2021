package Models.Prizes;

import Models.MyBall;
import Models.MyBoard;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.LinkedList;

public class MultipleBall extends Prize {
    private static final int Prize_Id= 2;
    public MultipleBall(){}
    MultipleBall(int YVelocity, ImageIcon icon,
                 int x, int y, MyBoard board, LinkedList<MyBall> balls) {
        super(YVelocity, icon, x, y, board, balls);
       usingTime=new Timer(1000,null);
    }

    @Override
    public void usePrize() throws IOException {
        MyBall ball1=new MyBall();
        ball1.setX_ball(board.getX_board() + board.getBoardImage().
                getWidth(null) / 2
                - 20);
        ball1.setY_ball(board.getY_board() - board.getBoardImage()
                .getHeight(null)
                - 1);
        ball1.setXVelocity(-5);
        ball1.setYVelocity(-5);
        MyBall ball2=new MyBall();
        ball2.setX_ball(board.getX_board() + board.getBoardImage().
                getWidth(null) / 2
                - 20);
        ball2.setY_ball(board.getY_board() - board.getBoardImage()
                .getHeight(null)
                - 1);
        ball2.setXVelocity(5);
        ball2.setYVelocity(6);
        addBall(ball1);
        addBall(ball2);
    }

    public  int getPrize_Id() {
        return Prize_Id;
    }

    @Override
    public Prize prizeBuilder(int YVelocity,ImageIcon icon,
                              int x,int y,MyBoard board,
                              LinkedList<MyBall> balls) {
        return new MultipleBall( YVelocity,  icon,
         x,  y,  board, balls);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        /////
    }
}
