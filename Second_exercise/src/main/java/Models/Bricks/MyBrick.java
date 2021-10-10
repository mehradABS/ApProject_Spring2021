package Models.Bricks;

import Logic.GamePanel;
import Models.Game;
import Models.MyBall;
import Models.Player;

import javax.swing.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class MyBrick extends JLabel {
    protected int live=2;
    protected int imageIconNumber;
    protected boolean removeFile=false;
    private static int ID_COUNTER;
    protected File file;
    protected  FileWriter writer;
    protected  int id;
    protected long lastMoveTime;
    protected int x_brick;
    protected int y_brick;
    protected final int YVelocity = 15;
    protected static int counter= new Random().nextInt(5) + 1;;
    protected static int lastCounter = counter;
    public MyBrick(){
        super();

    }
    public MyBrick(ImageIcon icon) throws IOException {
        super();
        if(counter - lastCounter > 9) {
            counter = new Random().nextInt(5) + 1;
            lastCounter = counter;
        }
        this.setLayout(null);
        this.setIcon(icon);
        x_brick = (counter % 10) * icon.getIconWidth();
        this.setBounds(x_brick, 20, icon.getIconWidth(),
                icon.getIconHeight() / 2);
        counter++;
        ID_COUNTER++;
        id=ID_COUNTER;
        file=new File("src\\Save\\Players\\" +
                Player.id+"\\"+ Game.id+"\\" +
                "Brick\\"+id+".txt");
        Save();
    }

    public void setFile(File file) {
        this.file = file;
    }

    public int getX_brick() {
        return x_brick;
    }

    public int getY_brick() {
        return y_brick;
    }

    public void move(long now) {
        if (now - lastMoveTime > 2000000000) {
            y_brick += YVelocity;
            this.setBounds(x_brick, y_brick, this.getIcon().getIconWidth(),
                    this.getIcon().getIconHeight() / 2);
            lastMoveTime=now;
            try {
                Save();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
 
    public void brick_break(MyBall ball, GamePanel panel){
        panel.getPlayer().setScore(panel.getPlayer().getScore()+1);
        panel.removeComponent(this);
        panel.removeBrick(this);
        panel.setBrickCounter(panel.getBrickCounter()-1);
        file.delete();
       removeFile=true;
    }

    public void setRemoveFile(boolean removeFile) {
        this.removeFile = removeFile;
    }

    public boolean IsVisible(){
        return true;
     }

     public void start(){

     }

     public void stop(){

     }

    public static void setCounter(int counter) {
        MyBrick.counter = counter;
    }

    public static void setLastCounter(int lastCounter) {
        MyBrick.lastCounter = lastCounter;
    }

    public static int getIdCounter() {
        return ID_COUNTER;
    }

    public static void setIdCounter(int idCounter) {
        ID_COUNTER = idCounter;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setImageIconNumber(int imageIconNumber) {
        this.imageIconNumber = imageIconNumber;
        try {
            Save();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void setSavedImageIconNumber(int imageIconNumber) {
        this.imageIconNumber = imageIconNumber;
    }
    public void setX_brick(int x_brick) {
        this.x_brick = x_brick;
    }

    public void setY_brick(int y_brick) {
        this.y_brick = y_brick;
    }

    public void setLive(int live) {
        this.live = live;
    }

    public void Save() throws IOException {
        if (!removeFile) {
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                file.createNewFile();
            }
            writer = new FileWriter(file, false);
            writer.write(imageIconNumber+"\n");
            writer.write(x_brick + "\n");
            writer.write(y_brick + "\n");
            writer.write(id + "\n");
            writer.write(ID_COUNTER + "\n");
            writer.write(live+"\n");
            writer.flush();
            writer.close();
        }
    }
}
