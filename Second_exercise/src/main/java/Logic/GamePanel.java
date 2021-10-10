package Logic;


import Models.*;
import Models.Bricks.*;
import Models.Prizes.Prize;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Random;
import java.util.Scanner;


public class GamePanel extends JPanel implements KeyListener, ActionListener {
    boolean gameOver=false;
    int gameOver1 =0;
    public static  LinkedList<Integer> gotPrizesId;

    public static LinkedList<Prize> gotPrizes=new LinkedList<>();

    private  File file;

    private  final Player player;

    private boolean lose=false;

    private boolean pauseGame=false;

    private Pause pause;

    private int live=3;

    private final LinkedList<MyBrick> helpList=new LinkedList<>();

    private  int BrickCounter=0;

    private final LinkedList<MyBall> balls=new LinkedList<>();

    private final LinkedList<Integer> balls_id=new LinkedList<>();

    private final LinkedList<MyBall> helpList2=new LinkedList<>();

    private boolean RightKeyPressed=false;

    private boolean LeftKeyPressed=false;

    private  MyBoard board;

    private  Image backgroundImage;

    private  LinkedList<MyBrick> bricks;

    private final LinkedList<Integer> bricks_id=new LinkedList<>();

    private final LinkedList<Prize> prizes=new LinkedList<>();

    private final LinkedList<Integer> prizes_id=new LinkedList<>();

    private final LinkedList<Prize> helpList1=new LinkedList<>();

    public final Timer repaintTimer=new Timer(1,this);

    public final Timer regeneratingBrick=new Timer(10000,
            this::regeneratingBrick);

    Action startAction;

    public GamePanel(Father frame){
       gotPrizesId=new LinkedList<>();
        player=new Player();
       board=new MyBoard("null");
       bricks=new LinkedList<>();
        this.setPreferredSize(new Dimension
                (Constant.PANEL_WIDTH, Constant.PANEL_HEIGHT));
        this.setLayout(null);
        backgroundImage = new ImageIcon
                ("finalproject\\photo\\space.png").getImage();


        this.setFocusable(true);
        this.addKeyListener(this);

        this.pause=new Pause(frame);
        pause.setPanel(this);

        startAction = new StartAction();
        this.getInputMap().put(KeyStroke.getKeyStroke(' '),' ');
        this.getActionMap().put(' ', startAction);

    }

    public GamePanel(Father frame, String playerName, String gameName ) throws IOException {
        gotPrizesId=new LinkedList<>();
        player = new Player();
        player.setId(playerName);
        player.getGame().setId(gameName);
        ///////
        board = new MyBoard();
    //////////////////////////////////
        file=new File("src\\Save\\Players\\"+
                player.getId()+"\\"+player.getGame().getId()
                +"\\gamePanel.txt");
        //
        MyBall ball = new MyBall();
        ball.setX_ball(board.getX_board() + board.getBoardImage().getWidth(null) / 2
                - 20);
        ball.setY_ball(board.getY_board() - board.getBoardImage().getHeight(null)
                - 1);
        addBall(ball);
        //////////////////////////////
        bricks=new LinkedList<>();
        generatingBrick();
        /////////////////
        this.setPreferredSize(new Dimension
                (Constant.PANEL_WIDTH, Constant.PANEL_HEIGHT));
        this.setLayout(null);
        backgroundImage = new ImageIcon
                ("finalproject\\photo\\space.png").getImage();


        this.setFocusable(true);
        this.addKeyListener(this);
///////////////////////
        this.pause=new Pause(frame);
        pause.setPanel(this);
         //
          startAction = new StartAction();
        this.getInputMap().put(KeyStroke.getKeyStroke(' '),' ');
        this.getActionMap().put(' ', startAction);
        //
        Save();
    }
    public int getBrickCounter() {
        return BrickCounter;
    }

