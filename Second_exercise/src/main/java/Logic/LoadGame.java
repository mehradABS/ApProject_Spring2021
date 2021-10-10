package Logic;

import Models.*;
import Models.Bricks.MyBrick;
import Models.Bricks.PrizeBrick;
import Models.Prizes.Prize;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Objects;
import java.util.Scanner;

public class LoadGame {
    private String playerName;
    public LoadGame() {

    }

    public String getPlayerName() {
        return playerName;
    }

    public boolean isNewGame(String gameName) throws IOException {
        File playerFile = new File("src\\Save\\Players\\"+playerName);
        if(!playerFile.exists()){
            File file1=new File("src\\Save\\Players\\"+
                    "Names");
            FileWriter writer = new FileWriter(file1,true);
            writer.write(playerName+"\n");
            writer.flush();
            writer.close();
            playerFile.getParentFile().mkdirs();
            return true;
        }
        for (File file: Objects.requireNonNull(playerFile.listFiles())) {
            if(file.getName().equals(gameName)){
                return false;
            }
        }
         return true;
    }

    public GamePanel load(Father frame, String gameName) throws IOException {
     GamePanel gamepanel=new GamePanel(frame);
     gamepanel.getPlayer().setId(playerName);
     gamepanel.getPlayer().getGame().setId(gameName);
     File BoardFile=new File("src\\Save\\Players\\"+
                Player.id+"\\"+ Game.id+"\\board.txt");
     gamepanel.getBoard().setBoardFile(BoardFile);
     Scanner scanner1=new Scanner(BoardFile);
        String confused = scanner1.next();
        gamepanel.getBoard().setSaveConfused(!confused.equals("false"));
        String BoardImageType = scanner1.next();
        if(BoardImageType.equals("small")){
            gamepanel.getBoard().setSavedBoardImage
                    (gamepanel.getBoard().getSmallBoard().getImage());
        }
        else if(BoardImageType.equals("big")){
            gamepanel.getBoard().setSavedBoardImage
                    (gamepanel.getBoard().getBigBoard().getImage());
        }
        else{
            gamepanel.getBoard().setSavedBoardImage
                    (gamepanel.getBoard().getCommonBoard().getImage());
        }
        gamepanel.getBoard().setSavedX_board(scanner1.nextInt());
        scanner1.close();
        gamepanel.setFile
                (new File("src\\Save\\Players\\"+
                gamepanel.getPlayer().getId()+"\\"
                        +gamepanel.getPlayer().getGame().getId()
                +"\\gamePanel.txt"));
        Scanner scanner2 = new Scanner(gamepanel.getFile());
        gamepanel.setLose(scanner2.nextBoolean(),true);
        gamepanel.setLive(scanner2.nextInt(),true,false);
        gamepanel.setSavedBrickCounter(scanner2.nextInt());
        while (true){
            String id = scanner2.next();
            if(!id.equals("false")) {
                if (id.charAt(id.length() - 1) != ']') {
                    MyBall ball = loadBall(Integer.parseInt(id));
                    if (ball != null) {
                        gamepanel.getBalls().add(ball);
                        gamepanel.getBalls_id().add(Integer.parseInt(id));
                    }
                } else {
                    int id1 = Integer.parseInt(id.substring(0, id.length() - 1));
                    MyBall ball = loadBall(id1);
                    if (ball != null) {
                        gamepanel.getBalls().add(ball);
                        gamepanel.getBalls_id().add(id1);
                        break;
                    }
                }
            }
            else{
                break;
            }
        }
        while (true){
            String id=scanner2.next();
            if(!id.equals("false")){
            if(id.charAt(id.length()-1)!=']'){
                MyBrick brick=loadBrick(Integer.parseInt(id));
                if(brick!=null){
                    gamepanel.getBricks().add(brick);
                    gamepanel.getBricks_id().add(Integer.parseInt(id));
                    gamepanel.add(brick);
                }
            }
            else {
                int id1 = Integer.parseInt(id.substring(0, id.length() - 1));
                MyBrick brick = loadBrick(id1);
                if (brick != null) {
                    gamepanel.getBricks().add(brick);
                    gamepanel.getBricks_id().add(id1);
                    gamepanel.add(brick);
                    break;
                }
              }
            }
            else{
                break;
            }
        }
        while (true){
            String id=scanner2.next();
            if(!id.equals("false")) {
                if (id.charAt(id.length() - 1) != ']') {
                    Prize prize = loadPrize(Integer.parseInt(id));
                    if (prize != null) {
                        gamepanel.getPrizes().add(prize);
                        gamepanel.getPrizes_id().add(Integer.parseInt(id));
                        prize.getMoveTimer().start();
                        prize.MoveTimer(false);
                        gamepanel.add(prize);
                        prize.setBalls(gamepanel.getBalls());
                        prize.setBoard(gamepanel.getBoard());
                    }
                } else {
                    int id1 = Integer.parseInt(id.substring(0, id.length() - 1));
                    Prize prize = loadPrize(id1);
                    if (prize != null) {
                        gamepanel.getPrizes().add(prize);
                        gamepanel.getPrizes_id().add(id1);
                        prize.getMoveTimer().start();
                        prize.MoveTimer(false);
                        gamepanel.add(prize);
                        prize.setBalls(gamepanel.getBalls());
                        prize.setBoard(gamepanel.getBoard());
                        break;
                    }
                }
            }
            else{
                break;
            }
        }

        while (true){
            String id=scanner2.next();
            if(!id.equals("false")) {
                if (id.charAt(id.length() - 1) != ']') {
                    Prize prize = loadPrize(Integer.parseInt(id));
                    if (prize != null) {
                        gamepanel.getPrizes().add(prize);
                        gamepanel.getPrizes_id().add(Integer.parseInt(id));
                        prize.getMoveTimer().stop();
                        prize.setUsingTime(new Timer(8000,prize::actionPerformed));
                        prize.setBoard(gamepanel.getBoard());
                        prize.setBalls(gamepanel.getBalls());
                        prize.usePrize();
                        prize.UsingTime(false);
                    }
                } else {
                    int id1 = Integer.parseInt(id.substring(0, id.length() - 1));
                    Prize prize = loadPrize(id1);
                    if (prize != null) {
                        gamepanel.getPrizes().add(prize);
                        gamepanel.getPrizes_id().add(id1);
                        prize.getMoveTimer().stop();
                        prize.setUsingTime(new Timer(8000,prize::actionPerformed));
                        prize.setBalls(gamepanel.getBalls());
                        prize.setBoard(gamepanel.getBoard());
                        prize.usePrize();
                        break;
                    }
                }
            }
            else{
                break;
            }
        }
        for (Prize prize :gamepanel.getPrizes()) {
            prize.setBallId(gamepanel.getBalls_id());
        }
        gamepanel.getPlayer().setScore(scanner2.nextInt());
        scanner2.close();
        return gamepanel;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public MyBall loadBall(int id) throws IOException {
        File file=new File("src\\Save\\Players\\" +
                Player.id+"\\"+Game.id+"\\Ball"+id+".txt");
        if(file.exists()){
            Scanner scanner=new Scanner(file);
            MyBall ball = new MyBall(1);
            String ImageType=scanner.next();
            if(ImageType.equals("common")){
                ball.setBallImage(ball.getCommon_ball().getImage());
            }
            else {
                ball.setBallImage(ball.getFire_ball().getImage());
            }
            ball.setSavedX_ball(scanner.nextDouble());
            ball.setSavedY_ball(scanner.nextDouble());
            ball.setSavedXVelocity(scanner.nextDouble());
            ball.setSavedYVelocity(scanner.nextDouble());
            ball.setId(scanner.nextInt());
            MyBall.setIdCounter(scanner.nextInt());
            ball.setFile(file);
            scanner.close();
            return ball;
        }
        return null;
    }

    public MyBrick loadBrick(int id) throws FileNotFoundException {
        File file=new File("src\\Save\\Players\\" +
                Player.id+"\\"+Game.id+"\\" +
                "Brick\\"+id+".txt");
        if(file.exists()){
             Scanner scanner=new Scanner(file);
             MyBrick brick = new MyBrick();
             brick.setLayout(null);
             int icon = scanner.nextInt();
             brick.setSavedImageIconNumber(icon);
             ImageIcon icon1=new ImageIcon
                    ("finalproject\\photo\\brick"+icon+".png");
             brick.setIcon(icon1);
             brick.setX_brick(scanner.nextInt());
             brick.setY_brick(scanner.nextInt());
             brick.setBounds(brick.getX_brick(), brick.getY_brick(),
                    brick.getIcon().getIconWidth(),
                    brick.getIcon().getIconHeight() / 2);
             brick.setId(scanner.nextInt());
             MyBrick.setIdCounter(scanner.nextInt());
             brick.setLive(scanner.nextInt());
             brick.setFile(file);
             scanner.close();
             return brick;
        }
        return null;
    }

    public Prize loadPrize(int id) throws FileNotFoundException {
        File file=new File("src\\Save\\Players\\" +
                Player.id+"\\"+Game.id+"\\Prize\\"+id+".txt");
        if(file.exists()){
            Scanner scanner=new Scanner(file);
            int whichPrize = scanner.nextInt();
            new PrizeBrick();
            if(whichPrize==0){
                whichPrize=1;
            }
            Prize prize=PrizeBrick.getAllPrizes().get(whichPrize);
            prize.setIcon(new ImageIcon("finalproject\\photo\\" +
                    "prize" + whichPrize + ".png"));
            Prize.setIdCounter(scanner.nextInt());
            prize.setId(scanner.nextInt());
            prize.setSavedUsingTimerState(scanner.nextBoolean());
            prize.setYVelocity(scanner.nextInt());
            prize.setX_prize(scanner.nextInt());
            prize.setY_prize(scanner.nextInt());
            prize.setUsingTime(new Timer(15000,prize::actionPerformed));
            prize.getMoveTimer().stop();
            prize.setBounds
                    (prize.getX_prize(),prize.getY_prize(),
                            prize.getIcon().getIconWidth(),prize.getIcon()
                                    .getIconHeight());
            prize.setFile(file);
            scanner.close();
            return prize;
        }
        return null;
    }
}
