package Logic;


import Models.*;
import Models.Bricks.MyBrick;
import Models.Prizes.Prize;

import java.awt.*;
import java.io.IOException;
import java.util.LinkedList;

public class Collision {

     public static void ball_board(MyBall ball, MyBoard board){
         double board_length=board.getBoardImage().getWidth(null);
         double board_center= (double) board.getX_board()+ board_length/2 - 20;
         boolean x=Math.abs(board_center - (int)ball.getX_ball())<board_length/2;
         boolean y=ball.getY_ball()+ball.getBallImage().getHeight(null)>
                 board.getY_board();
         if(x && y){
             if(ball.getYVelocity()>0) {
                 double constant;
                 if(ball.getX_ball()-board_center>0){
                 if((board_center+board_length/(4.5))>ball.getX_ball()){
                     constant=1;
                     }
                 else {
                     constant=-1;
                     }
                 }
                 else{
                  if(ball.getX_ball()>(board_center-board_length/(4.5))){
                      constant=1;
                  }
                   else {
                       constant=-1;
                  }
                 }
                 ball.setXVelocity(ball.getXVelocity() * constant);
                 ball.setYVelocity(ball.getYVelocity() * -1);
             }
         }
     }

     public static void ball_brick(MyBall ball, LinkedList<MyBrick> bricks
     , LinkedList<MyBrick> helpList, GamePanel panel) {
         helpList.addAll(bricks);
         for (MyBrick brick:helpList) {
             int brick_length = brick.getIcon().getIconWidth();
             int brick_center = brick.getX_brick() + brick_length / 2;
             boolean x = 2 * Math.abs(brick_center - ball.getX_ball()) < brick_length;
             boolean f1 = ball.getY_ball() - ball.getBallImage().getHeight(null)
                     < brick.getY_brick();
             boolean f2 = ball.getY_ball() + ball.getBallImage().getHeight(null)
                     > brick.getY_brick() - brick_length;
             boolean y = (f1 && f2);
             if(x && y && brick.IsVisible()){
                 brick.brick_break(ball,panel);
                 if(ball.IsNotFireBall()) {
                     ball.setYVelocity(ball.getYVelocity() * -1);
                 }
             }
         }
         helpList.removeAll(helpList);
     }

     public static void ball_panel(MyBall ball){
         if(ball.getX_ball() + 90 >=
                 Constant.PANEL_WIDTH-ball.getBallImage().getWidth(null)
                 || ball.getX_ball()<0) {
             if(!(ball.getX_ball()<0 && ball.getXVelocity()>0)
             || !(ball.getX_ball() + 90 >=
                     Constant.PANEL_WIDTH-ball.getBallImage().getWidth(null)
              && ball.getXVelocity()<0)) {
                 ball.setXVelocity(ball.getXVelocity() * -1);
             }
         }
         if(ball.getY_ball()>=
                 Constant.PANEL_HEIGHT-ball.getBallImage().getHeight(null)
                 || ball.getY_ball()<0) {
             if(!(ball.getY_ball()<0 && ball.getYVelocity()>0)) {
                 ball.setYVelocity(ball.getYVelocity() * -1);
             }
         }
     }

     public static boolean brick_panel(LinkedList<MyBrick> bricks){
         for (MyBrick brick:bricks) {
             if(brick.getY_brick()>=Constant.PANEL_HEIGHT)
                return true;
         }
         return false;
     }

     public static void prize_board(LinkedList<Prize> prizes, MyBoard board
     , LinkedList<Prize> helpList1, GamePanel panel) throws IOException {
         helpList1.addAll(prizes);
         Rectangle boardRec = new Rectangle(board.getX_board(),board.getY_board()
         ,board.getBoardImage().getWidth(null),
                 board.getBoardImage().getHeight(null));
         for (Prize prize :helpList1) {
             if(boardRec.intersects(prize.getBounds())){
                 panel.removePrize(prize);
                 panel.removeComponent(prize);
                 prize.getMoveTimer().stop();
                 prize.usePrize();
             }
         }
         helpList1.removeAll(helpList1);
     }

     public static void prize_panel(LinkedList<Prize> prizes, MyBoard board
             , LinkedList<Prize> helpList1, GamePanel panel){
         helpList1.addAll(prizes);
         for (Prize prize:helpList1) {
             if(prize.getY_prize()>Constant.PANEL_HEIGHT){
                 panel.removePrize(prize);
                 panel.removeComponent(prize);
                 prize.getMoveTimer().stop();
                 prize.getFile().delete();
                 prize.removeFile=true;
             }
         }
         helpList1.removeAll(helpList1);
     }

     public static boolean ball_DownOfPanel(LinkedList<MyBall> balls,
                                            LinkedList<MyBall>
                                            helpList2, GamePanel panel) {
         helpList2.addAll(balls);
         for (MyBall ball : helpList2) {
             if (ball.getY_ball() >=
                     Constant.PANEL_HEIGHT - ball.getBallImage()
                             .getHeight(null)) {
                     panel.removeBall(ball);
                     if(balls.size()==0){
                         helpList2.removeAll(helpList2);
                         for (Prize prize: panel.getPrizes()) {
                             prize.getFile().delete();
                             prize.removeFile=true;
                         }
                         return true;
                     }
             }
         }
         helpList2.removeAll(helpList2);
         return false;
     }
}
