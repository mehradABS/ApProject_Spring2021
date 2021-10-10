package Logic;


import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Scanner;

public class ScoreBoard  {
    JScrollPane scrollPane;
    JTextArea textArea;
    JButton button;
    public ScoreBoard(MenuPanel panel, Father frame) throws FileNotFoundException {
        button = new JButton("Back");
        textArea = new JTextArea();
        scrollPane = new JScrollPane(textArea,
             JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
             JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPane.setBounds(700,300,500,500);
        textArea.setBackground(Color.CYAN);
        textArea.setFont(new Font("Consolas",Font.PLAIN,45));
        textArea.setBounds(700,300,500,500);
        textArea.setEditable(false);
        button.setBounds(910,240,100,40);
        File file=new File("src\\Save\\Players\\Names");
        Scanner scanner=new Scanner(file);

        LinkedList<Integer> scores = new LinkedList<>();
        LinkedList<String> players = new LinkedList<>();
        while (scanner.hasNext()) {
            String playerName = scanner.next();
            File file1 = new File("src\\Save\\Players\\" + playerName + "\\" +
                    "score.txt");
            Scanner scanner1 = new Scanner(file1);
            if (scanner1.hasNext()){
                scores.add(scanner1.nextInt());
                players.add(playerName+": ");
            }
            scanner1.close();
        }
        for (int i = 0; i < scores.size()-1; i++) {
            if(scores.get(i)>scores.get(i+1)){
                int a=scores.get(i);
                scores.set(i,scores.get(i+1));
                scores.set(i+1,a);
                String b=players.get(i);
                players.set(i,players.get(i+1));
                players.set(i+1,b);
            }
        }
        for (int i = scores.size()-1 ; i >=0; i--) {
            textArea.append(players.get(i)+""+scores.get(i)+"\n");
        }
        scanner.close();
        panel.add(button);
        panel.remove(panel.getExitButton());
        panel.remove(panel.getScoreBoardButton());
        panel.remove(panel.getStartButton());
        panel.add(scrollPane);
        panel.repaint();
        button.addActionListener(e->{
            panel.remove(scrollPane);
            panel.remove(button);
            panel.add(panel.getExitButton());
            panel.add(panel.getScoreBoardButton());
            panel.add(panel.getStartButton());
            panel.repaint();
        });
    }
}