    public void setBrickCounter(int brickCounter) {
        BrickCounter = brickCounter;
        try {
                Save();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    public void setSavedBrickCounter(int brickCounter){
        BrickCounter = brickCounter;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2D = (Graphics2D) g;
        this.requestFocus();
        /////////////////////
        boolean lose=false;
        while(true) {
            if(!gameOver) {
                for (MyBall ball : balls) {
                    Collision.ball_board(ball, board);
                    Collision.ball_panel(ball);
                    Collision.ball_brick(ball, bricks, helpList, this);
                }
                lose = Collision.ball_DownOfPanel(balls, helpList2, this);
                if (lose) {
                    live--;
                    File file1 = new File("src\\Save\\Players\\" +
                            player.getId() + "\\" + player.getGame().getId()
                            + "\\Brick");
                    if (file1.exists()) {
                        for (File file : Objects.requireNonNull(file1.listFiles())) {
                            file.delete();
                        }
                    }

                    file1 = new File("src\\Save\\Players\\" +
                            player.getId() + "\\" + player.getGame().getId()
                            + "\\Prize");
                    if (file1.exists()) {
                        for (File file : Objects.requireNonNull(file1.listFiles())) {
                            file.delete();
                        }
                    }
                }
                boolean GameOver1 = live == 0;
                boolean GameOver2 = Collision.brick_panel(bricks);
                if (GameOver1 || GameOver2) {
                    gameOver = true;
                    break;
                } else if (lose) {
                    try {
                        restart();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                }

                Collision.prize_panel(prizes, board, helpList1, this);
                try {
                    Collision.prize_board(prizes, board, helpList1, this);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                /////////////////////
                for (MyBall ball : balls) {
                    ball.move();
                }
                for (MyBrick brick : bricks) {
                    brick.move(System.nanoTime());
                }


                if (RightKeyPressed) {
                    if (board.isNotConfused()) {
                        board.RightAction();
                    } else {
                        board.LeftAction();
                    }
                }

                if (LeftKeyPressed) {
                    if (board.isNotConfused()) {
                        board.LeftAction();
                    } else {
                        board.RightAction();
                    }
                }
            }
            g2D.drawImage(backgroundImage, 0, 0, null);
            g2D.drawImage(board.getBoardImage(), board.getX_board(),
                    board.getY_board(), null);
            for (MyBall ball : balls) {
                g2D.drawImage(ball.getBallImage(), (int) ball.getX_ball(),
                        (int) ball.getY_ball(), null);
            }
            break;
        }
        if(lose){
            lose=false;
            repaint();
        }
        try {
            Save();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(gameOver && gameOver1 ==0){
            JLabel label=new JLabel();
            JLabel label1=new JLabel();

            label.setFont(new Font("Consolas",Font.PLAIN,35));
            label.setForeground(Color.RED);
            label.setLayout(null);
            label.setBounds(944,300,200,50);
            label.setText("GAME OVER");
            label1.setFont(new Font("Consolas",Font.PLAIN,35));
            label1.setForeground(Color.RED);
            label1.setLayout(null);
            label1.setBounds(944,340,200,50);
            label1.setText("score: "+player.getScore());
            this.add(label);
            this.add(label1);
            this.add(pause.getMenu1());
            pause.pause();
            startAction.setEnabled(false);
            File file1=new File("src\\Save\\Players\\"+
                    player.getId()+"\\"+player.getGame().getId()
                    +"\\Brick");
            if(file1.exists()){
                for (File file: Objects.requireNonNull(file1.listFiles())) {
                    file.delete();
                }
            }
            file1=new File("src\\Save\\Players\\"+
                    player.getId()+"\\"+player.getGame().getId()
                    +"\\Prize");
            if(file1.exists()) {
                for (File file : Objects.requireNonNull(file1.listFiles())) {
                    if(file.delete())
                    System.out.println("deleted");
                }
            }
            file.delete();
            file1=new File("src\\Save\\Players\\"+
                    player.getId()+"\\"+player.getGame().getId());
            if(file1.exists()){
                for (File file: Objects.requireNonNull(file1.listFiles())) {
                    file.delete();
                }
            }
            file1=new File("src\\Save\\Players\\"+
                    player.getId()+"\\"+player.getGame().getId()+"\\board.txt");
            if(file1.exists())file1.delete();
            file1=new File("src\\Save\\Players\\"+
                    player.getId()+"\\"+player.getGame().getId());
            if(file1.exists())file1.delete();
            for (Prize prize:prizes) {
                prize.setRemoveFile(true);
            }
            for (MyBrick brick:bricks) {
               brick.setRemoveFile(true);
            }
            for (MyBall ball:balls) {
                 ball.setRemoveFile(true);
            }
            board.removeFile=true;
            bricks.removeAll(bricks);
            bricks_id.removeAll(bricks_id);
            balls.removeAll(balls);
            balls_id.removeAll(balls_id);
            prizes_id.removeAll(prizes_id);
            prizes.removeAll(prizes);
            gotPrizesId.removeAll(gotPrizesId);
            live=-1;
            gameOver1++;
            repaint();
        }
    }


    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        requestFocus();
        if(!pauseGame) {
            switch (e.getKeyChar()) {
                case 'a': {
                    LeftKeyPressed = true;
                    break;
                }
                case 'd': {
                    RightKeyPressed = true;
                    break;
                }
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        requestFocus();
        switch (e.getKeyChar()){
            case 'a':{
                LeftKeyPressed=false;
                break;
            }
            case 'd':{
                RightKeyPressed=false;
                break;
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
        revalidate();
    }

    public void generatingBrick() throws IOException {
            Random random=new Random();
            int i = random.nextInt(5)+1;
            MyBrick brick1=new InvisibleBrick(new ImageIcon
                    ("finalproject\\photo\\brick"+i+".png"));
            brick1.setImageIconNumber(i);
            bricks.add(brick1);
            bricks_id.add(bricks.get(BrickCounter).getId());
            this.add(bricks.get(BrickCounter));
            BrickCounter++;
            i=(i+1)%10;
            MyBrick brick2=new PrizeBrick(new ImageIcon
                    ("finalproject\\photo\\brick"+i+".png"));
            brick2.setImageIconNumber(i);
            bricks.add(brick2);
            bricks_id.add(bricks.get(BrickCounter).getId());
            this.add(bricks.get(BrickCounter));
            BrickCounter++;
            i=(i+1)%10;
            MyBrick brick3=new WoodenBrick(new ImageIcon
                    ("finalproject\\photo\\brick"+i+".png"));
            brick3.setImageIconNumber(i);
            bricks.add(brick3);
           bricks_id.add(bricks.get(BrickCounter).getId());
            this.add(bricks.get(BrickCounter));
            BrickCounter++;
            i=(i+1)%10;
            MyBrick brick4=new BlinkingBrick(new ImageIcon
                    ("finalproject\\photo\\brick"+i+".png"));
            brick4.setImageIconNumber(i);
            bricks.add(brick4);
        bricks_id.add(bricks.get(BrickCounter).getId());
            this.add(bricks.get(BrickCounter));
            BrickCounter++;
            i=(i+1)%10;
            MyBrick brick5=new GlassBrick(new ImageIcon
                    ("finalproject\\photo\\brick"+i+".png"));
            brick5.setImageIconNumber(i);
            bricks.add(brick5);
        bricks_id.add(bricks.get(BrickCounter).getId());
            this.add(bricks.get(BrickCounter));
            BrickCounter++;
            MyBrick brick6=new InvisibleBrick(new ImageIcon
                    ("finalproject\\photo\\brick"+i+".png"));
            brick6.setImageIconNumber(i);
            bricks.add(brick6);
        bricks_id.add(bricks.get(BrickCounter).getId());
            this.add(bricks.get(BrickCounter));
        BrickCounter++;
        i=(i+1)%10;
        MyBrick brick7=new PrizeBrick(new ImageIcon
                ("finalproject\\photo\\brick"+i+".png"));
        brick7.setImageIconNumber(i);
        bricks.add(brick7);
        bricks_id.add(bricks.get(BrickCounter).getId());
        this.add(bricks.get(BrickCounter));
        BrickCounter++;
        i=(i+1)%10;
        MyBrick brick8=new WoodenBrick(new ImageIcon
                ("finalproject\\photo\\brick"+i+".png"));
        brick8.setImageIconNumber(i);
        bricks.add(brick8);
        bricks_id.add(bricks.get(BrickCounter).getId());
        this.add(bricks.get(BrickCounter));
        BrickCounter++;
        i=(i+1)%10;
        MyBrick brick9=new BlinkingBrick(new ImageIcon
                ("finalproject\\photo\\brick"+i+".png"));
        brick9.setImageIconNumber(i);
        bricks.add(brick9);
        bricks_id.add(bricks.get(BrickCounter).getId());
        this.add(bricks.get(BrickCounter));
        BrickCounter++;
        i=(i+1)%10;
        MyBrick brick10=new GlassBrick(new ImageIcon
                ("finalproject\\photo\\brick"+i+".png"));
        brick10.setImageIconNumber(i);
        bricks.add(brick10);
        bricks_id.add(bricks.get(BrickCounter).getId());
        this.add(bricks.get(BrickCounter));
        BrickCounter++;
        try {
            Save();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void regeneratingBrick(ActionEvent actionEvent){
        try {
            generatingBrick();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addPrize(Prize prize){
        prizes.add(prize);
        prize.setBallId(balls_id);
        prizes_id.add(prize.getId());
        try {
            Save();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public MyBoard getBoard() {
        return board;
    }

    public void addBall(MyBall ball){
        balls.add(ball);
        balls_id.add(ball.getId());
        try {
            Save();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void removeBall(MyBall ball){
        balls.remove(ball);
        for (int i = 0; i < balls_id.size(); i++) {
            if(balls_id.get(i)== ball.getId()){
                balls_id.remove(i);
                break;
            }
        }
        ball.setRemoveFile(true);
        ball.getFile().delete();
        try {
            Save();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void removeComponent(Component component){
        this.remove(component);
    }

    public void removeBrick(MyBrick brick){
        bricks.remove(brick);
        for (int i = 0; i < bricks_id.size(); i++) {
            if(bricks_id.get(i)==brick.getId()){
                bricks_id.remove(i);
                break;
            }
        }
        try {
            Save();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void removePrize(Prize prize){
        prizes.remove(prize);
        for (int i = 0; i < prizes_id.size(); i++) {
            if(prizes_id.get(i)==prize.getId()){
                prizes_id.remove(i);
                break;
            }
        }
        prize.setRemoveFile(true);
        try {
            Save();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public LinkedList<MyBall> getBalls() {
        return balls;
    }


    public void setBoard(MyBoard board) {
        this.board = board;
    }

    public LinkedList<MyBrick> getBricks() {
        return bricks;
    }

    public LinkedList<Prize> getPrizes() {
        return prizes;
    }

    public void changeRepaintTimer(boolean start) {
        if(start){
            repaintTimer.start();
        }
        else {
            repaintTimer.stop();
        }
    }

    public void changeRegeneratingBrick(boolean start) {
        if(start){
            regeneratingBrick.start();
        }
        else{
            regeneratingBrick.stop();
        }
    }

    public boolean isPauseGame() {
        return pauseGame;
    }

    public void setPauseGame(boolean pauseGame) {
        this.pauseGame = pauseGame;
        try {
            Save();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean isLose() {
        return lose;
    }

    public void setLose(boolean lose,boolean save) {
        this.lose = lose;
        if(!save){
        try {
            Save();
        } catch (IOException e) {
            e.printStackTrace();
          }
        }
    }

    public void addButton(){
        this.add(pause.getMenu());
        this.add(pause.getRestart());
        repaint();
        revalidate();
    }

    public LinkedList<Integer> getBricks_id() {
        return bricks_id;
    }
    public LinkedList<Integer> getBalls_id() {
        return balls_id;
    }

    public LinkedList<Integer> getPrizes_id() {
        return prizes_id;
    }

    public void restart() throws IOException {
        this.setLose(true,false);
        this.repaintTimer.stop();
        this.regeneratingBrick.stop();
        //
        this.setBoard(new MyBoard());
        //
        MyBall ball = new MyBall();
        ball.setX_ball(this.getBoard().getX_board() + this.getBoard().getBoardImage().getWidth(null) / 2
                - 20);
        ball.setY_ball(this.getBoard().getY_board() - this.getBoard().getBoardImage().getHeight(null)
                - 1);
        this.getBalls().removeAll(this.getBalls());
        this.addBall(ball);
        //
        for (MyBrick brick:this.getBricks()) {
            this.removeComponent(brick);
        }
        this.getBricks().removeAll(this.getBricks());
        this.getBricks_id().removeAll(this.getBricks_id());
        this.setBrickCounter(0);
        this.generatingBrick();
        //
        for (Prize prize:this.getPrizes()) {
            this.removeComponent(prize);
        }
        this.getPrizes().removeAll(this.getPrizes());
        this.getPrizes_id().removeAll(this.getPrizes_id());
        this.remove(pause.getRestart());
        this.remove(pause.getMenu());
        repaint();
        revalidate();
        this.setPauseGame(false);
        try {
            Save();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public File getFile() {
        return file;
    }

    public Pause getPause() {
        return pause;
    }

    public void setLive(int live,boolean save,boolean gameOver) {
        this.live = live;
        if (!save) {
            try {
                Save();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public void Save() throws IOException {
        if (!gameOver) {
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                file.createNewFile();
            }
            FileWriter writer = new FileWriter(
                    file, false);
            writer.write(lose + "\n");
            writer.write(live + "\n");
            writer.write(BrickCounter + "\n");
            if (balls_id.size() > 1) {
                String[] a = balls_id.toString().split(",");
                for (String s : a) {
                    if (s.charAt(0) == ' ' || s.charAt(0) == '[') {
                        writer.write(s.substring(1) + "\n");
                    } else {
                        writer.write(s + "\n");
                    }

                }
            } else {
                if (balls_id.size() == 1) {
                    writer.write(balls_id.get(0) + "]\n");
                } else {
                    writer.write("false\n");
                }
            }
            if (bricks_id.size() > 1) {
                String[] b = bricks_id.toString().split(",");
                for (String s : b) {
                    if (s.charAt(0) == ' ' || s.charAt(0) == '[') {
                        writer.write(s.substring(1) + "\n");
                    } else {
                        writer.write(s + "\n");
                    }

                }
            } else {
                if (bricks_id.size() == 0) {
                    writer.write("false" + "\n");
                } else {
                    writer.write(bricks_id.get(0) + "]\n");
                }
            }
            if (prizes_id.size() > 1) {
                String[] c = prizes_id.toString().split(",");
                for (String s : c) {
                    if (s.charAt(0) == ' ' || s.charAt(0) == '[') {
                        writer.write(s.substring(1) + "\n");
                    } else {
                        writer.write(s + "\n");
                    }

                }
            } else {
                if (prizes_id.size() == 0) {
                    writer.write("false" + "\n");
                } else {
                    writer.write(prizes_id.get(0) + "]\n");
                }
            }
            if (gotPrizesId.size() > 1) {
                String[] d = gotPrizesId.toString().split(",");
                for (String s : d) {
                    if (s.charAt(0) == ' ' || s.charAt(0) == '[') {
                        writer.write(s.substring(1) + "\n");
                    } else {
                        writer.write(s + "\n");
                    }
                }
            } else {
                if (gotPrizesId.size() == 0) {
                    writer.write("false" + "\n");
                } else {
                    writer.write(gotPrizesId.get(0) +
                            "]\n");
                }
            }
            writer.write(player.getScore() + "\n");
            writer.flush();
            writer.close();
            File file1=new File("src\\Save\\Players\\"+
                    player.getId()
                    +"\\score.txt");
            if(!file1.exists()){
                file.getParentFile().mkdirs();
                file.createNewFile();
                System.out.println("salam");
            }
            Scanner scanner2 = new Scanner(file1);
            FileWriter writer1=new FileWriter(file1);
            int score;
            if(scanner2.hasNext()) {
                 score = player.getScore() + scanner2.nextInt();
            }
            else{
                score=player.getScore();
            }
            writer1.write(score+"\n");
            scanner2.close();
            writer1.flush();
            writer1.close();
        }
    }
    public void setFile(File file) {
        this.file=file;
    }

    public Player getPlayer() {
        return player;
    }

    public  class StartAction extends AbstractAction{

        private int whichAction=0;
        @Override
        public void actionPerformed(ActionEvent e) {
            if(whichAction==0 || lose) {
                for (MyBrick brick : bricks) {
                    brick.start();
                }
                for (Prize prize:prizes) {
                    prize.MoveTimer(true);
                }
                repaintTimer.start();
                regeneratingBrick.start();
                whichAction=1;
                lose=false;
            }
            else if(whichAction==1){
                pause.pause();
                addButton();
                whichAction=2;
            }
            else if(whichAction==2){
                pause.Continue();
                whichAction=1;
            }
        }
    }
}
