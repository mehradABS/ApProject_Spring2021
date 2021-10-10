package app.personalPage.subPart.forwardMessage;

import app.chat.ContactsPanel;
import events.forwardMessage.ForwardMessageEvent;
import network.EventListener;
import resources.Colors;
import resources.Images;
import responses.forwardMessage.ForwardMessageResponse;
import responses.forwardMessage.ForwardMessageResponseVisitor;
import responses.visitors.ResponseVisitor;
import view.listeners.StringListener;
import view.usersView.UserPanelSelectable;
import view.usersView.UsersViewPanel;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class ForwardPanel extends JPanel implements ForwardMessageResponseVisitor {

    private String messageType;
    private int messageId;
    private final JButton backButton;
    private final UsersViewPanel<UserPanelSelectable> usersViewPanel;
    private final JButton sendButton;
    private StringListener stringListener;
    private final EventListener eventListener;
    private final String responseVisitorType;

    public ForwardPanel(EventListener eventListener, HashMap<String, ResponseVisitor>
                         visitors, String responseVisitorType) {
        this.responseVisitorType = responseVisitorType;
        visitors.put(responseVisitorType, this);
        this.eventListener = eventListener;
        usersViewPanel = new UsersViewPanel<>(700,UserPanelSelectable::new);
        JScrollPane downPa = new JScrollPane(usersViewPanel,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        downPa.setBounds(0,50,700,380);
        //
        backButton = new JButton(Images.CLOSE_ICON);
        sendButton = new JButton(Images.SEND_ICON);
        //
        sendButton.setBackground(Color.decode(Colors.CHANGE_INFO_COLOR));
        sendButton.setBounds(350,440,54,45);
        sendButton.setEnabled(false);
        sendButton.addActionListener(e->{
            try {
                sendButtonAction(usersViewPanel.selectedPanelIds());
                listenMe("ap is ....");
            }
            catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });
        //
        backButton.setBounds(10, 5, 54, 45);
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
        //
        usersViewPanel.setStringListener(text -> {
            if(usersViewPanel.isAnyPanelChosen()){
                sendButton.setEnabled(true);
            }
        });
        //
        this.setLayout(null);
        this.setBounds(0,200,700,500);
        this.setBackground(Color.decode(Colors.CHANGE_INFO_COLOR));
        this.add(backButton);
        this.add(sendButton);
        this.add(downPa);
    }

    public void getAnswer(ForwardMessageResponse response){
        setInfo(response.getInfo());
    }

    public void resetPanel(){
        usersViewPanel.resetSelectedPanels();
        usersViewPanel.resetPanel();
    }

    public void setStringListener(StringListener stringListener) {
        this.stringListener = stringListener;
    }

    public void listenMe(String name) throws IOException {
        stringListener.stringEventOccurred(name);
    }

    public void sendButtonAction(List<Integer> ids) throws IOException {
        ForwardMessageEvent event = new ForwardMessageEvent();
        event.setEvent("sendMessage");
        event.setResponseVisitorType(responseVisitorType);
        event.setMessageId(messageId);
        event.setMessageType(messageType);
        event.setIds(ids);
        eventListener.listen(event);
    }

    public void setMessageId(int messageId) {
        this.messageId = messageId;
    }

    public void getInfo(){
        ForwardMessageEvent event = new ForwardMessageEvent();
        event.setEvent("loadFollowing");
        event.setResponseVisitorType(responseVisitorType);
        eventListener.listen(event);
    }

    public void setInfo(List<String[]> info){
        ContactsPanel.imageHandling(info);
        try {
            usersViewPanel.setUserPanels(info);
            usersViewPanel.setSize();
            usersViewPanel.addPanels();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }
}
