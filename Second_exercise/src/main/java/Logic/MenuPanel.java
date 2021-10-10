package Logic;

import javax.swing.*;
import java.awt.*;
import java.io.FileNotFoundException;
import java.io.IOException;

public class MenuPanel extends JPanel  {
    private final JButton startButton;
    private final JButton scoreBoardButton;
    private final JButton ExitButton;
    private final Image MenuBackgroundImage=new ImageIcon
            ("finalproject\\photo\\space.png").getImage();
    private JButton submit;
    private JTextField textField;
    private final LoadGame loadGame;
    public MenuPanel(Father frame) {
        loadGame=new LoadGame();
        this.setLayout(null);
        this.setPreferredSize(new Dimension
                (Constant.PANEL_WIDTH, Constant.PANEL_HEIGHT));
        startButton=new JButton("Start");
        scoreBoardButton=new JButton("ScoreBoard");
        ExitButton=new JButton("Exit");
        startButton.setBounds(900,400,250,40);
        scoreBoardButton.setBounds(900,500,250,40);
        startButton.setIcon
                (new ImageIcon("finalproject\\photo\\startbutton.png"));
        startButton.setBackground(Color.red);
        scoreBoardButton.setIcon
                (new ImageIcon("finalproject\\photo\\scoreboardbutton.png"));
        scoreBoardButton.setBackground(Color.red);
        ExitButton.setIcon(new ImageIcon("finalproject\\" +
                "photo\\exitbutton.png"));
        ExitButton.setBackground(Color.red);
        ExitButton.setBounds(900,600,250,40);
        startButton.addActionListener(e ->{
            if(e.getSource()==startButton){
                startButtonAction(frame);
            }
        });

      scoreBoardButton.addActionListener(e->{
          try {
              new ScoreBoard(this,frame);
              repaint();
          } catch (FileNotFoundException fileNotFoundException) {
              fileNotFoundException.printStackTrace();
          }
      });

        ExitButton.addActionListener(e->{
            System.exit(0);
        });
        this.add(ExitButton);
        this.add(startButton);
        this.add(scoreBoardButton);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2D = (Graphics2D) g;
        g2D.drawImage(MenuBackgroundImage,0,0,null);
    }

    public void startButtonAction(Father frame){
        this.remove(ExitButton);
        this.remove(startButton);
        this.remove(scoreBoardButton);


        textField=new JTextField();
        textField.setPreferredSize(new Dimension(250,40));
        textField.setFont(new Font("Consolas",Font.PLAIN,35));
        textField.setForeground(new Color(0x00FF00));
        textField.setBackground(Color.BLACK);
        textField.setCaretColor(Color.WHITE);
        textField.setText("yourName");
        textField.setBounds(900,400,250,40);

        submit=new JButton("submit");
        submit.setFocusable(false);
        submit.setBounds(750,400,120,40);
        submit.addActionListener(e-> {
            if(e.getSource()==submit){
                String playerName=textField.getText();
                loadGame.setPlayerName(playerName);
                submitButtonAction(frame);
            }
        });

        this.add(textField);
        this.add(submit);
        repaint();
    }

    public void submitButtonAction(Father frame){
       textField.setText("Game's name");
       // ye chizaye dige ham bayad bashe

       submit.addActionListener(e->{
           String gameName=textField.getText();
           this.remove(textField);
           this.remove(submit);
           try {
               frame.StartGame(loadGame.isNewGame(gameName),gameName);
           } catch (IOException ioException) {
               ioException.printStackTrace();
           }
       });
    }

    public JButton getStartButton() {
        return startButton;
    }

    public JButton getSubmit() {
        return submit;
    }

    public JButton getExitButton() {
        return ExitButton;
    }

    public JButton getScoreBoardButton() {
        return scoreBoardButton;
    }

    public LoadGame getLoadGame() {
        return loadGame;
    }


}
