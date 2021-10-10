package Models.Bricks;

import Logic.GamePanel;
import Models.Bricks.MyBrick;
import Models.MyBall;
import Models.Prizes.*;

import javax.swing.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.Random;


public class PrizeBrick extends MyBrick {
    private static final HashMap<Integer, Prize> allPrizes=new HashMap<>();
    private static void BuildAllPrizes(){
        allPrizes.put(1,new FireBall());
        allPrizes.put(2,new MultipleBall());
        allPrizes.put(3,new BigBoard());
        allPrizes.put(4,new SmallBoard());
        allPrizes.put(5,new FastBall());
        allPrizes.put(6,new SlowBall());
        allPrizes.put(7,new ConfusedBoard());
    }
    public PrizeBrick(){
        BuildAllPrizes();
    }
    public PrizeBrick(ImageIcon icon) throws IOException {
        super(icon);
        BuildAllPrizes();
    }

    public void brick_break(MyBall ball, GamePanel panel) {
        panel.getPlayer().setScore(panel.getPlayer().getScore()+1);
        panel.removeComponent(this);
        panel.removeBrick(this);
        panel.setBrickCounter(panel.getBrickCounter() - 1);
        removeFile=true;
        file.delete();
        int whichPrize= new Random().nextInt(8) + 1;
        Prize prize;
        if(whichPrize!=8) {
            prize = allPrizes.get(whichPrize).prizeBuilder(4,
                    new ImageIcon("finalproject\\photo\\" +
                            "prize" + whichPrize + ".png")
                    , this.getX_brick() + this.getWidth() / 2, this.getY_brick(),
                    panel.getBoard(), panel.getBalls());
            prize.setIconNumber(whichPrize);
        }
        else{
            int whichPrize1=new Random().nextInt(7) + 1;
            prize = allPrizes.get(whichPrize1).prizeBuilder(4,
                    new ImageIcon("finalproject\\photo\\" +
                            "prize9.png")
                    , this.getX_brick() + this.getWidth() / 2, this.getY_brick(),
                    panel.getBoard(), panel.getBalls());
            prize.setIconNumber(whichPrize1);
        }
        panel.add(prize);
        panel.addPrize(prize);
    }

    public static HashMap<Integer, Prize> getAllPrizes() {
        return allPrizes;
    }
}