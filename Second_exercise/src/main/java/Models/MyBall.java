package Models;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


public class MyBall {
    private boolean removeFile=false;
    private  File file ;
    private int id;
    private static int ID_COUNTER;

    private  FileWriter writer;

    private final ImageIcon common_ball=new ImageIcon
            ("finalproject\\photo\\ball.png");
    private final ImageIcon Fire_ball=new ImageIcon
            ("finalproject\\photo\\fireball.png");
    private Image ballImage;
    private double x_ball;
    private double y_ball;
    private double xVelocity = 4;
    private double yVelocity = 7;
    private boolean IsFireBall = false;

    public MyBall (int a){

    }
    public MyBall() throws IOException {
       ballImage = common_ball.getImage();
       ID_COUNTER++;
       id=ID_COUNTER;
       file=new File("src\\Save\\Players\\" +
               Player.id+"\\"+Game.id+"\\Ball"+id+".txt");
       Save();
    }

    public void setFile(File file) {
        this.file = file;
    }

    public Image getBallImage() {
        return ballImage;
    }

    public double getX_ball() {
        return x_ball;
    }

    public double getY_ball() {
        return y_ball;
    }

    public void setX_ball(int x_ball) {
        this.x_ball = x_ball;
        try {
            Save();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void setSavedX_ball(double x_ball){
        this.x_ball = x_ball;
    }

    public void setY_ball(int y_ball) {
        this.y_ball = y_ball;
        try {
            Save();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void setSavedY_ball(double y_ball){
        this.y_ball = y_ball;
    }
    public void move() {
        x_ball = x_ball + xVelocity;
        y_ball = y_ball + yVelocity;
        try {
            Save();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public double getXVelocity() {
        return xVelocity;
    }

    public void setXVelocity(double xVelocity) {
        this.xVelocity = xVelocity;
        try {
            Save();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void setSavedXVelocity(double xVelocity) {
        this.xVelocity = xVelocity;
    }
    public double getYVelocity() {
        return yVelocity;
    }

    public void setYVelocity(double yVelocity) {
        this.yVelocity = yVelocity;
        try {
            Save();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void setSavedYVelocity(double yVelocity){
        this.yVelocity = yVelocity;
    }
    public void setBallImage(Image ballImage) {
        this.ballImage = ballImage;
    }

    public boolean IsNotFireBall() {
        return !IsFireBall;
    }

    public void setFireBall(boolean fireBall) {
        IsFireBall = fireBall;
        try {
            Save();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ImageIcon getFire_ball() {
        return Fire_ball;
    }

    public ImageIcon getCommon_ball() {
        return common_ball;
    }

    public void changVelocity(boolean fast) throws IOException {
        if(fast){
            if(xVelocity>0){
                setXVelocity(xVelocity+3);
            }
            else{
                setXVelocity(xVelocity-3);
            }
            if(yVelocity>0){
                setYVelocity(yVelocity+3);
            }
            else{
                setYVelocity(yVelocity-3);
            }
        }
        else{
            if(xVelocity>0){
                setXVelocity(xVelocity-3);
            }
            else{
                setXVelocity(xVelocity+3);
            }
            if(yVelocity>0){
                setYVelocity(yVelocity-3);
            }
            else{
                setYVelocity(yVelocity+3);
            }
        }
        Save();
    }

    public void resetVelocity() throws IOException {
        setYVelocity(7);
        setXVelocity(4);
        Save();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public static int getIdCounter() {
        return ID_COUNTER;
    }

    public static void setIdCounter(int idCounter) {
        ID_COUNTER = idCounter;
    }

    public void setRemoveFile(boolean removeFile) {
        this.removeFile = removeFile;
    }

    public File getFile() {
        return file;
    }

    public void Save() throws IOException {
        if (!removeFile) {
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                file.createNewFile();
            }
            writer = new FileWriter(file, false);
            if (!IsFireBall) {
                writer.write("common\n");
            } else {
                writer.write("fire\n");
            }
            writer.write((x_ball) + "\n");
            writer.write((y_ball) + "\n");
            writer.write((xVelocity) + "\n");
            writer.write((yVelocity) + "\n");
            writer.write(id+"\n");
            writer.write(ID_COUNTER+"\n");
            writer.flush();
            writer.close();
        }
    }
}
