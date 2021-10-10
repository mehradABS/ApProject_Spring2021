package app.explore.search;

import controller.OfflineController;
import controller.OnlinePanels;
import events.search.SearchEvent;
import network.EventListener;
import resources.Colors;
import resources.Fonts;
import resources.Images;
import resources.Texts;
import responses.visitors.ResponseVisitor;
import responses.visitors.search.SearchResponseVisitor;
import view.listeners.StringListener;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.io.IOException;
import java.util.HashMap;

public class SearchPanel extends JPanel implements SearchResponseVisitor, OnlinePanels {

    private final JTextArea searchArea;
    private final JButton searchButton;
    private final JLabel defaultText;
    private final JLabel invalidLabel;
    private StringListener stringListener;
    private final SearchEvent searchEvent;
    private int userId;
    private final EventListener eventListener;
    private final JLabel connectingLabel;
    public SearchPanel(EventListener eventListener, HashMap<String, ResponseVisitor>
                       visitors) {
        this.eventListener = eventListener;
        visitors.put("SearchResponseVisitor", this);
        //
        searchEvent = new SearchEvent();
        //
        connectingLabel = new JLabel(Texts.CONNECTING);
        connectingLabel.setBounds(150,0,500,200);
        connectingLabel.setFont(Fonts.WELCOME_LABEL_FONT);
        //
        searchArea = new JTextArea();
        searchArea.setBounds(10, 10, 500, 84);
        searchArea.setBackground(Color.decode(Colors.CHANGE_INFO_COLOR));
        searchArea.setFont(Fonts.TWEET_FONT);
        defaultText = new JLabel();
        defaultText.setFont(searchArea.getFont());
        defaultText.setText(Texts.SEARCH_AREA_DEFAULT_TEXT);
        defaultText.setBounds(0, -7, 200, 40);
        searchArea.add(defaultText);
        searchArea.getDocument().putProperty("name", "TextArea");
        searchButton = new JButton(Images.SEARCH_ICON);
        searchButton.setEnabled(false);
        searchButton.setFocusable(false);
        searchArea.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                defaultText.setVisible(false);
                searchButton.setEnabled(true);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                if (searchArea.getText().isEmpty()) {
                    defaultText.setVisible(true);
                    searchButton.setEnabled(false);
                }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {

            }
        });
        searchButton.setBounds(530, 20, 80, 70);
        searchButton.setBackground(Color.decode(Colors.CHANGE_INFO_COLOR));
        searchButton.addActionListener(e -> searchButtonAction());
        //
        invalidLabel = new JLabel(Texts.INVALID_SEARCH);
        invalidLabel.setBounds(200, 90, 400, 40);
        invalidLabel.setForeground(Color.decode(Colors.LABEL_COLOR));
        invalidLabel.setFont(Fonts.Label_FONT);
        invalidLabel.setVisible(false);
        //
        this.setLayout(null);
        this.setBounds(0, 0, 700, 150);
        this.setBackground(Color.decode(Colors.SUB_PANEL));
    }

    public JTextArea getSearchArea() {
        return searchArea;
    }

    public JButton getSearchButton() {
        return searchButton;
    }

    public JLabel getDefaultText() {
        return defaultText;
    }

    public JLabel getInvalidLabel() {
        return invalidLabel;
    }

    public void searchButtonAction() {
        searchEvent.setUsername(searchArea.getText());
        eventListener.listen(searchEvent);
    }

    public void setStringListener(StringListener stringListener){
        this.stringListener = stringListener;
    }

    public void listenMe(String name){
        try {
            stringListener.stringEventOccurred(name);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    public int getUserId() {
        return userId;
    }

    public void resetPanel(){
        searchArea.setText(null);
        searchButton.setEnabled(false);
        invalidLabel.setVisible(false);
    }

    @Override
    public void gotoSearchedUserPanel(int id) {
        if(id != -1) {
            userId = id;
            invalidLabel.setVisible(false);
            listenMe("search");
        }
        else{
            invalidLabel.setVisible(true);
        }
    }

    @Override
    public void changeState() {
        resetPanel();
        removeAll();
        if(OfflineController.IS_ONLINE) {
            addPanels();
        }
        else{
            add(connectingLabel);
            repaint();
            revalidate();
        }
    }

    public void addPanels(){
        this.add(searchArea);
        this.add(searchButton);
        this.add(invalidLabel);
        repaint();
        revalidate();
    }

    public void setInfo(){
        if(OfflineController.IS_ONLINE){
            addPanels();
        }
        else{
            add(connectingLabel);
            repaint();
            revalidate();
        }
    }
}