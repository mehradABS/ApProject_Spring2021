package Logic;

import javax.swing.*;
import java.io.File;
import java.io.IOException;


public class Father extends JFrame{
    GamePanel gamePanel;
    MenuPanel menuPanel;
    Pause pause;
    public Father()  {
        menuPanel=new MenuPanel(this);
    }
    public void initialize(){
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.add(menuPanel);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setVisible(true);
    }

    public void StartGame(boolean isNewGame,String gameName) throws IOException {
        this.remove(menuPanel);

        if(isNewGame){
            File file1=new File("src\\Save\\Players\\"+
                    menuPanel.getLoadGame().getPlayerName()+"\\" +
                    "score.txt");
            if(!file1.exists()){
                file1.getParentFile().mkdirs();
                file1.createNewFile();
            }
            gamePanel=new GamePanel(this,
                    menuPanel.getLoadGame().getPlayerName(),gameName);
        }
        else{
            gamePanel=menuPanel.getLoadGame().load(this,gameName);
        }
        this.pause= gamePanel.getPause();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.add(gamePanel);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setVisible(true);
    }

    public void backToMenu(){
        this.remove(gamePanel);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.add(menuPanel);
        menuPanel.add(menuPanel.getExitButton());
        menuPanel.add(menuPanel.getStartButton());
        menuPanel.add(menuPanel.getScoreBoardButton());
        this.pack();
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setVisible(true);
    }
}