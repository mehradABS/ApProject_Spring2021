package view;



import auth.AuthToken;
import network.EventListener;
import responses.visitors.ResponseVisitor;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public class FatherFrame extends JFrame {


    public FatherFrame(EventListener eventListener,
                       HashMap<String, ResponseVisitor> visitors,
                       AuthToken authToken) throws HeadlessException{
        ProgramMainPanel programMainPanel = new ProgramMainPanel(
                eventListener, visitors, authToken);
        //
        //
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.add(programMainPanel);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setResizable(false);
    }


    public void initialize() {
        setVisible(true);
    }
}