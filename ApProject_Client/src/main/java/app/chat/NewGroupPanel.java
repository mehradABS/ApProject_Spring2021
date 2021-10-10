package app.chat;

import controller.OfflineController;
import controller.OnlinePanels;
import events.chat.LoadFriendsForNewGroupPanelEvent;
import network.EventListener;
import resources.Colors;
import resources.Fonts;
import resources.Images;
import resources.Texts;
import view.listeners.StringListener;
import view.usersView.UserPanelSelectable;
import view.usersView.UsersViewPanel;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.io.IOException;
import java.util.List;

public class NewGroupPanel extends JPanel implements OnlinePanels {

    private final JLabel connectingLabel;
    private StringListener stringListener;
    private final JTextArea textArea;
    private final JButton addButton;
    private final JLabel defaultText;
    private final JButton backButton;
    private final JScrollPane downPanel;
    private final UsersViewPanel<UserPanelSelectable> usersViewPanel;
    private final JLabel noFriendLabel;
    private final EventListener eventListener;


    public NewGroupPanel(EventListener eventListener){
        OfflineController.ONLINE_PANELS.add(this);
        connectingLabel = new JLabel(Texts.CONNECTING);
        connectingLabel.setBounds(150,0,500,200);
        connectingLabel.setFont(Fonts.WELCOME_LABEL_FONT);
        this.eventListener = eventListener;
        textArea = new JTextArea();
         textArea.setBackground(Color.decode(Colors.CHANGE_INFO_COLOR));
         textArea.setBounds(75,10,300,46);
         textArea.setFont(Fonts.TWEET_FONT);
         addButton = new JButton(Images.ADD_LIST);
         addButton.setBackground(Color.decode(Colors.CHANGE_INFO_COLOR));
         addButton.setBounds(10, 60, 54, 45);
         backButton = new JButton(Images.BACK_ICON);
         defaultText = new JLabel(Texts.GROUP_NAME);
        defaultText.setFont(Fonts.TWEET_FONT);
        defaultText.setBounds(0, -7, 200, 40);
        textArea.add(defaultText);
        textArea.getDocument().putProperty("name", "TextArea");
        addButton.setEnabled(false);
        addButton.setFocusable(false);

        textArea.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                defaultText.setVisible(false);
                if(usersViewPanel.isAnyPanelChosen())
                addButton.setEnabled(true);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                if (textArea.getText().isEmpty()) {
                    defaultText.setVisible(true);
                    addButton.setEnabled(false);
                }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {

            }
        });
        backButton.setBounds(10, 10, 54, 45);
        backButton.setBackground(Color.decode(Colors.CHANGE_INFO_COLOR));
        backButton.setFocusable(false);
        backButton.addActionListener(e -> {
            try {
                resetPanel();
                listenMe("back");
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });
        addButton.addActionListener(e -> {
            try {
                listenMe("addGroup");
                resetPanel();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });
        //
        usersViewPanel = new UsersViewPanel<>(410, UserPanelSelectable::new);
        usersViewPanel.setStringListener(this::listenMe);
        downPanel = new JScrollPane(usersViewPanel,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        downPanel.setBackground(Color.decode(Colors.MENU_PANEL_COLOR));
        downPanel.setBounds(0,120,410,580);
        //
        usersViewPanel.setStringListener
                (text -> addButton.setEnabled(usersViewPanel.isAnyPanelChosen()
         && !textArea.getText().isEmpty()));
        //
        noFriendLabel = new JLabel(Texts.NO_FRIEND);
        noFriendLabel.setBounds(70,140,600,200);
        noFriendLabel.setForeground(Color.decode(Colors.WELCOME_LABEL_COLOR));
        noFriendLabel.setFont(Fonts.WELCOME_LABEL_FONT);
        //
        this.setLayout(null);
        this.setBounds(0,0,410,700);
        this.setBackground(Color.decode(Colors.MENU_PANEL_COLOR));
    }

    public void setStringListener(StringListener stringListener) {
        this.stringListener = stringListener;
    }

    public void listenMe(String name) throws IOException {
        stringListener.stringEventOccurred(name);
    }

    public List<Integer> getUserIds() {
        return usersViewPanel.selectedPanelIds();
    }

    public String groupName(){
        return textArea.getText();
    }

    public void setUsers(List<String[]> info) throws IOException {
       usersViewPanel.setUserPanels(info);
       usersViewPanel.setSize();
       usersViewPanel.addPanels();
       if(usersViewPanel.getUserPanelList().size() == 0){
           usersViewPanel.add(noFriendLabel);
       }
       repaint();
       revalidate();
    }

    public void getInfo(){
        eventListener.listen(new LoadFriendsForNewGroupPanelEvent());
    }

    public void resetPanel(){
        usersViewPanel.resetSelectedPanels();
        usersViewPanel.resetPanel();
        textArea.setText("");
        repaint();
        revalidate();
    }

    @Override
    public void changeState() {
        setInfo();
    }

    public void addPanels(){
        this.add(downPanel);
        this.add(backButton);
        this.add(addButton);
        this.add(textArea);
        getInfo();
        repaint();
        revalidate();
    }

    public void setInfo(){
        removeAll();
        if(OfflineController.IS_ONLINE){
            addPanels();
        }
        else{
            add(backButton);
            add(connectingLabel);
            repaint();
            revalidate();
        }
    }
}