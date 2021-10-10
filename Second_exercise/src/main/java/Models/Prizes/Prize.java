package Models.Prizes;

import Logic.GamePanel;
import Models.Game;
import Models.MyBall;
import Models.MyBoard;
import Models.Player;

import javax.swing.*;
import java.awt.event.ActionEvent;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public abstract class Prize extends JLabel {
    protected int iconNumber = 7;
    public boolean removeFile=false;
    private static int ID_COUNTER;
    protected File file;
    protected  int id;
    protected  FileWriter writer;
    protected boolean usingTimerState = false;
    protected  LinkedList<MyBall> balls;
    protected MyBoard board;
    protected Timer usingTime;
    protected Timer moveTimer;
    protected  int YVelocity;
    protected  int x_prize;
    protected int y_prize;
    protected static final List<Integer> gotPrizes_Id =new ArrayList<>();
    protected LinkedList<Integer> ballId;
    Prize(){
     super();
     this.setLayout(null);
     this.moveTimer=new Timer(15,this::move);
     this.moveTimer=new Timer(15,this::move);
    }
    Prize(int YVelocity,ImageIcon icon,int x,int y,MyBoard board,
          LinkedList<MyBall> balls)  {
        super();
        this.balls=balls;
        this.YVelocity=YVelocity;
        this.setLayout(null);
        this.setIcon(icon);
        this.x_prize=x;
        this.y_prize=y;
        this.setBounds(x_prize,y_prize,icon.getIconWidth(),icon.getIconHeight());
        this.board=board;
        this.moveTimer=new Timer(15,this::move);
        moveTimer.start();
        ID_COUNTER++;
        id=ID_COUNTER;
        file =new File("src\\Save\\Players\\" +
                Player.id+"\\"+ Game.id+"\\Prize\\"+id+".txt");
        try {
            Save();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setFile(File file) {
        this.file = file;
    }

    public int getX_prize() {
        return x_prize;
    }

    public int getY_prize() {
        return y_prize;
    }

    public void move(ActionEvent e){
           y_prize += YVelocity;
           this.setBounds(x_prize, y_prize, this.getIcon().getIconWidth(),
                   this.getIcon().getIconHeight());

        try {
            Save();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    public abstract void usePrize() throws IOException;

    public Timer getMoveTimer() {
        return moveTimer;
    }

    public void addBall(MyBall ball){
        balls.add(ball);
        ballId.add(ball.getId());
        try {
            Save();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    public void addGotPrizes(int a){
        gotPrizes_Id.add(a);
        try {
            Save();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    public void removeGotPrize(Prize prize){
        for (int i = 0; i < gotPrizes_Id.size(); i++) {
            if(gotPrizes_Id.get(i)==prize.getPrize_Id()){
                gotPrizes_Id.remove(i);
                break;
            }
        }
        GamePanel.gotPrizes.remove(prize);
        for (int i = 0; i < GamePanel.gotPrizesId.size(); i++) {
            if(GamePanel.gotPrizesId.get(i)==prize.getId()){
                GamePanel.gotPrizesId.remove(i);
                break;
            }
        }
        prize.setRemoveFile(true);
        try {
            Save();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    public abstract int getPrize_Id();

    public boolean GotPrizeNotContains(int a){
        for (Integer integer : gotPrizes_Id) {
            if (integer == a) {
                return false;
            }
        }
        return true;
    }

    public abstract Prize prizeBuilder(int YVelocity,
                                       ImageIcon icon,int x,int y,
                                       MyBoard board,
                                       LinkedList<MyBall> balls);

    public void MoveTimer(boolean start){
        if(start){
            moveTimer.start();
        }
        else {
            moveTimer.stop();
        }
    }

    public void UsingTime(boolean start){
        if(start){
            usingTime.start();
            setUsingTimerState(true);
        }
        else {
            usingTime.stop();
            setUsingTimerState(false);
        }
    }

    public boolean UsingTimerState() {
        return usingTimerState;
    }

    public void setUsingTimerState(boolean usingTimerState) {
        this.usingTimerState = usingTimerState;
        try {
            Save();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }
    public void setSavedUsingTimerState(boolean usingTimerState) {
        this.usingTimerState = usingTimerState;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setRemoveFile(boolean removeFile) {
        this.removeFile = removeFile;
    }

    public static void setIdCounter(int idCounter) {
        ID_COUNTER = idCounter;
    }

    public void setIconNumber(int iconNumber) {
        this.iconNumber = iconNumber;
    }

    public void setBalls(LinkedList<MyBall> balls) {
        this.balls = balls;
    }

    public File getFile() {
        return file;
    }

    public void setBoard(MyBoard board) {
        this.board = board;
    }

    public void setUsingTime(Timer usingTime) {
        this.usingTime = usingTime;
    }

    public void setYVelocity(int YVelocity) {
        this.YVelocity = YVelocity;
    }

    public void setX_prize(int x_prize) {
        this.x_prize = x_prize;
    }

    public void setBallId(LinkedList<Integer> ballId) {
        this.ballId = ballId;
    }

    public boolean isRemoveFile() {
        return removeFile;
    }

    public void setY_prize(int y_prize) {
        this.y_prize = y_prize;
    }

    public abstract void actionPerformed(ActionEvent e);

    public void Save() throws IOException {
        if (!removeFile) {
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                file.createNewFile();
            }
            writer = new FileWriter(file, false);
            writer.write(iconNumber+"\n");
            writer.write(ID_COUNTER + "\n");
            writer.write(id + "\n");
            writer.write(usingTimerState + "\n");
            writer.write(YVelocity + "\n");
            writer.write(x_prize + "\n");
            writer.write(y_prize + "\n");
            writer.flush();
            writer.close();
        }
    }
}
